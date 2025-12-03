package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class MainMenuScreen extends ScalableGameScreen {
    private PlayerClass player;
    private float worldX; // voor parallax beweging
    private float broccoliX;
    private boolean isStarting = false;

    public MainMenuScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;

    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 100);

        // Init parallax achtergrond
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);

        // Broccoli texture toevoegen
        GameApp.addTexture("brocolli", "img/brocolli.png");
        broccoliX = getWorldWidth() / 2f - player.spriteWidth / 2f - 50;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        // Spatie activeert beweging én springen
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            isStarting = true;

            // Springen (max 2 sprongen zoals in je update)
            if (player.jumpCount < 2) {
                player.velocity = 20;
                player.jumpCount++;
            }
        }

        // --- SPRING UPDATE ---
        player.velocity -= player.gravity;
        player.yPlayer += player.velocity;

        if (player.yPlayer < player.groundLevel) {
            player.yPlayer = player.groundLevel;
            player.velocity = 0;
            player.jumpCount = 0;
        }

        // Achtergrond beweegt alleen zolang we NIET starten
        if (!isStarting) {
            PlayerClass.worldX += 100 * delta; // trager in menu
        }
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(PlayerClass.worldX, getWorldWidth());

        // Broccoli beweegt naar startpositie (alleen X)
        if (isStarting && broccoliX > 100) {
            broccoliX -= 300 * delta;
            if (broccoliX < 100) broccoliX = 100;
        }

        // Zodra X bereikt is → start spel
        if (isStarting && broccoliX == 100) {
            GameApp.switchScreen("YourGameScreen");
        }

        // Teken broccoli op huidige X en Y (nu met springen)
        GameApp.drawTexture("brocolli", (int)broccoliX, player.yPlayer);

        // Tekst
        Methodes_Rutger.drawMenuText(this);

        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}