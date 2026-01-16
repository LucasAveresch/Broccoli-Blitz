package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class SettingsScreen extends ScalableGameScreen {

    private PlayerClass player;
    private EnemyClass enemy;
    private int tutorialStep = 1;
    private float worldX = 0;

    private unlimitedAmmoPowerupClass unlimitedAmmoPowerupClass = new unlimitedAmmoPowerupClass();

    public SettingsScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;
    }

    @Override
    public void show() {
        GameApp.addFont("basic", "fonts/basic.ttf", 60);
        GameApp.addFont("small", "fonts/basic.ttf", 30);

        GameApp.addTexture("brocolli", "img/brocolli3.png");
        GameApp.addTexture("coin", "img/munt.png");
        GameApp.addTexture("kogel", "img/kogel.png");

        for (int i = 0; i < MuzzleFlash.TOTAL_FRAMES; i++) {
            GameApp.addTexture("muzzleFlash" + i, "img/MuzzleFlash/muzzle_flash_" + i + ".png");
        }

        for (int i = 1; i <= BombClass.TOTAL_FRAMES; i++) {
            GameApp.addTexture("bom" + i, "img/Bom/bom" + i + ".png");
        }

        GameApp.addSound("shoot", "Sounds/Schieten.mp3");
        GameApp.addSound("Reload", "Sounds/Reload.mp3");
        GameApp.addSound("coin", "Sounds/coin.mp3");
        GameApp.addSound("NoAmmo", "Sounds/NoAmmo.mp3");
        GameApp.addSound("Bomb", "Sounds/explosie.mp3");

        Methodes_Lucas.LucasParallaxMethods.initParallax(0);

        Methodes_Rutger.resetTutorial(player);
        tutorialStep = 1;
        enemy = null;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        boolean inShootTutorial = (tutorialStep == 1);

        PlayerClass.totalPlayTime += delta;

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        // Achtergrond
        worldX += 200 * delta;
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(
                worldX,
                getWorldWidth(),
                getWorldHeight()
        );

        // Updates
        Methodes_Rutger.update(player, unlimitedAmmoPowerupClass, inShootTutorial);
        Methodes_Rutger.updateBomb(player, enemy);
        Methodes_Rutger.updateCoins(player);

        // HUD (nu met outline)
        Methodes_Rutger.drawGameHud(player);
        Methodes_Rutger.drawBombCooldown();

        // Tutorial tekst (nu met outline)
        Methodes_Rutger.drawTutorialText(this, tutorialStep);

        // Tutorial stappen
        switch (tutorialStep) {

            case 1:
                if (Methodes_Rutger.tutorialShoot(player)) tutorialStep++;
                break;

            case 2:
                if (Methodes_Rutger.tutorialReload(player)) tutorialStep++;
                break;

            case 3:
                if (Methodes_Rutger.tutorialBomb(player, enemy)) tutorialStep++;
                break;

            case 4:
                if (Methodes_Rutger.tutorialCoin(player)) tutorialStep++;
                break;

            case 5:
                if (enemy == null) {
                    enemy = new EnemyClass(
                            "img/chef.png",
                            "chef",
                            "img/enemy2.png",
                            "enemy2",
                            900,
                            150,
                            100
                    );
                    enemy.enemyIsDead = false;
                }

                if (!enemy.enemyIsDead) {
                    managerClass managerClass =new managerClass();
                    GameApp.drawTexture(enemy.textureKey, enemy.enemyXPos, enemy.enemyYPos-50, 150, 220);
                    Methodes_Rutger.checkBulletHitsTutorialEnemy(player, enemy,managerClass);
                }

                if (enemy.enemyIsDead) {
                    tutorialStep++;
                }
                break;
        }

        GameApp.endSpriteRendering();

        // Terug naar menu
        if (GameApp.isKeyJustPressed(Input.Keys.M)) {
            GameApp.switchScreen("MainMenuScreen");
        }
    }

    @Override
    public void hide() {
        GameApp.disposeFont("basic");
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}