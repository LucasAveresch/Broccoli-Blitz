package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    private PlayerClass player;
    private float worldX; // voor parallax beweging
    private float broccoliX;
    private boolean isStarting = false;
    private boolean justDied = false; // nieuwe flag

    public MainMenuScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;

    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
        GameApp.addFont("small", "fonts/basic.ttf", 30); // kleiner font voor HUD
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);
        GameApp.addTexture("brocolli", "img/brocolli.png");

        broccoliX = getWorldWidth() / 2f - player.spriteWidth / 2f - 50;
        isStarting = false;

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();
// Starter
        isStarting = Methodes_Rutger.handleMenuStart(isStarting);
// Springen
        Methodes_Rutger.updateJump(player);
// Achtergrond
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(PlayerClass.worldX, getWorldWidth());
// Broccoli bewegen + tekenen
        broccoliX = Methodes_Rutger.handleMenuBroccoli(player, broccoliX, isStarting, delta);
// Tekst
        Methodes_Rutger.drawMenuText(this, player);
        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}