package nl.saxion.game.yourgamename;

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

    // --- Magazijn ---
    int ammo = 5;             // huidige kogels
    int maxAmmo = 5;          // maximale kogels
    boolean isReloading = false;
    long reloadStartTime = 0; // tijdstip herladen

    // --- Nieuwe variabele ---
    public double speed = 1; // snelheid waarmee munten naar links bewegen
    public double distanceTravelled = 0; // totaal aantal meters gelopen
    public static float worldX = 0; // gedeelde parallax offset

    public int totalCoins = 0;       // alle munten over meerdere rondes
    public double highScore = 0;     // hoogste afstand ooit gehaald


}

