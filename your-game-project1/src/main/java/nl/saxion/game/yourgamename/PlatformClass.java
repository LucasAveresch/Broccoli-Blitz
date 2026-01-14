package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class PlatformClass {

    public float x, y;
    public float width, height;
    public float textureWidth, textureHeight;
    public String textureKey;
    public boolean isActive = true;

    public PlatformClass(float x, float y,
                         float width, float height,
                         float textureWidth, float textureHeight,
                         String textureKey) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        this.textureKey = textureKey;
    }

    public void update(float delta) {
        x -= 300 * delta;
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