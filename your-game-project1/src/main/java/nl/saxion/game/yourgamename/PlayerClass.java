package nl.saxion.game.yourgamename;

import java.util.ArrayList;

public class PlayerClass {

    // --- Coins ---
    public int coinsPickedUp;

    // --- Sprite / hitbox ---
    public String filepath = "img/brocolli3.png";

    // ⭐ BELANGRIJK: sprite = 200x200 → hitbox = 200x200
    public int spriteWidth = 120;
    public int spriteHeight = 120;

    // --- Physics ---
    public int velocity = 0;
    public int gravity = 1;
    public int groundLevel = 100;
    public int yPlayer = 100;
    public int jumpCount = 0;

    // --- Shooting ---
    public ArrayList<BulletClass> bullets = new ArrayList<>();
    public int ammo = 5;
    public int maxAmmo = 5;
    public boolean isReloading = false;
    public long reloadStartTime = 0;

    // --- Movement / world ---
    public double speed = 1;
    public double distanceTravelled = 0;
    public static float worldX = 0;

    // --- Parallax ---
    public static float totalPlayTime = 0f;

    // --- Stats ---
    public int totalCoins = 0;
    public double highScore = 0;
    public int enemiesDefeated = 0;
    public int shotsFired = 0;
    public float survivalTime = 0;

    // --- Combat ---
    public boolean isBlocking = false;
}