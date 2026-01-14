package nl.saxion.game.yourgamename;

import java.util.ArrayList;

public class PlayerClass {
    int coinsPickedUp;
    String filepath = "img/brocolli3.png";
    int velocity = 0;
    int gravity = 1;
    int groundLevel = 100;
    int yPlayer = 100;
    int jumpCount = 0;
    int spriteHeight = 120;
    int spriteWidth = 80;
    ArrayList<BulletClass> bullets = new ArrayList<>();

    // --- Magazijn ---
    int ammo = 5;
    int maxAmmo = 5;
    boolean isReloading = false;
    long reloadStartTime = 0;

    // --- Movement / world ---
    public double speed = 1;
    public double distanceTravelled = 0;
    public static float worldX = 0;

    // --- NIEUW: nodig voor dynamische parallax ---
    public static float totalPlayTime = 0f;

    // --- Statistieken ---
    public int totalCoins = 0;
    public double highScore = 0;

    public int enemiesDefeated = 0;
    public int shotsFired = 0;
    public float survivalTime = 0;
    public boolean isBlocking = false;
}