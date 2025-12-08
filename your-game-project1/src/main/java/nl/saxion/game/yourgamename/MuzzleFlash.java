package nl.saxion.game.yourgamename;

public class MuzzleFlash {
    public int x, y;
    public int frameIndex = 0;
    public static final int TOTAL_FRAMES = 5;   // aantal frames in je sheet
    public static final float FRAME_DURATION = 0.05f; // tijd per frame (50 ms)
    float timer = 0f;
    public boolean active = true;

    public MuzzleFlash(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= FRAME_DURATION) {
            timer = 0;
            frameIndex++;
            if (frameIndex >= TOTAL_FRAMES) {
                active = false; // animatie klaar
            }
        }
    }
}