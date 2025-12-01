package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;
import java.util.ArrayList;

public class Methodes_Rutger {
    // Lijst van kogels
    public static ArrayList<BulletClass> bullets = new ArrayList<>();

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
            if (now - Player.reloadStartTime >= 750) { // 1.5 seconden
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

      //  // --- AMMO HUD RECHTSBOVEN ---
     //   GameApp.drawText("Ammo: " + Player.ammo + "/" + Player.maxAmmo, String.valueOf(24, GameApp.getWorldHeight() - 30, GameApp.getWorldWidth() - 30, Color.WHITE);

    }
    }