package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    private PlayerClass player;

    public MainMenuScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;


    }


    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        // When the user presses enter, go to the next screen
        if (GameApp.isKeyJustPressed(Input.Keys.ENTER)) {
            GameApp.switchScreen("YourGameScreen");
        }

        // Render the main menu
        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();
        GameApp.drawTextCentered("basic", "Start Game (press enter)", getWorldWidth()/2, getWorldHeight()/2, "amber-500");
        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
    }
}
