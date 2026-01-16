package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class ObstacleClass {
    public float x;
    public float y;

    // HITBOX
    public float width;
    public float height;

    // SPRITE
    public float textureWidth;
    public float textureHeight;

    public String textureKey;
    public boolean isActive = true;

    public ObstacleClass(float x, float y,
                         float width, float height,
                         float textureWidth, float textureHeight,
                         String textureKey) {

        this.x = x;
        this.y = y;

        // SPRITE
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        // ⭐ HITBOX = SPRITE
        this.width = textureWidth;
        this.height = textureHeight;

        this.textureKey = textureKey;
    }

    public void update(float delta) {
        x -= 300 * delta;
    }

    public void draw() {
        if (isActive) {
            GameApp.drawTexture(textureKey, x, y, textureWidth, textureHeight);
        }
    }

    public boolean collidesWith(PlayerClass player) {
        float px = 100;
        float py = player.yPlayer;
        float pw = player.spriteWidth;
        float ph = player.spriteHeight;

        return isActive &&
                px < x + width &&
                px + pw > x &&
                py < y + height &&
                py + ph > y;
    }
}