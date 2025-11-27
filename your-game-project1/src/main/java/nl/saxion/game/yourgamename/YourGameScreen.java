package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Screen;
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
        // Geen extra setup nodig
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // Scherm volledig zwart maken
        GameApp.clearScreen("black");
        Underground();
        Player(player.yPosition);
    }

    @Override
    public void hide() {
        // Geen cleanup nodig
    }
}