package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class PlatformClass {

    public float x, y;

    // HITBOX
    public float width, height;

    // SPRITE
    public float textureWidth, textureHeight;

    public String textureKey;

    // bewegen of niet
    public boolean moves;

    private float moveSpeed;
    private float moveRange;
    private float startY;
    private boolean moveUp = true;

    public PlatformClass(float x, float y,
                         float width, float height,              // HITBOX (bijv. 300 × 70)
                         float textureWidth, float textureHeight, // SPRITE (bijv. 300 × 120)
                         String textureKey,
                         boolean moves,
                         float moveSpeed,
                         float moveRange) {

        this.x = x;
        this.y = y;

        // SPRITE (visueel)
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        // HITBOX (collision)
        // ⭐ width komt 1-op-1 uit de generator
        // ⭐ textureWidth komt 1-op-1 uit de generator
        // → beide zijn gelijk
        this.width = width;
        this.height = height;

        this.textureKey = textureKey;

        this.moves = moves;
        this.moveSpeed = moveSpeed;
        this.moveRange = moveRange;
        this.startY = y;
    }

    public void update(float delta) {
        // beweegt mee met wereld
        x -= 300 * YourGameScreen.gameSpeed * delta;
        if (moves) {
            if (moveUp) {
                y += moveSpeed * delta;
                if (y > startY + moveRange) moveUp = false;
            } else {
                y -= moveSpeed * delta;
                if (y < startY - moveRange) moveUp = true;
            }
        }
    }

    public void draw() {
        GameApp.drawTexture(textureKey, x, y, textureWidth, textureHeight);
    }

    public boolean playerIsOnTop(PlayerClass player) {
        float px = 120;
        float pw = player.spriteWidth;

        boolean horizontal = px + pw > x && px < x + width;

        float feet = player.yPlayer;
        float platformTop = y + height;

        boolean vertical = feet >= platformTop - 10 && feet <= platformTop + 20;

        return horizontal && vertical;
    }
}