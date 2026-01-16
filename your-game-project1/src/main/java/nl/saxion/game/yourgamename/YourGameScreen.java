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
    public managerClass managerClass;

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

        flamethrowerClass = new flamethrowerClass(-2000, 0);

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
        for (int i = 1; i <= 10; i++) {
            GameApp.addTexture("flame" + i, "img/flame2/flame2." + i + ".png");
        }

        GameApp.addSound("shoot", "Sounds/Schieten.mp3");
        GameApp.addSound("coin", "Sounds/coin.mp3");
        GameApp.addSound("Reload", "Sounds/Reload.mp3");
        GameApp.addSound("NoAmmo", "Sounds/NoAmmo.mp3");
        GameApp.addSound("Bomb", "Sounds/explosie.mp3");
        GameApp.addSound("block", "Sounds/block.mp3");

        enemyClass = new EnemyClass("img/chef.png", "chef", "img/ketchup.png", "enemy2", 1100, 150, 1000);

        String[] knifeFrames = {
                "img/knife1.png",
                "img/knife2.png",
                "img/knife3.png",
                "img/knife4.png",
                "img/knife5.png",
                "img/knife6.png",
                "img/knife7.png",
                "img/knife8.png"
        };
        projectileClass = new ProjectileClass(
                knifeFrames,
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

        powerupClassSchild = new PowerupClass(
                "img/schild.png",
                "schild",
                "img/unlimitedKogels.png",
                "unlimitedkogels"
        );

        managerClass = new managerClass();

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
        PlayerClass.worldX += 600 * delta;

        // achtergrond
        Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(
                PlayerClass.worldX,
                getWorldWidth(),
                getWorldHeight()
        );

        // basisvloer als fallback
        player.groundLevel = baseGroundLevel;

        // nieuwe segmenten genereren
        if (managerClass.spawnObstacle) {
            managerClass.spawnObstacle = false;
            managerClass.obstacleActive = true;
            SegmentGenerator.GeneratedSegment seg =
                    segmentGenerator.generate(nextSegmentX,enemyClass);

            activePlatforms.addAll(seg.platforms);
            activeObstacles.addAll(seg.obstacles);

        }

        // platforms updaten + tekenen
        for (PlatformClass p : activePlatforms) {
            p.update(delta);
            p.draw();
        }

        activePlatforms.removeIf(p -> p.x + p.width < 0);

// ⭐ PLATFORM COLLISION — massieve raises
        for (PlatformClass p : activePlatforms) {

            // Player (broccoli)
            int px1 = 100;
            int px2 = px1 + (int) player.spriteWidth;
            int feet = (int) player.yPlayer;
            int head = feet + (int) player.spriteHeight;

            // Platform hitbox
            int platLeft   = (int) p.x;
            int platRight  = platLeft + (int) p.width;
            int platBottom = (int) p.y;
            int platTop    = platBottom + (int) p.height;

            boolean horizontalOverlap = px2 > platLeft && px1 < platRight;
            boolean verticalOverlap   = head > platBottom && feet < platTop;
            boolean falling           = player.velocity < 0;

            // ⭐ TOP COLLISION — landen op raise
            // Voeten gaan door platTop heen terwijl je valt
            if (horizontalOverlap && falling && head > platTop && feet <= platTop) {
                player.yPlayer   = platTop;              // voeten op bovenkant
                player.groundLevel = platTop;
                player.velocity  = 0;
                continue;
            }

            // ⭐ SIDE COLLISION — tegen zijkant knallen = dood
            // Je overlapt verticaal met het platform, maar staat er niet bovenop
            if (horizontalOverlap && verticalOverlap && !(feet >= platTop - 2)) {
                Methodes_Rutger.killPlayer(player);
                GameApp.endSpriteRendering();
                return;
            }
        }

        // obstacles updaten + tekenen
        for (ObstacleClass o : activeObstacles) {
            o.update(delta);
            o.draw();
        }
        activeObstacles.removeIf(o -> o.x + o.width < 0);

        if (managerClass.obstacleActive) {
            if (activePlatforms.isEmpty() || activeObstacles.isEmpty()) {
                managerClass.obstacleActive = false;
            } else if (activePlatforms.getLast().x < 300 && activeObstacles.getLast().x < 300) {
                managerClass.obstacleActive = false;
            }
        }

// ⭐ SPIKE COLLISION — kleine maar iets grotere hitbox
        // ⭐ SPIKE COLLISION — volledige spike, iets smaller
        for (ObstacleClass o : activeObstacles) {

            // Player hitbox
            int px1 = 100;
            int px2 = px1 + (int) player.spriteWidth;
            int py1 = (int) player.yPlayer;
            int py2 = py1 + (int) player.spriteHeight;

            // Spike volledige hitbox
            int ox1_full = (int) o.x;
            int ox2_full = ox1_full + (int) o.width;
            int oy1_full = (int) o.y;
            int oy2_full = oy1_full + (int) o.height;

            // 🔧 Maak spike iets smaller (fairness)
            int shrinkX = (int) (o.width * 0.4f); // 20% eraf links/rechts

            int ox1 = ox1_full + shrinkX;
            int ox2 = ox2_full - shrinkX;

            int oy1 = oy1_full;
            int oy2 = oy2_full;

            boolean intersects =
                    px2 > ox1 &&
                            px1 < ox2 &&
                            py2 > oy1 &&
                            py1 < oy2;

            if (intersects) {
                Methodes_Rutger.killPlayer(player);
                GameApp.endSpriteRendering();
                return;
            }
        }


        // physics / movement
        Methodes_Rutger.update(player, unlimitedAmmoPowerupClass, false);

        // rest van je game logic
        Methodes_Rutger.spawnCoins();
        Methodes_Rutger.updateCoins(player);
        Methodes_Rutger.updateScore(player, delta);
        Methodes_Rutger.drawGameHud(player);
        Methodes_Rutger.drawBombCooldown();


        Methodes_Maxje.updateEnemies(delta, enemyClass,subEnemyClass,flamethrowerClass,managerClass);
        Methodes_Rutger.checkBulletHitsEnemy(player, enemyClass,managerClass);
        Methodes_Maxje.checkCollsionMes(projectileClass, player);
        Methodes_Maxje.checkCollisionEnemy(player, enemyClass, subEnemyClass, schildClass, powerupClassSchild);
        Methodes_Maxje.checkForPowerupPickup(player, powerupClassSchild,managerClass);
        Methodes_Maxje.updateSchildPowerup(delta, powerupClassSchild, schildClass);
        Methodes_Maxje.updateunlimitedKogels(delta, powerupClassSchild, unlimitedAmmoPowerupClass, player);
        Methodes_Maxje.unlimitedKogelsLogic(delta, unlimitedAmmoPowerupClass, player, powerupClassSchild);
        Methodes_Maxje.activeSchildUpdate(schildClass, player);
        Methodes_Maxje.selectEnemyWillekeurig(delta, enemyClass,managerClass);
        Methodes_Maxje.checkShieldCollisionKnife(schildClass, projectileClass, player);
        Methodes_Rutger.updateSurvivalTime(player, delta);
        Methodes_Rutger.updateBlocking(player);
        Methodes_Rutger.updateBomb(player, enemyClass);
        Methodes_Maxje.addMes(delta, projectileClass, enemyClass);
        Methodes_Maxje.updateMes(delta, projectileClass);
        Methodes_Rutger.updatePowerupTimer(delta, powerupClassSchild, schildClass);
        Methodes_Rutger.drawPowerupTimer(powerupClassSchild);
        Methodes_Maxje.genereerRandomPowerup(powerupClassSchild,delta);
        Methodes_Maxje.tekenFlamethrower(delta, flamethrowerClass, enemyClass);
        Methodes_Maxje.checkFlamethrowerCollision(flamethrowerClass,player,schildClass);
        Methodes_Maxje.addSpatel(delta,projectileClass,enemyClass);
        Methodes_Maxje.updateSpatel(delta,projectileClass);
        Methodes_Maxje.checkCollsionSpatel(projectileClass,player);
        Methodes_Maxje.generateGameLogic(managerClass,delta);
        Methodes_Maxje.updateManagerTimer(managerClass,delta);

        // 1. Enemies updaten + tekenen
        Methodes_Maxje.updateEnemies(delta, enemyClass, subEnemyClass, flamethrowerClass,managerClass);

// 2. Flame tekenen
        Methodes_Maxje.tekenFlamethrower(delta, flamethrowerClass, enemyClass);

// 3. Flame collision checken
        Methodes_Maxje.checkFlamethrowerCollision(flamethrowerClass, player,schildClass);

        // 🔥 DEBUG HITBOX FLAME (rechts → links)
        if (Methodes_Maxje.DEBUG_FLAME && flamethrowerClass != null && flamethrowerClass.frame > 0) {

            int f = flamethrowerClass.frame;

            // Sprite beginpunt
            float drawX = flamethrowerClass.x - 450;
            float drawY = flamethrowerClass.y;

            // Flame groeit van rechts naar links
            float flameRight = drawX + 500;
            float flameLeft  = flameRight - Methodes_Maxje.flameHitboxWidth[f];

            // Debug‑texture tekenen
            GameApp.drawTexture(
                    "platform",          // jouw debug PNG
                    flameLeft,           // x‑positie
                    drawY,               // y‑positie
                    Methodes_Maxje.flameHitboxWidth[f], // breedte per frame
                    100                  // hoogte van flame
            );
        }


        GameApp.endSpriteRendering();
    }

    @Override
    public void hide() {
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
    }
}