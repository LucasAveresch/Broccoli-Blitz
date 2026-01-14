package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import java.util.Random;

public class PlatformClass {

    public float x, y;
    public float width, height;
    public float textureWidth, textureHeight;
    public String textureKey;
    public boolean isActive = true;

    // beweging
    private float moveSpeed;
    private float moveRange;
    private float startY;
    private boolean moveUp;

    public PlatformClass(float x, float y,
                         float width, float height,
                         float textureWidth, float textureHeight,
                         String textureKey,
                         float moveSpeed,
                         float moveRange) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        this.textureKey = textureKey;

        this.moveSpeed = moveSpeed;
        this.moveRange = moveRange;
        this.startY = y;
        this.moveUp = true;
    }

    public void update(float delta) {
        // horizontale beweging met wereld
        x -= 300 * delta;

        // verticale beweging
        if (moveUp) {
            y += moveSpeed * delta;
            if (y > startY + moveRange) moveUp = false;
        } else {
            y -= moveSpeed * delta;
            if (y < startY - moveRange) moveUp = true;
        }
    }

    public void draw() {
        GameApp.drawTexture(textureKey, x, y, textureWidth, textureHeight);
    }

    public boolean playerIsOnTop(PlayerClass player) {
        float px = 100;
        float pw = player.spriteWidth;

        boolean horizontal = px + pw > x && px < x + width;

        float feet = player.yPlayer;
        float platformTop = y + height;

        boolean vertical = feet >= platformTop - 10 && feet <= platformTop + 20;

        return isActive && horizontal && vertical;
    }
}