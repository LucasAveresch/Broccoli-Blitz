package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    private PlayerClass player;
    private float broccoliX;
    private boolean isStarting = false;

    public MainMenuScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;
    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);
        GameApp.addFont("small", "fonts/basic.ttf", 30);

        Methodes_Lucas.LucasParallaxMethods.initParallax(0);

        GameApp.addTexture("brocolli", "img/brocolli3.png");
        GameApp.addSound("start", "Sounds/Start.mp3");

        broccoliX = getWorldWidth() / 2f - player.spriteWidth / 2f - 50;
        isStarting = false;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        PlayerClass.totalPlayTime += delta;

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        isStarting = Methodes_Rutger.handleMenuStart(isStarting);
        Methodes_Rutger.updateJump(player);

        if (!isStarting) {
            PlayerClass.worldX += 100 * delta;
        }

        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(
                PlayerClass.worldX,
                getWorldWidth(),
                getWorldHeight()
        );



        broccoliX = Methodes_Rutger.handleMenuBroccoli(player, broccoliX, isStarting, delta);
        Methodes_Rutger.drawMenuText(this, player);

        GameApp.endSpriteRendering();

        if (GameApp.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameApp.switchScreen("SettingsScreen");
        }
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
        GameApp.disposeFont("small");
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}