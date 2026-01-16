package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Methodes_Rutger {
    // Lijst van kogels
    //public static ArrayList<BulletClass> bullets = new ArrayList<>();
    public static ArrayList<CoinClass> coins = new ArrayList<>();
    public static ArrayList<MuzzleFlash> muzzleFlashes = new ArrayList<>();
    private static long lastCoinSpawnTime = 0;
    private static boolean bombOnCooldown = false;
    private static long lastBombTime = 0;
    private static ArrayList<BombClass> bombs = new ArrayList<>();
   private static managerClass managerClasstutorial = new managerClass();
    public static void drawTextWithOutline(String font, String text, int x, int y, String color) {
        int o = 3;

        GameApp.drawText(font, text, x - o, y, "black");
        GameApp.drawText(font, text, x + o, y, "black");
        GameApp.drawText(font, text, x, y - o, "black");
        GameApp.drawText(font, text, x, y + o, "black");

        GameApp.drawText(font, text, x - o, y - o, "black");
        GameApp.drawText(font, text, x + o, y - o, "black");
        GameApp.drawText(font, text, x - o, y + o, "black");
        GameApp.drawText(font, text, x + o, y + o, "black");

        GameApp.drawText(font, text, x, y, color);
    }

    public static void drawTextCenteredWithOutline(String font, String text, int x, int y, String color) {
        int o = 3;

        GameApp.drawTextCentered(font, text, x - o, y, "black");
        GameApp.drawTextCentered(font, text, x + o, y, "black");
        GameApp.drawTextCentered(font, text, x, y - o, "black");
        GameApp.drawTextCentered(font, text, x, y + o, "black");

        GameApp.drawTextCentered(font, text, x - o, y - o, "black");
        GameApp.drawTextCentered(font, text, x + o, y - o, "black");
        GameApp.drawTextCentered(font, text, x - o, y + o, "black");
        GameApp.drawTextCentered(font, text, x + o, y + o, "black");

        GameApp.drawTextCentered(font, text, x, y, color);
    }


    public static void update(PlayerClass player, unlimitedAmmoPowerupClass powerupClass, boolean blockShooting) {

        // --- SPRINGEN ---
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.jumpCount < 2) {
                player.velocity = 20;
                player.jumpCount++;
            }
        }

        // --- BUKKEN ---
        int currentGravity = player.gravity;
        if (GameApp.isKeyPressed(Input.Keys.SHIFT_LEFT) || GameApp.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            currentGravity = player.gravity * 3;
        }

        // --- BEWEGING VERTICAAL ---
        player.velocity -= currentGravity;
        player.yPlayer += player.velocity;

        if (player.yPlayer < player.groundLevel) {
            player.yPlayer = player.groundLevel;
            player.velocity = 0;
            player.jumpCount = 0;
        }

        // --- RELOAD ---
        if (GameApp.isKeyJustPressed(Input.Keys.R) && player.ammo < player.maxAmmo) {
            player.isReloading = true;
            player.reloadStartTime = System.currentTimeMillis();
            GameApp.playSound("Reload", 0.8f);
        }

        if (player.isReloading) {
            long now = System.currentTimeMillis();
            if (now - player.reloadStartTime >= 1500) {
                player.ammo = player.maxAmmo;
                player.isReloading = false;
            }
        }

        // --- KOGELS UPDATEN ---
        for (int i = 0; i < player.bullets.size(); i++) {
            BulletClass b = player.bullets.get(i);
            b.x += b.velocity;
            GameApp.drawTexture("kogel", b.x, b.y, 45, 40);

            if (b.x > GameApp.getWorldWidth()) {
                player.bullets.remove(i);
                i--;
            }
        }

        // --- BROCCOLI TEKENEN ---
        GameApp.drawTexture("brocolli", 100, player.yPlayer, 200, 200);

        // --- SCHIETEN (UITGESCHAKELD TIJDENS TUTORIAL STAP 1) ---
        if (!blockShooting) {

            if (!player.isBlocking && GameApp.isKeyJustPressed(Input.Keys.F) && !player.isReloading) {

                int broccoliX = 100;
                int startX = broccoliX + player.spriteWidth;
                int startY = player.yPlayer + 40 + player.spriteHeight / 2;

                if (powerupClass.isactive) {
                    player.bullets.add(new BulletClass(startX, startY));
                    player.ammo--;
                    muzzleFlashes.add(new MuzzleFlash(startX, startY));
                    GameApp.playSound("shoot");
                    player.shotsFired++;

                } else {
                    if (player.ammo > 0) {
                        player.bullets.add(new BulletClass(startX, startY));
                        player.ammo--;
                        muzzleFlashes.add(new MuzzleFlash(startX, startY));
                        GameApp.playSound("shoot");
                        player.shotsFired++;
                    } else {
                        GameApp.playSound("NoAmmo", 0.8f);
                    }
                }
            }
        }

        // --- POWERUP AMMO CHECK ---
        if (powerupClass.isactive && player.ammo <= 0) {
            powerupClass.isactive = false;
            player.maxAmmo = 5;
            player.isReloading = false;
        }

        // --- MUZZLE FLASH UPDATEN ---
        for (int i = 0; i < muzzleFlashes.size(); i++) {
            MuzzleFlash flash = muzzleFlashes.get(i);

            flash.timer += GameApp.getDeltaTime();
            if (flash.timer >= MuzzleFlash.FRAME_DURATION) {
                flash.timer = 0;
                flash.frameIndex++;
            }

            if (flash.frameIndex < MuzzleFlash.TOTAL_FRAMES) {
                int drawX = flash.x + 75;
                int drawY = flash.y - 22;
                GameApp.drawTexture("muzzleFlash" + flash.frameIndex, drawX, drawY, 64, 64);
            } else {
                muzzleFlashes.remove(i);
                i--;
            }
        }
    }

    public static void updateCoins(PlayerClass player) {
        int broccoliX = 100;
        int broccoliYTop = player.yPlayer;
        int broccoliYBottom = player.yPlayer + player.spriteHeight;

        for (int i = 0; i < coins.size(); i++) {
            CoinClass coin = coins.get(i);

            if (!coin.isCollected) {
                // Munten bewegen naar links
                coin.x -= player.speed * 5;

                // Teken munt
                GameApp.drawTexture("coin", coin.x, coin.y, coin.width, coin.height);

                // Collision check (AABB)
                boolean overlap =
                        broccoliX < coin.x + coin.width &&
                                broccoliX + player.spriteWidth > coin.x &&
                                broccoliYTop < coin.y + coin.height &&
                                broccoliYBottom > coin.y;

                if (overlap) {
                    coin.isCollected = true;
                    player.coinsPickedUp++;
                    GameApp.playSound("coin", 0.25f);
                }

                // Verwijder munt als hij uit beeld is
                if (coin.x + coin.width < 0) {
                    coin.isCollected = true;
                }
            }
        }

        // HIER: echt uit de lijst slopen
        coins.removeIf(c -> c.isCollected || c.x + c.width < 0);
    }


    public static void spawnCoins() {
        long now = System.currentTimeMillis();

        // Check of er minstens 5 seconden voorbij zijn
        if (now - lastCoinSpawnTime >= 5000) {
            lastCoinSpawnTime = now;

            // Spawn helemaal rechts
            int x = (int) GameApp.getWorldWidth();

            // Random hoogte tussen groundLevel en worldHeight - 100
            int minY = 100; // iets boven de grond
            int maxY = (int) (GameApp.getWorldHeight() - 300);
            int y = (int) (Math.random() * (maxY - minY) + minY);

            coins.add(new CoinClass(x, y));
        }
    }

    public static void updateScore(PlayerClass player, double delta) {
        // Tel meters op: snelheid (m/s) * tijd (s)
        player.distanceTravelled += player.speed * delta;

        // Check of huidige afstand groter is dan highscore
        if (player.distanceTravelled > player.highScore) {
            player.highScore = player.distanceTravelled;
        }
    }

    public static void drawMenuText(ScalableGameScreen screen, PlayerClass player) {

        int centerX = (int) (GameApp.getWorldWidth() / 2);

        // Titel
        String title = "Broccoli Blitz";
        int centerY = (int) (GameApp.getWorldHeight() / 2 + 250);
        drawTextCenteredWithOutline("basic", title, centerX, centerY, "white");

        // Subtitel
        String subtitle = "jump to start!";
        int subY = (int) (GameApp.getWorldHeight() / 2 + 150);
        drawTextCenteredWithOutline("basic", subtitle, centerX, subY, "white");

        // Tutorial tekst
        String tutorialText = "Press ESC to start the tutorial";
        int tutY = (int) (GameApp.getWorldHeight() / 2 + 50);
        drawTextCenteredWithOutline("small", tutorialText, centerX, tutY, "white");

        // Statistieken rechtsboven
        String coinsText = "Total Coins: " + player.totalCoins;
        String highScoreText = "High Score: " + String.format("%.1f", player.highScore) + " m";

        int margin = 20;
        int topY = (int) GameApp.getWorldHeight();

        int coinsWidth = (int) GameApp.getTextWidth("small", coinsText);
        int scoreWidth = (int) GameApp.getTextWidth("small", highScoreText);

        drawTextWithOutline("small", coinsText,
                (int) (GameApp.getWorldWidth() - margin - coinsWidth),
                topY - 30,
                "white");

        drawTextWithOutline("small", highScoreText,
                (int) (GameApp.getWorldWidth() - margin - scoreWidth),
                topY - 60,
                "white");
    }

    public static boolean checkDeath(PlayerClass player) {
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE) && GameApp.isKeyPressed(Input.Keys.NUM_9)) {
            // Tellen bij einde ronde
            player.totalCoins += player.coinsPickedUp;
            if (player.distanceTravelled > player.highScore) {
                player.highScore = player.distanceTravelled;
            }

            // Niet resetten hier -> eerst tonen in DeathScreen
            GameApp.switchScreen("DeathScreen");
            return true;
        }
        return false;
    }

    public static void killPlayer(PlayerClass player) {

        player.totalCoins += player.coinsPickedUp;

        if (player.distanceTravelled > player.highScore) {
            player.highScore = player.distanceTravelled;
        }

        GameApp.switchScreen("DeathScreen");
    }

    public static void updateJump(PlayerClass player) {
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.jumpCount < 2) {   // maximaal 2 sprongen
                player.velocity = 20;
                player.jumpCount++;
            }
        }

        player.velocity -= player.gravity;
        player.yPlayer += player.velocity;

        if (player.yPlayer < player.groundLevel) {
            player.yPlayer = player.groundLevel;
            player.velocity = 0;
            player.jumpCount = 0;
        }
    }

    public static float handleMenuBroccoli(PlayerClass player, float broccoliX, boolean isStarting, float delta) {
        // Broccoli beweegt naar startpositie
        if (isStarting && broccoliX > 100) {
            broccoliX -= 300 * delta;
            if (broccoliX < 100) broccoliX = 100;
        }

        // Zodra X bereikt is → start spel
        if (isStarting && broccoliX == 100) {
            GameApp.switchScreen("YourGameScreen");
        }

        // Teken broccoli op huidige X en Y (nu met springen)
        GameApp.drawTexture("brocolli", (int) broccoliX, player.yPlayer, 200, 200);

        return broccoliX;
    }

    public static boolean handleMenuStart(boolean isStarting) {
        // Spatie activeert beweging
        if (!isStarting && GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            isStarting = true;
            // 🔊 Start sound afspelen bij game start
            GameApp.playSound("start", 0.8f); // iets zachter volume

        }
        return isStarting; // geef de bijgewerkte waarde terug
    }

    public static void drawGameHud(PlayerClass player) {
        int margin = 20;
        int topY = (int) GameApp.getWorldHeight();

        String coins = "Coins: " + player.coinsPickedUp;
        String ammo = "Ammo: " + player.ammo + "/" + player.maxAmmo;
        String distance = "Distance: " + String.format("%.1f", player.distanceTravelled) + " m";
        String highScore = "High Score: " + String.format("%.1f", player.highScore) + " m";

        int coinsWidth = (int) GameApp.getTextWidth("small", coins);
        int ammoWidth = (int) GameApp.getTextWidth("small", ammo);
        int distWidth = (int) GameApp.getTextWidth("small", distance);
        int hsWidth = (int) GameApp.getTextWidth("small", highScore);

        int xCoins = (int) (GameApp.getWorldWidth() - margin - coinsWidth);
        int xAmmo = (int) (GameApp.getWorldWidth() - margin - ammoWidth);
        int xDist = (int) (GameApp.getWorldWidth() - margin - distWidth);
        int xHS   = (int) (GameApp.getWorldWidth() - margin - hsWidth);

        int y = topY - 30;

        drawTextWithOutline("small", coins, xCoins, y, "white");
        drawTextWithOutline("small", ammo, xAmmo, y - 30, "white");
        drawTextWithOutline("small", distance, xDist, y - 60, "white");
        drawTextWithOutline("small", highScore, xHS, y - 90, "white");
    }

    public static void drawTextRight(String fontKey, String text, int marginRight, int y, String color) {
        int textWidth = (int) GameApp.getTextWidth(fontKey, text); // breedte van de tekst
        int x = (int) (GameApp.getWorldWidth() - marginRight - textWidth); // corrigeer zodat tekst rechts staat
        GameApp.drawText(fontKey, text, x, y, color);
    }

    public static void checkBulletHitsEnemy(PlayerClass player, EnemyClass enemy,managerClass managerClass) {
        if (enemy.allEnemies.isEmpty()) return;
        EnemyClass currentenemy = enemy.allEnemies.get(0);
        for (int i = 0; i < player.bullets.size(); i++) {
            BulletClass bullet = player.bullets.get(i);
            if (currentenemy.type == 1) {


                boolean overlapX = bullet.x < currentenemy.enemyXPos + 100 && bullet.x + 90 > currentenemy.enemyXPos;
                boolean overlapY = bullet.y < currentenemy.enemyYPos + 140 && bullet.y + 75 > currentenemy.enemyYPos;

                if (overlapX && overlapY && !enemy.allEnemies.isEmpty()) {
                    player.bullets.remove(i);
                    GameApp.addSound("Enemydood", "Sounds/enemydoosounds.mp3");
                    GameApp.playSound("Enemydood");
                    i--;
                    managerClass.enemyActive = false;
                    player.enemiesDefeated++; // ✅ kill registreren
                    enemy.allEnemies.remove(0);
                    enemy.type = 0;
                }
            } else if (currentenemy.type == 2) {
                boolean overlapX = bullet.x < currentenemy.enemyXPos + 100 && bullet.x + 45 > currentenemy.enemyXPos;
                boolean overlapY = bullet.y < currentenemy.enemyYPos + 140 && bullet.y + 40 > currentenemy.enemyYPos;

                if (overlapX && overlapY && !enemy.allEnemies.isEmpty()) {
                    player.bullets.remove(i);
                    GameApp.addSound("Enemydood", "Sounds/enemydoosounds.mp3");
                    GameApp.playSound("Enemydood");
                    i--;
                    managerClass.enemyActive = false;
                    player.enemiesDefeated++; // ✅ kill registreren
                    enemy.allEnemies.remove(0);
                    enemy.type = 0;
                }
            } else if (currentenemy.type == 3) {
                boolean overlapX = bullet.x < currentenemy.enemyXPos + 100 && bullet.x + 45 > currentenemy.enemyXPos;
                boolean overlapY = bullet.y < currentenemy.enemyYPos + 140 && bullet.y + 40 > currentenemy.enemyYPos;

                if (overlapX && overlapY && !enemy.allEnemies.isEmpty() && currentenemy.hp > 1) {
                    player.bullets.remove(i);
                    currentenemy.hp--;
                    i--;
                } else if (overlapX && overlapY && !enemy.allEnemies.isEmpty() && currentenemy.hp == 1) {
                    player.bullets.remove(i);
                    GameApp.addSound("Enemydood", "Sounds/enemydoosounds.mp3");
                    GameApp.playSound("Enemydood");
                    i--;
                    player.enemiesDefeated++; // ✅ kill registreren
                    enemy.allEnemies.remove(0);
                    enemy.type = 0;
                    managerClass.enemyActive = false;
                }
            }
        }
    }


    public static void registerEnemyKill(PlayerClass player) {
        player.enemiesDefeated++;
    }
    public static void registerShot(PlayerClass player) {
        player.shotsFired++;
    }
    public static void updateSurvivalTime(PlayerClass player, float delta) {
        player.survivalTime += delta;
    }
    public static void drawDeathStats(PlayerClass player, ScalableGameScreen screen) {
        float leftX = 50;
        float topY = screen.getWorldHeight() - 100;
        float line = 40;

        GameApp.drawText("basic", "GAME OVER", leftX, topY, "white");
        GameApp.drawText("small", "Coins: " + player.totalCoins, leftX, topY - line * 1, "white");
        GameApp.drawText("small", "Score: " + String.format("%.1f", player.distanceTravelled), leftX, topY - line * 2, "white");
        GameApp.drawText("small", "Verslagen enemies: " + player.enemiesDefeated, leftX, topY - line * 3, "white");
        GameApp.drawText("small", "Aantal keer geschoten: " + player.shotsFired, leftX, topY - line * 4, "white");
        GameApp.drawText("small", "Overlevingstijd: " + (int) player.survivalTime + "s", leftX, topY - line * 5, "white");
        GameApp.drawText("small", "Druk [M] voor hoofdmenu", leftX, topY - line * 7, "white");
    }
    public static void resetRoundStats(PlayerClass player) {
        // Ronde-specifieke statistieken
        player.coinsPickedUp = 0;
        player.enemiesDefeated = 0;
        player.shotsFired = 0;
        player.survivalTime = 0f;
        player.distanceTravelled = 0.0;

        // Spelerstatus
        player.yPlayer = player.groundLevel;
        player.velocity = 0;
        player.jumpCount = 0;
        player.ammo = player.maxAmmo;

        // Lijsten opruimen
        player.bullets.clear();
        coins.clear();
        muzzleFlashes.clear();
    }
    public static void updateBomb(PlayerClass player, EnemyClass enemy) {

        // --- BOM GOOIEN ---
        if (GameApp.isKeyJustPressed(Input.Keys.G) && !bombOnCooldown) {
            int startX = 100 + player.spriteWidth;
            int startY = player.yPlayer + player.spriteHeight / 2;

            bombs.add(new BombClass(startX, startY));
            bombOnCooldown = true;
            lastBombTime = System.currentTimeMillis();
        }

        // --- COOLDOWN RESETTEN ---
        if (bombOnCooldown) {
            long now = System.currentTimeMillis();
            if (now - lastBombTime >= 10000) {
                bombOnCooldown = false;
            }
        }

        // --- BOMMEN UPDATEN ---
        for (int i = 0; i < bombs.size(); i++) {
            BombClass bomb = bombs.get(i);
            bomb.update();

            // --- IMPACT MET GROND ---
            if (!bomb.exploded && bomb.y <= player.groundLevel) {
                bomb.y = player.groundLevel;
                bomb.exploded = true;
                bomb.frameIndex = 5;
                GameApp.playSound("Bomb");
            }

            // --- IMPACT MET ENEMY (DIRECTE HIT) ---
            if (!bomb.exploded && enemy != null && !enemy.allEnemies.isEmpty()) {

                for (int e = 0; e < enemy.allEnemies.size(); e++) {
                    EnemyClass en = enemy.allEnemies.get(e);

                    boolean overlapX = bomb.x < en.enemyXPos + 100 &&
                            bomb.x + 128 > en.enemyXPos;

                    boolean overlapY = bomb.y < en.enemyYPos + 100 &&
                            bomb.y + 128 > en.enemyYPos;

                    if (overlapX && overlapY) {
                        bomb.exploded = true;
                        bomb.frameIndex = 5;
                        GameApp.playSound("Bomb");
                        break; // bom is ontploft → klaar
                    }
                }
            }

            // --- RADIUS DAMAGE (ALLEEN NA EXPLOSIE!) ---
            if (bomb.exploded && enemy != null && !enemy.allEnemies.isEmpty()) {

                float bombCenterX = bomb.x + 64;
                float bombCenterY = bomb.y + 64;

                for (int e = 0; e < enemy.allEnemies.size(); e++) {

                    EnemyClass en = enemy.allEnemies.get(e);

                    float enemyCenterX = en.enemyXPos + 50;
                    float enemyCenterY = en.enemyYPos + 50;

                    float dx = bombCenterX - enemyCenterX;
                    float dy = bombCenterY - enemyCenterY;
                    float distance = (float)Math.sqrt(dx * dx + dy * dy);

                    if (distance <= BombClass.EXPLOSION_RADIUS) {
                        en.enemyIsDead = true;
                        player.enemiesDefeated++;
                        enemy.allEnemies.remove(e);
                        e--; // lijst corrigeren
                    }
                }
            }

            // --- BOM TEKENEN ---
            if (bomb.frameIndex >= 1 && bomb.frameIndex <= BombClass.TOTAL_FRAMES) {
                GameApp.drawTexture("bom" + bomb.frameIndex, bomb.x, bomb.y, 128, 128);
            }

            // --- BOM VERWIJDEREN NA ANIMATIE ---
            if (bomb.exploded && bomb.frameIndex == BombClass.TOTAL_FRAMES) {
                bombs.remove(i);
                i--;
            }
        }
    }
    public static int getBombCooldownRemaining() {
        if (!bombOnCooldown) {
            return 0; // direct beschikbaar
        }
        long now = System.currentTimeMillis();
        long elapsed = now - lastBombTime;
        int remaining = (int) ((10000 - elapsed) / 1000); // in seconden
        return Math.max(0, remaining);
    }
    public static void drawBombCooldown() {
        int margin = 20;
        int topY = (int) GameApp.getWorldHeight();

        int remaining = getBombCooldownRemaining();

        String text = (remaining == 0) ? "Bomb: READY" : "Bomb: " + remaining + "s";

        drawTextWithOutline("small", text, margin, topY - 30, "white");
    }


    public static void drawTutorialText(ScalableGameScreen screen, int step) {
        int centerX = (int) (GameApp.getWorldWidth() / 2);
        int centerY = (int) (GameApp.getWorldHeight() / 2);

        String text;
        switch (step) {
            case 1: text = "Press [F] to SHOOT"; break;
            case 2: text = "Press [R] to RELOAD"; break;
            case 3: text = "Press [G] to throw a BOMB"; break;
            case 4: text = "Collect the COIN"; break;
            case 5: text = "Defeat the ENEMY"; break;
            default: text = "Tutorial complete! Press [M] for Menu"; break;
        }

        drawTextCenteredWithOutline("basic", text, centerX, centerY, "white");
    }

    // --- Tutorial logica per stap ---
    public static boolean tutorialShoot(PlayerClass player) {
        if (GameApp.isKeyJustPressed(Input.Keys.F) && player.ammo > 0) {

            int startX = 100 + player.spriteWidth;
            int startY = player.yPlayer + player.spriteHeight / 2 + 47; // 30 pixels hoger

            player.bullets.add(new BulletClass(startX, startY));
            player.ammo--;
            GameApp.playSound("shoot");

            return true; // stap gehaald
        }
        return false;
    }

    public static boolean tutorialReload(PlayerClass player) {
        if (GameApp.isKeyJustPressed(Input.Keys.R)) {
            player.isReloading = true;
            player.reloadStartTime = System.currentTimeMillis();
            GameApp.playSound("Reload", 0.8f);
            return true;
        }
        return false;
    }

    public static boolean tutorialBomb(PlayerClass player, EnemyClass enemy) {
        if (GameApp.isKeyJustPressed(Input.Keys.G)) {
            bombs.add(new BombClass(100 + player.spriteWidth, player.yPlayer + player.spriteHeight / 2));
            GameApp.playSound("shoot");
            return true;
        }
        return false;
    }
    public static boolean tutorialCoin(PlayerClass player) {
        // Als er GEEN coins meer zijn → spawn er één nieuwe
        if (coins.isEmpty()) {
            int x = (int) GameApp.getWorldWidth() - 200;
            int y = (int) (GameApp.getWorldHeight() / 2);
            coins.add(new CoinClass(x, y));
        }

        // Coins bewegen + collision
        updateCoins(player);

        // Ga door naar volgende stap zodra speler er één heeft gepakt
        return player.coinsPickedUp > 0;
    }

    public static boolean tutorialEnemy(PlayerClass player, EnemyClass enemy) {
        if (!enemy.enemyIsDead) {
            checkBulletHitsEnemy(player, enemy,managerClasstutorial);
        }
        return enemy.enemyIsDead;
    }
    // --- Tutorial coin spawn (eenmalig) ---
    private static boolean tutorialCoinSpawned = false;

    public static void spawnTutorialCoin() {
        if (!tutorialCoinSpawned) {
            int x = (int) GameApp.getWorldWidth() - 200;
            int y = (int) (GameApp.getWorldHeight() / 2);
            coins.add(new CoinClass(x, y));
            tutorialCoinSpawned = true;
        }
    }

    // Reset flag zodat coin opnieuw kan verschijnen bij nieuwe ronde/tutorial
    public static void resetTutorialCoin() {
        tutorialCoinSpawned = false;
    }
    public static void checkBulletHitsTutorialEnemy(PlayerClass player, EnemyClass enemy,managerClass managerClass) {
        // simpele hit-detectie voor EEN enkele enemy (geen allEnemies-lijst)
        for (int i = 0; i < player.bullets.size(); i++) {
            BulletClass bullet = player.bullets.get(i);

            boolean overlapX = bullet.x < enemy.enemyXPos + 100 && bullet.x + 90 > enemy.enemyXPos;
            boolean overlapY = bullet.y < enemy.enemyYPos + 100 && bullet.y + 75 > enemy.enemyYPos;

            if (overlapX && overlapY && !enemy.enemyIsDead) {
                // kogel verwijderen
                player.bullets.remove(i);
                i--;

                // sound + stats
                GameApp.addSound("Enemydood", "Sounds/enemydoosounds.mp3");
                GameApp.playSound("Enemydood");
                player.enemiesDefeated++;

                // enemy markeren als dood
                enemy.enemyIsDead = true;
                break;
            }
        }
    }
    public static void resetTutorial(PlayerClass player) {
        // Reset tutorial stap
        player.coinsPickedUp = 0;

        // Reset coin systeem
        coins.clear();
        tutorialCoinSpawned = false;

        // Reset bommen
        bombs.clear();

        // Reset speler stats
        player.bullets.clear();
        player.enemiesDefeated = 0;
        player.shotsFired = 0;

        // Reset enemy gerelateerde dingen gebeuren in SettingsScreen zelf
    }
    public static void updatePowerupTimer(float delta, PowerupClass powerUp, SchildClass schildClass) {

        if (powerUp == null) return;
        if (!powerUp.hasTimer) return;
        if (powerUp.timeLeft <= 0) return;

        powerUp.timeLeft -= delta;

        if (powerUp.timeLeft <= 0) {
            powerUp.timeLeft = 0;
            powerUp.hasTimer = false;

            schildClass.isactive = false;
            schildClass.HP = 0;   // ❗ BELANGRIJK
        }
    }
    public static void drawPowerupTimer(PowerupClass powerUp) {

        if (powerUp == null) return;
        if (!powerUp.hasTimer) return;
        if (powerUp.timeLeft <= 0) return;

        float topY = GameApp.getWorldHeight();

        String text = "Powerup: " + String.format("%.1f", powerUp.timeLeft) + "s";

        GameApp.drawText("small", text, 20, topY - 65, "white");
    }
    public static void updateBlocking(PlayerClass player) {

        // Block aan/uit
        if (GameApp.isKeyPressed(Input.Keys.E)) {
            player.isBlocking = true;
        } else {
            player.isBlocking = false;
        }

        // Block sprite tekenen
        if (player.isBlocking) {
            GameApp.drawTexture("block", 130, player.yPlayer-50 + 20, 200,150);
        }
    }
    public static boolean checkSegmentObstacleCollision(PlayerClass player, ArrayList<ObstacleClass> obstacles) {
        for (ObstacleClass o : obstacles) {
            if (o.collidesWith(player)) {
                return true;
            }
        }
        return false;
    }


}

