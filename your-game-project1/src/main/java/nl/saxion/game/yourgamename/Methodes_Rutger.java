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
    public static ArrayList<BulletClass> bullets = new ArrayList<>();
    public static ArrayList<CoinClass> coins = new ArrayList<>();
    public static ArrayList<MuzzleFlash> muzzleFlashes = new ArrayList<>();
    private static long lastCoinSpawnTime = 0;



    // Speler update (springen, bukken, tekenen, schieten)
    public static void update(PlayerClass player) {
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
        if (GameApp.isKeyJustPressed(Input.Keys.R) && !player.isReloading) {
            player.isReloading = true;
            player.reloadStartTime = System.currentTimeMillis();
        }
        if (player.isReloading) {
            long now = System.currentTimeMillis();
            if (now - player.reloadStartTime >= 1500) { // 1.5 seconden
                player.ammo = player.maxAmmo;
                player.isReloading = false;
            }
        }

        // --- KOGELS UPDATEN ---
        for (int i = 0; i < bullets.size(); i++) {
            BulletClass b = bullets.get(i);
            b.x += b.velocity;
            GameApp.drawTexture("kogel", b.x, b.y, 90, 75);

            if (b.x > GameApp.getWorldWidth()) {
                bullets.remove(i);
                i--;
            }
        }

        // --- TEKENEN VAN DE BROCCOLI ---
        GameApp.drawTexture("brocolli", 100, player.yPlayer, 200, 200);

        // --- SCHIETEN + MUZZLE FLASH + SOUND ---
        if (GameApp.isKeyJustPressed(Input.Keys.F) && player.ammo > 0 && !player.isReloading) {
            int broccoliX = 100;
            int startX = broccoliX + player.spriteWidth;
            int startY = player.yPlayer + player.spriteHeight / 2;

            // Voeg kogel toe
            bullets.add(new BulletClass(startX, startY));
            player.ammo--;

            // Start muzzle flash (offset zodat hij bij de loop zit)
            muzzleFlashes.add(new MuzzleFlash(startX, startY));

            // Geluid afspelen
            GameApp.playSound("shoot");
        }

        // --- MUZZLE FLASH UPDATEN/TEKENEN ---
        for (int i = 0; i < muzzleFlashes.size(); i++) {
            MuzzleFlash flash = muzzleFlashes.get(i);

            // Tijd bijwerken
            flash.timer += GameApp.getDeltaTime();
            if (flash.timer >= MuzzleFlash.FRAME_DURATION) {
                flash.timer = 0;
                flash.frameIndex++;
            }

            // Frame tekenen zolang we binnen range zitten
            if (flash.frameIndex < MuzzleFlash.TOTAL_FRAMES) {
                // Pas desnoods deze offsets aan voor perfecte uitlijning
                int drawX = flash.x + 75;   // kleine offset naar rechts vanaf broccoli
                int drawY = flash.y - 22;   // kleine offset omlaag richting loop
                GameApp.drawTexture("muzzleFlash" + flash.frameIndex, drawX, drawY, 64, 64);
            } else {
                // Animatie klaar -> verwijderen
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
        // Titel (midden)
        String title = "Broccoli Blitz";
        int centerX = (int) (GameApp.getWorldWidth() / 2);
        int centerY = (int) (GameApp.getWorldHeight() / 2 + 200);
        GameApp.drawTextCentered("basic", title, centerX, centerY, "white");

        // Subtitel (midden)
        String subtitle = "jump to start!";
        int subY = (int) (GameApp.getWorldHeight() / 2 + 100);
        GameApp.drawTextCentered("basic", subtitle, centerX, subY, "white");

        // Statistieken rechtsboven (kleiner font)
        String coinsText = "Total Coins: " + player.totalCoins;
        String highScoreText = "High Score: " + String.format("%.1f", player.highScore) + " m";

        int margin = 20;
        int topY = (int) GameApp.getWorldHeight();

        // Bereken breedte van de tekst zodat hij rechts uitgelijnd staat
        int coinsWidth = (int) GameApp.getTextWidth("small", coinsText);
        int scoreWidth = (int) GameApp.getTextWidth("small", highScoreText);

        GameApp.drawText("small", coinsText, GameApp.getWorldWidth() - margin - coinsWidth, topY - 30, "white");
        GameApp.drawText("small", highScoreText, GameApp.getWorldWidth() - margin - scoreWidth, topY - 60, "white");
    }

    public static boolean checkDeath(PlayerClass player) {
        if (GameApp.isKeyPressed(Input.Keys.ESCAPE) && GameApp.isKeyPressed(Input.Keys.NUM_9)) {
            // Munten en highscore bijwerken
            player.totalCoins += player.coinsPickedUp;
            if (player.distanceTravelled > player.highScore) {
                player.highScore = player.distanceTravelled;
            }

            // Reset ronde waarden
            player.yPlayer = player.groundLevel;
            player.velocity = 0;
            player.jumpCount = 0;
            player.coinsPickedUp = 0;
            player.ammo = player.maxAmmo;
            player.distanceTravelled = 0;

            // Naar deathscreen
            GameApp.switchScreen("DeathScreen");
            return true;
        }
        return false;
    }
    public static void updateJump(PlayerClass player) {
        // --- SPRINGEN ---
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.jumpCount < 2) {   // maximaal 2 sprongen
                player.velocity = 20;
                player.jumpCount++;
            }
        }

        // --- GRAVITY ---
        player.velocity -= player.gravity;
        player.yPlayer += player.velocity;

        // --- LANDING ---
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
        GameApp.drawTexture("brocolli", (int)broccoliX, player.yPlayer,200,200);

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

        // Coins
        drawTextRight("small", "Coins: " + player.coinsPickedUp, margin, topY - 30, "white");

        // Ammo
        drawTextRight("small", "Ammo: " + player.ammo + "/" + player.maxAmmo, margin, topY - 60, "white");

        // Distance
        drawTextRight("small", "Distance: " + String.format("%.1f", player.distanceTravelled) + " m",
                margin, topY - 90, "white");

        // Highscore
        drawTextRight("small", "High Score: " + String.format("%.1f", player.highScore) + " m",
                margin, topY - 120, "white");
    }

    public static void drawTextRight(String fontKey, String text, int marginRight, int y, String color) {
        int textWidth = (int) GameApp.getTextWidth(fontKey, text); // breedte van de tekst
        int x = (int) (GameApp.getWorldWidth() - marginRight - textWidth); // corrigeer zodat tekst rechts staat
        GameApp.drawText(fontKey, text, x, y, color);
    }

    public static void checkBulletHitsEnemy(EnemyClass enemy) {
        for (int i = 0; i < bullets.size(); i++) {
            BulletClass bullet = bullets.get(i);

            // AABB collision check
            boolean overlapX = bullet.x < enemy.enemyXPos + 100 && bullet.x + 90 > enemy.enemyXPos;
            boolean overlapY = bullet.y < enemy.enemyYPos + 100 && bullet.y + 75 > enemy.enemyYPos;

            if (overlapX && overlapY && !enemy.enemyIsDead) {
                enemy.enemyIsDead = true;
                bullets.remove(i); // verwijder de kogel
                i--; // index corrigeren
                break; // enemy geraakt, klaar
            }
        }
    }
    }
