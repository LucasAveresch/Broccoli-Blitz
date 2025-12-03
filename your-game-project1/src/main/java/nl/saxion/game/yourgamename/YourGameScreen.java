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

        // --- Dood gaan check ---
        if (Methodes_Rutger.checkDeath(player)) {
            return; // stop render zodat er niet verder getekend wordt
        }

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        PlayerClass.worldX += 300 * delta; // sneller in game
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(PlayerClass.worldX, getWorldWidth());

        Methodes_Rutger.update(player, player.filepath);
        Methodes_Rutger.spawnCoins();
        Methodes_Rutger.updateCoins(player);
        Methodes_Rutger.updateScore(player, delta);
        Methodes_Rutger.drawGameHud(player);

        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}