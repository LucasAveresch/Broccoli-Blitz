package nl.saxion.game.yourgamename;

public class CoinClass {
    int x;
    int y;
    int width = 100;
    int height = 100;
    boolean isCollected = false;

    public CoinClass(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Laat de coin meebewegen met de world speed
    public void update(int gameSpeed) {
        x -= gameSpeed;
    }
}