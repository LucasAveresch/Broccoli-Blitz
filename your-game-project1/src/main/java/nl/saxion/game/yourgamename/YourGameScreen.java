package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;

import java.util.ArrayList;
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

    private final Random rng = new Random();

    private int baseGroundLevel;

    // Kaasje-style level data
    private SegmentGenerator segmentGenerator;
    private float nextSegmentX = 1800;

    private ArrayList<PlatformClass> activePlatforms = new ArrayList<>();
    private ArrayList<ObstacleClass> activeObstacles = new ArrayList<>();

    public YourGameScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player;
    }

    @Override
    public void show() {

        Methodes_Rutger.resetRoundStats(player);
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);

        // vloer
        baseGroundLevel = 100;
        player.groundLevel = baseGroundLevel;
        player.yPlayer = baseGroundLevel;

        // textures
        GameApp.addTexture("kogel", "img/kogel.png");
        GameApp.addTexture("brocolli", "img/brocolli3.png");
        GameApp.addTexture("block", "img/block1.png");
        GameApp.addTexture("coin", "img/munt.png");

        // oude obstacle/platform keys kun je laten staan als je ze nog gebruikt
        GameApp.addTexture("obstacle", "img/obstacle.png");
        GameApp.addTexture("platform", "img/platform.png");

        // nieuwe Kaasje-style assets
        GameApp.addTexture("floorTile", "img/floorTile.png");
        GameApp.addTexture("floorRaise", "img/floorRaise.png");
        GameApp.addTexture("spike", "img/spike.png");
        GameApp.addTexture("spikeDouble", "img/spikeDouble.png");
        GameApp.addTexture("spikeTriple", "img/spikeTriple.png");

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

        // Kaasje-style segment generator
        segmentGenerator = new SegmentGenerator(baseGroundLevel);
        nextSegmentX = 1800;

        activePlatforms.clear();
        activeObstacles.clear();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Methodes_Rutger.checkDeath(player)) {
            return;
        }

        GameApp.clearScreen("black");
        GameApp.startSpriteRendering();

        // wereld beweegt
        PlayerClass.worldX += 300 * delta;

        // achtergrond
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(
                PlayerClass.worldX,
                getWorldWidth(),
                getWorldHeight()
        );

        // basisvloer als fallback
        player.groundLevel = baseGroundLevel;

        // nieuwe segmenten genereren
        if (PlayerClass.worldX + getWorldWidth() > nextSegmentX) {

            SegmentGenerator.GeneratedSegment seg =
                    segmentGenerator.generate(nextSegmentX);

            activePlatforms.addAll(seg.platforms);
            activeObstacles.addAll(seg.obstacles);

            nextSegmentX += 900;
        }

        // platforms updaten + tekenen
        for (PlatformClass p : activePlatforms) {
            p.update(delta);
            p.draw();
        }
        activePlatforms.removeIf(p -> p.x + p.width < 0);

        // platform collision → vloer/verhogingen zoals Kaasje
        for (PlatformClass p : activePlatforms) {
            if (p.playerIsOnTop(player)) {
                int top = (int) (p.y + p.height);
                if (player.yPlayer < top) {
                    player.yPlayer = top;
                }
                player.groundLevel = top;
            }
        }

        // obstacles updaten + tekenen
        for (ObstacleClass o : activeObstacles) {
            o.update(delta);
            o.draw();
        }
        activeObstacles.removeIf(o -> o.x + o.width < 0);

        // physics / movement
        Methodes_Rutger.update(player, unlimitedAmmoPowerupClass);

        // rest van je game logic
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