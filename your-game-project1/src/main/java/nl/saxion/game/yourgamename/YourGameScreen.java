package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;
import java.util.Random;

public class YourGameScreen extends ScalableGameScreen {

    public PlayerClass player;
    public EnemyClass enemyClass;
    public ProjectileClass projectileClass;
    public PowerupClass powerupClassSchild;
    public SchildClass schildClass;
    public unlimitedAmmoPowerupClass unlimitedAmmoPowerupClass;
    public SubEnemyClass subEnemyClass;
    public flamethrowerClass flamethrowerClass;

    public ObstacleClass obstacle;

    private PlatformManager platformManager;
    private final Random rng = new Random();

    private int baseGroundLevel;

    public YourGameScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;
    }

    @Override
    public void show() {

        Methodes_Rutger.resetRoundStats(player);
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);

        // ⭐ ALTIJD vloer resetten bij nieuwe ronde
        player.groundLevel = 100;
        player.yPlayer = 100;
        baseGroundLevel = 100;

        GameApp.addTexture("kogel", "img/kogel.png");
        GameApp.addTexture("brocolli", "img/brocolli3.png");
        GameApp.addTexture("block", "img/block1.png");
        GameApp.addTexture("coin", "img/munt.png");
        GameApp.addTexture("obstacle", "img/obstacle.png");
        GameApp.addTexture("platform", "img/platform.png");

        for (int i = 0; i < MuzzleFlash.TOTAL_FRAMES; i++) {
            GameApp.addTexture("muzzleFlash" + i, "img/MuzzleFlash/muzzle_flash_" + i + ".png");
        }

        for (int i = 1; i <= 10; i++) {
            GameApp.addTexture("bom" + i, "img/Bom/bom" + i + ".png");
        }

        GameApp.addSound("shoot", "Sounds/Schieten.mp3");
        GameApp.addSound("coin", "Sounds/coin.mp3");
        GameApp.addSound("Reload", "Sounds/Reload.mp3");
        GameApp.addSound("NoAmmo", "Sounds/NoAmmo.mp3");
        GameApp.addSound("Bomb", "Sounds/explosie.mp3");
        GameApp.addSound("block", "Sounds/block.mp3");

        flamethrowerClass = new flamethrowerClass(0, 0);
        enemyClass = new EnemyClass("img/chef.png", "chef", "img/ketchup.png", "enemy2", 1100, 150, 1000);

        projectileClass = new ProjectileClass(
                "img/mes.png",
                "mes",
                enemyClass.enemyXPos,
                enemyClass.enemyYPos + 20,
                200
        );

        schildClass = new SchildClass(
                "img/activeSchild.png",
                "fullschild",
                "crackedSchild",
                "img/activeCrackedShield.png"
        );

        unlimitedAmmoPowerupClass = new unlimitedAmmoPowerupClass();

        subEnemyClass = new SubEnemyClass(
                "img/stokbrood.png",
                "stokbrood",
                enemyClass.enemyXPos,
                enemyClass.enemyYPos,
                350
        );

        powerupClassSchild = new PowerupClass(
                "img/schild.png",
                "schild",
                "img/unlimitedKogels.png",
                "unlimitedkogels"
        );

        // ⭐ Eerste obstacle op de vloer
        obstacle = new ObstacleClass(
                1500,
                baseGroundLevel,
                110, 70,
                150, 150,
                "obstacle"
        );

        // ⭐ PlatformManager + startplatformen
        platformManager = new PlatformManager(baseGroundLevel);
        platformManager.spawnPlatform(1600);
        platformManager.spawnPlatform(2200);
        platformManager.spawnPlatform(2800);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Methodes_Rutger.checkDeath(player)) {
            return;
        }

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        PlayerClass.worldX += 300 * delta;

        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(
                PlayerClass.worldX,
                getWorldWidth(),
                getWorldHeight()
        );

        // ⭐ Elke frame vloer resetten
        player.groundLevel = baseGroundLevel;

        // ⭐ Platform update + draw
        platformManager.update(delta);
        platformManager.draw();

        // ⭐ Platform collision
        for (PlatformClass platform : platformManager.platforms) {

            if (platform.playerIsOnTop(player)) {

                int platformTop = (int) (platform.y + platform.height);

                if (player.yPlayer < platformTop) {
                    player.yPlayer = platformTop;
                }

                player.groundLevel = platformTop;
            }
        }

        // ⭐ Obstacle update + draw
        obstacle.update(delta);
        obstacle.draw();

        // ⭐ Random nieuwe obstacle op de vloer
        if (obstacle.x + obstacle.width < 0) {

            float randomDistance = 1000 + rng.nextInt(1000);

            obstacle = new ObstacleClass(
                    PlayerClass.worldX + randomDistance,
                    baseGroundLevel,   // altijd vloer
                    110, 70,
                    150, 150,
                    "obstacle"
            );
        }

        // ⭐ Physics-engine
        Methodes_Rutger.update(player, unlimitedAmmoPowerupClass);

        // ⭐ Random platform spawn (geen limiet)
        if (rng.nextFloat() < 0.003f) {  // 0.3% kans per frame
            platformManager.spawnPlatform(PlayerClass.worldX + 1600);
        }

        // ⭐ Rest van de game logic
        Methodes_Rutger.spawnCoins();
        Methodes_Rutger.updateCoins(player);
        Methodes_Rutger.updateScore(player, delta);
        Methodes_Rutger.drawGameHud(player);
        Methodes_Rutger.drawBombCooldown();

        if (!enemyClass.enemyIsDead) {
            Methodes_Maxje.updateEnemies(delta, enemyClass, subEnemyClass);
        }

        Methodes_Rutger.checkBulletHitsEnemy(player, enemyClass);
        Methodes_Rutger.checkKogelCollisionSubEnemy(player, subEnemyClass);
        Methodes_Maxje.checkCollsionMes(projectileClass, player);
        Methodes_Maxje.checkCollisionEnemy(player, enemyClass, subEnemyClass, schildClass, powerupClassSchild);
        Methodes_Maxje.checkForPowerupPickup(player, powerupClassSchild);
        Methodes_Maxje.updateSchildPowerup(delta, powerupClassSchild, schildClass);
        Methodes_Maxje.updateunlimitedKogels(delta, powerupClassSchild, unlimitedAmmoPowerupClass, player);
        Methodes_Maxje.unlimitedKogelsLogic(delta, unlimitedAmmoPowerupClass, player, powerupClassSchild);
        Methodes_Maxje.activeSchildUpdate(schildClass, player);
        Methodes_Maxje.selectEnemyWillekeurig(delta, enemyClass);
        Methodes_Maxje.checkShieldCollisionKnife(schildClass, projectileClass, player);
        Methodes_Rutger.updateSurvivalTime(player, delta);
        Methodes_Rutger.updateBlocking(player);
        Methodes_Rutger.updateBomb(player, enemyClass);
        Methodes_Maxje.addMes(delta, projectileClass, enemyClass);
        Methodes_Maxje.updateMes(delta, projectileClass, enemyClass);
        Methodes_Rutger.updatePowerupTimer(delta, powerupClassSchild, schildClass);
        Methodes_Rutger.drawPowerupTimer(powerupClassSchild);
        Methodes_Maxje.genereerRandomPowerup(powerupClassSchild, delta);
        Methodes_Maxje.tekenFlamethrower(delta, flamethrowerClass, enemyClass);

        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}