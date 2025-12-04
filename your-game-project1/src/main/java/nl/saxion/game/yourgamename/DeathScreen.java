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
        deathSoundId = GameApp.playSound("death", 0.8f); // ID opslaan

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        // Titel
        GameApp.drawText("basic", "GAME OVER", getWorldWidth()/2f - 250, getWorldHeight()/2f + 200,"white");

        // Statistieken van de speler
        GameApp.drawText("small", "Coins: " + player.totalCoins,
                getWorldWidth()/2f - 200, getWorldHeight()/2f + 100,"white");

        GameApp.drawText("small", "Score: " + player.distanceTravelled,
                getWorldWidth()/2f - 200, getWorldHeight()/2f + 60,"white");

        GameApp.drawText("small", "Verslagen enemies: " + player.enemiesDefeated,
                getWorldWidth()/2f - 200, getWorldHeight()/2f + 20,"white");

        GameApp.drawText("small", "Aantal keer geschoten: " + player.shotsFired,
                getWorldWidth()/2f - 200, getWorldHeight()/2f - 20,"white");

        GameApp.drawText("small", "Overlevingstijd: " + (int)player.survivalTime + "s",
                getWorldWidth()/2f - 200, getWorldHeight()/2f - 60,"white");

        // Instructies
        GameApp.drawTextCentered("small", "Druk [M] voor hoofdmenu",
                getWorldWidth()/2f, getWorldHeight()/2f - 160,"white");

        GameApp.endSpriteRendering();

        // Input afhandelen
        if (GameApp.isKeyJustPressed(Input.Keys.M)) {
            GameApp.stopSound(deathSoundId); // stop het geluid

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