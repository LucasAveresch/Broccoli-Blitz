package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import java.util.ArrayList;

public class Methodes_Rutger {
    // Lijst van kogels
    public static ArrayList<BulletClass> bullets = new ArrayList<>();

    // Speler update (springen, bukken, tekenen)
    public static void update(PlayerClass Player, String filepath) {
        // --- SPRINGEN ---
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            if (Player.jumpCount < 2) { // maximaal 2 sprongen
                Player.velocity = 20;
                Player.jumpCount++;
            }
        }

        // --- BUKKEN / SNELLER VALLEN ---
        int currentGravity = Player.gravity;
        if (GameApp.isKeyPressed(Input.Keys.SHIFT_LEFT) || GameApp.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            currentGravity = Player.gravity * 3; // 3x sneller naar beneden
        }

        // Zwaartekracht toepassen
        Player.velocity -= currentGravity;
        Player.yPlayer += Player.velocity;

        // Terug naar de grond
        if (Player.yPlayer < Player.groundLevel) {
            Player.yPlayer = Player.groundLevel;
            Player.velocity = 0;
            Player.jumpCount = 0;
        }

        // --- SCHIETEN ---
        if (GameApp.isKeyJustPressed(Input.Keys.F)) {
            int broccoliX = 100; // zelfde X als broccoli
            int startX = broccoliX + Player.spriteWidth;
            int startY = Player.yPlayer + Player.spriteHeight / 2; // middenhoogte van broccoli
            bullets.add(new BulletClass(startX, startY));
        }

        // Kogels bewegen en tekenen
        for (int i = 0; i < bullets.size(); i++) {
            BulletClass b = bullets.get(i);
            b.x += b.velocity; // beweegt horizontaal
            GameApp.drawTexture("kogel", b.x, b.y, 90, 75);

            if (b.x > GameApp.getWorldWidth()) {
                bullets.remove(i);
                i--;
            }
        }

        // --- TEKENEN VAN DE BROCCOLI ---
        GameApp.drawTexture("brocolli", 100, Player.yPlayer);
    }
    }