package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;
import static nl.saxion.game.yourgamename.Methodes_Rutger.*;
import static nl.saxion.game.yourgamename.Methodes_Maxje.*;
import static nl.saxion.game.yourgamename.Methodes_Lucas.*;

public class YourGameScreen extends ScalableGameScreen {
    public PlayerClass player;
    public YourGameScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player; // resolutie 1280x720

    }

    @Override
    public void show() {
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);
        // Geen extra setup nodig
    }
    private float Worldx;
    @Override
    public void render(float delta) {
        super.render(delta);

            // Scherm volledig zwart maken
            GameApp.clearScreen("black");

            GameApp.startSpriteRendering();
            Worldx += 300 * delta;
            Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(Worldx, getWorldWidth());
            Texture chef = Player(player.yPosition, player.filepath);
        Jump(200,chef);
            GameApp.endSpriteRendering();





    }


    @Override
    public void hide() {
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
        // Geen cleanup nodig
    }
}