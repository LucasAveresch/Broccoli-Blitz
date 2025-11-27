package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class YourGameScreen extends ScalableGameScreen {

    public YourGameScreen() {
        super(1280, 720); // resolutie 1280x720
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
    }

    @Override
    public void hide() {
        // Geen cleanup nodig
    }
}