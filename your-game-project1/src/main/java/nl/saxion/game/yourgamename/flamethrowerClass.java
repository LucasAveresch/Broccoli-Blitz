package nl.saxion.game.yourgamename;

public class flamethrowerClass {

    public float x, y;
    public int frame = 1;
    public float frameTimer = 0;

    public static final float FRAME_DURATION = 0.12f; // animatiesnelheid

    public flamethrowerClass(float x, float y) {
        this.x = x;
        this.y = y;
    }
}