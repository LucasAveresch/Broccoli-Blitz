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
    ArrayList<BulletClass> bullets= new ArrayList<>();
    // --- Magazijn ---
    int ammo = 5;             // huidige kogels
    int maxAmmo = 5;          // maximale kogels
    boolean isReloading = false;
    long reloadStartTime = 0; // tijdstip herladen

    // --- Nieuwe variabele ---
    public double speed = 1;
    public double distanceTravelled = 0;
    public static float worldX = 0;

    public int totalCoins = 0;
    public double highScore = 0;

    // --- Statistieken voor DeathScreen ---
    public int enemiesDefeated = 0;    // aantal verslagen enemies
    public int shotsFired = 0;         // aantal keer geschoten
    public float survivalTime = 0;     // tijd in seconden
    public boolean isBlocking = false;
}