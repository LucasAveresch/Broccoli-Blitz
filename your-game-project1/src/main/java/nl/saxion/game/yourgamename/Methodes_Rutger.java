package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Methodes_Rutger {
    // Lijst van kogels
    public static ArrayList<BulletClass> bullets = new ArrayList<>();
    public static ArrayList<CoinClass> coins = new ArrayList<>();
    private static long lastCoinSpawnTime = 0;



    // Speler update (springen, bukken, tekenen)
    public static void update(PlayerClass Player, String filepath) {
        // --- SPRINGEN ---
        if (GameApp.isKeyJustPressed(Input.Keys.SPACE)) {
            if (Player.jumpCount < 2) {
                Player.velocity = 20;
                Player.jumpCount++;
            }
        }

        // --- BUKKEN ---
        int currentGravity = Player.gravity;
        if (GameApp.isKeyPressed(Input.Keys.SHIFT_LEFT) || GameApp.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            currentGravity = Player.gravity * 3;
        }

        Player.velocity -= currentGravity;
        Player.yPlayer += Player.velocity;

        if (Player.yPlayer < Player.groundLevel) {
            Player.yPlayer = Player.groundLevel;
            Player.velocity = 0;
            Player.jumpCount = 0;
        }

        // --- RELOAD ---
        if (GameApp.isKeyJustPressed(Input.Keys.R) && !Player.isReloading) {
            Player.isReloading = true;
            Player.reloadStartTime = System.currentTimeMillis();
        }
        if (Player.isReloading) {
            long now = System.currentTimeMillis();
            if (now - Player.reloadStartTime >= 250) { // 1.5 seconden
                Player.ammo = Player.maxAmmo;
                Player.isReloading = false;
            }
        }

        // --- SCHIETEN ---
        if (GameApp.isKeyJustPressed(Input.Keys.F) && Player.ammo > 0 && !Player.isReloading) {
            int broccoliX = 100;
            int startX = broccoliX + Player.spriteWidth;
            int startY = Player.yPlayer + Player.spriteHeight / 2;
            bullets.add(new BulletClass(startX, startY));
            Player.ammo--; // verlaag kogels
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
        GameApp.drawTexture("brocolli", 100, Player.yPlayer);


      // --- AMMO HUD RECHTSBOVEN ---
        GameApp.drawText("default", "Ammo: " + Player.ammo + "/" + Player.maxAmmo,
                GameApp.getWorldWidth() - 150, GameApp.getWorldHeight() - 30, "white");

    }

    public static void updateCoins(PlayerClass player) {
        int broccoliX = 100;
        int broccoliYTop = player.yPlayer;
        int broccoliYBottom = player.yPlayer + player.spriteHeight;

        for (int i = 0; i < coins.size(); i++) {
            CoinClass coin = coins.get(i);

            if (!coin.isCollected) {
                // Munten bewegen naar links
                coin.x -= player.speed*5;

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
                }

                // Verwijder munt als hij uit beeld is
                if (coin.x + coin.width < 0) {
                    coin.isCollected = true;
                }
            }
        }

        // HUD rechtsboven
        GameApp.drawText("default", "Coins: " + player.coinsPickedUp,
                GameApp.getWorldWidth() - 150,
                GameApp.getWorldHeight() - 60,
                "white");

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
            int y = (int)(Math.random() * (maxY - minY) + minY);

            coins.add(new CoinClass(x, y));
        }
    }
    public static void updateScore(PlayerClass player, double delta) {
        // Tel meters op: snelheid (m/s) * tijd (s)
        player.distanceTravelled += player.speed * delta;

        // HUD rechtsboven
        GameApp.drawText("default", "Distance: " + String.format("%.1f", player.distanceTravelled) + " m",
                GameApp.getWorldWidth() - 150, GameApp.getWorldHeight() - 90, "white");
    }
    }