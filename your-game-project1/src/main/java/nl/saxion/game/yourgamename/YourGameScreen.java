package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

public class YourGameScreen extends ScalableGameScreen {
    public PlayerClass player;
    public EnemyClass enemyClass;
    public ProjectileClass projectileClass;

    public YourGameScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;
    }

    @Override
    public void show() {
        // Schoon starten
        Methodes_Rutger.resetRoundStats(player);

        // Assets
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);
        GameApp.addTexture("kogel", "img/kogel.png");
        GameApp.addTexture("brocolli", "img/brocolli3.png");
        GameApp.addTexture("coin", "img/munt.png");
        for (int i = 0; i < MuzzleFlash.TOTAL_FRAMES; i++) {
            GameApp.addTexture("muzzleFlash" + i, "img/MuzzleFlash/muzzle_flash_" + i + ".png");
        }
        GameApp.addSound("shoot", "Sounds/Schieten.mp3");
        GameApp.addSound("coin", "Sounds/coin.mp3");

        // Nieuwe enemy/projectiel
        enemyClass = new EnemyClass("img/chef.png", "chef", 1100, 150, 100);
        projectileClass = new ProjectileClass("img/mes.png", "mes",
                enemyClass.enemyXPos, enemyClass.enemyYPos + 20, 500);
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

        Methodes_Rutger.update(player);
        Methodes_Rutger.spawnCoins();
        Methodes_Rutger.updateCoins(player);
        Methodes_Rutger.updateScore(player, delta);
        Methodes_Rutger.drawGameHud(player);

        if (!enemyClass.enemyIsDead) {
            Methodes_Maxje.updateEnenmy(delta, enemyClass);
            Methodes_Rutger.checkBulletHitsEnemy(player, enemyClass);
        }
        Methodes_Maxje.addMes(delta,projectileClass,enemyClass);
        Methodes_Maxje.updateMes(delta, projectileClass,enemyClass);
        Methodes_Maxje.checkCollsionMes(projectileClass,player);
        Methodes_Maxje.checkCollisionEnemy(player,enemyClass);
        Methodes_Rutger.updateSurvivalTime(player, delta);

        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}
