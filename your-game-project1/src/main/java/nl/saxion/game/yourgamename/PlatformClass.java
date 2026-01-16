package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class PlatformClass {

    public float x, y;
    public float width, height;          // HITBOX
    public float textureWidth, textureHeight; // SPRITE
    public String textureKey;

    // bewegen of niet
    public boolean moves;

    private float moveSpeed;
    private float moveRange;
    private float startY;
    private boolean moveUp = true;

    public PlatformClass(float x, float y,
                         float width, float height,
                         float textureWidth, float textureHeight,
                         String textureKey,
                         boolean moves,
                         float moveSpeed,
                         float moveRange) {

        this.x = x;
        this.y = y;

        // SPRITE
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        // ⭐ HITBOX = SPRITE (altijd gelijk)
        this.width = textureWidth;
        this.height = textureHeight;

        this.textureKey = textureKey;

        this.moves = moves;
        this.moveSpeed = moveSpeed;
        this.moveRange = moveRange;
        this.startY = y;
    }

    public void update(float delta) {
        // beweegt mee met wereld
        x -= 300 * delta;

        // alleen bewegen als moves == true
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

    // oude helper, mag blijven voor andere code
    public boolean playerIsOnTop(PlayerClass player) {
        float px = 100;
        float pw = player.spriteWidth;

        boolean horizontal = px + pw > x && px < x + width;

        float feet = player.yPlayer;
        float platformTop = y + height;

        boolean vertical = feet >= platformTop - 10 && feet <= platformTop + 20;

        return horizontal && vertical;
    }
}