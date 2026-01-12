package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class BombClass {
    public int x, y;
    public int frameIndex = 1; // nooit 0!
    public float timer = 0;
    public static final int EXPLOSION_RADIUS = 200;

    // Bewegingsvariabelen
    public float velocityX = 12;
    public float velocityY = 20;
    public float gravity = -1.0f;

    public boolean exploded = false; // nieuwe flag

    public static final int TOTAL_FRAMES = 10;
    public static final float FRAME_DURATION = 0.2f;

    public BombClass(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void update() {
        if (!exploded) {
            // Parabolische beweging
            x += velocityX;
            y += velocityY;
            velocityY += gravity;
        }

        // Animatie frames alleen als hij geëxplodeerd is
        if (exploded) {
            timer += GameApp.getDeltaTime();
            if (timer >= FRAME_DURATION) {
                timer = 0;
                if (frameIndex < TOTAL_FRAMES) {
                    frameIndex++;
                }
            }
        }
    }
}