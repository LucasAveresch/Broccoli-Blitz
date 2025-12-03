package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class YourGameScreen extends ScalableGameScreen {
    public PlayerClass player;

    public YourGameScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;
    }

    @Override
    public void show() {
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);
        GameApp.addTexture("kogel", "img/kogel.png");
        GameApp.addTexture("brocolli", "img/brocolli.png");
        GameApp.addTexture("coin", "img/munt.png");
    }

    private float Worldx;

    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        Worldx += 300 * delta;
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(Worldx, getWorldWidth());

        // Use the SAME player instance everywhere:
        Methodes_Rutger.update(player, player.filepath);
        Methodes_Rutger.spawnCoins();
        Methodes_Rutger.updateCoins(player);

        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}