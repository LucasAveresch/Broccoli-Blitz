package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import nl.saxion.gameapp.GameApp;
import org.w3c.dom.Text;

import java.security.PublicKey;

public class Methodes_Rutger {

    // Globale variabelen (bovenaan je programma)


    public static void update(PlayerClass Player, String filepath) {
        // Sprong starten als spatie net is ingedrukt
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            if (Player.jumpCount < 2) { // maximaal 2 sprongen
                Player.velocity = 20;   // omhoog
                Player.jumpCount++;     // tel sprong op
            }
        }

        // Check of Shift wordt ingedrukt → bukken/sneller vallen
        int currentGravity = Player.gravity;
        if (GameApp.isKeyPressed(Input.Keys.SHIFT_LEFT) || GameApp.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            currentGravity = Player.gravity * 4; // 3x sneller naar beneden
        }

        // Zwaartekracht toepassen
        Player.velocity -= currentGravity;
        Player.yPlayer += Player.velocity;

        // Check of speler weer op de grond staat
        if (Player.yPlayer < Player.groundLevel) {
            Player.yPlayer = Player.groundLevel;
            Player.velocity = 0;
            Player.jumpCount = 0; // reset sprongen
        }

        // Tekenen van de sprite
        GameApp.drawTexture("brocolli", 100, Player.yPlayer);
    }
}
