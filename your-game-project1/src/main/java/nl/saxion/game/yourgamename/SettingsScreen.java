package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class SettingsScreen extends ScalableGameScreen {
    private PlayerClass player;
    private EnemyClass enemy;
    private int tutorialStep = 1;

    // voor parallax beweging
    private float worldX = 0;

    public SettingsScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;
    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 60);

        // Textures
        GameApp.addTexture("brocolli", "img/brocolli3.png");
        GameApp.addTexture("coin", "img/munt.png");
        GameApp.addTexture("kogel", "img/kogel.png");

        // Muzzle flash frames
        for (int i = 0; i < MuzzleFlash.TOTAL_FRAMES; i++) {
            GameApp.addTexture("muzzleFlash" + i, "img/MuzzleFlash/muzzle_flash_" + i + ".png");
        }

        // Bomb frames
        for (int i = 1; i <= BombClass.TOTAL_FRAMES; i++) {
            GameApp.addTexture("bom" + i, "img/Bom/bom" + i + ".png");
        }

        // Sounds
        GameApp.addSound("shoot", "Sounds/Schieten.mp3");
        GameApp.addSound("Reload", "Sounds/Reload.mp3");
        GameApp.addSound("coin", "Sounds/coin.mp3");
        GameApp.addSound("NoAmmo", "Sounds/NoAmmo.mp3");

        // Enemy voor tutorial
        enemy = new EnemyClass("img/chef.png", "chef", 900, 150, 100);
        enemy.enemyIsDead = false; // zeker weten dat hij leeft

        // Parallax achtergrond
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);

        // Reset tutorial coin flag
        Methodes_Rutger.resetTutorialCoin();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        // Achtergrond bewegen
        worldX += 200 * delta;
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(worldX, getWorldWidth());

        // Player update (springen, vallen, schieten, reload, muzzle flash, bommen, etc.)
        Methodes_Rutger.update(player);
        Methodes_Rutger.updateBomb(player, enemy);
        Methodes_Rutger.updateCoins(player);

        // HUD tekenen (ammo + coins + distance + highscore)
        Methodes_Rutger.drawGameHud(player);

        // Bomb cooldown tekst
        Methodes_Rutger.drawBombCooldown();

        // Tutorial tekst en checks
        Methodes_Rutger.drawTutorialText(this, tutorialStep);
        switch (tutorialStep) {
            case 1: if (Methodes_Rutger.tutorialShoot(player)) tutorialStep++; break;
            case 2: if (Methodes_Rutger.tutorialReload(player)) tutorialStep++; break;
            case 3: if (Methodes_Rutger.tutorialBomb(player, enemy)) tutorialStep++; break;
            case 4: if (Methodes_Rutger.tutorialCoin(player)) tutorialStep++; break;
            case 5: if (Methodes_Rutger.tutorialEnemy(player, enemy)) tutorialStep++; break;
        }

        GameApp.endSpriteRendering();

        // Exit naar menu
        if (GameApp.isKeyJustPressed(Input.Keys.M)) {
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
        // Parallax resources opruimen
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}