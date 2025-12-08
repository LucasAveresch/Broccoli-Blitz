package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class DeathScreen extends ScalableGameScreen {
    private PlayerClass player;
    private long deathSoundId; // hier bewaren we het ID

    public DeathScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;


    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
        GameApp.addFont("small", "fonts/basic.ttf", 30);

        GameApp.addSound("death", "Sounds/DeadSound.mp3");
        GameApp.addTexture("DeathScreen", "img/DeathScreen.png");
        deathSoundId = GameApp.playSound("death", 0.8f); // ID opslaan

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        // Achtergrond + stats
        GameApp.drawTexture("DeathScreen", 0, 0, getWorldWidth(), getWorldHeight());
        Methodes_Rutger.drawDeathStats(player, this);

        GameApp.endSpriteRendering();

        // Exit naar menu: eerst sound stoppen, dan resetten, dan switch
        if (GameApp.isKeyJustPressed(Input.Keys.M)) {
            GameApp.stopSound(deathSoundId);
            Methodes_Rutger.resetRoundStats(player);
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
        GameApp.disposeFont("small");
    }

    @Override
    public void dispose() {
        // eventueel resources opruimen
    }
}