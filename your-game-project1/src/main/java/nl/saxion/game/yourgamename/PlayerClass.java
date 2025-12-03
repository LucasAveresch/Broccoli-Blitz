package nl.saxion.game.yourgamename;

public class PlayerClass {
    int coinsPickedUp;
    String filepath = "img/brocolli.png";
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
    public int speed = 5; // snelheid waarmee munten naar links bewegen

}

