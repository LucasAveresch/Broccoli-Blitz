package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class PlatformClass {

    public float x, y;

    // HITBOX (collision)
    public float width, height;

    // SPRITE (visueel)
    public float textureWidth, textureHeight;

    public String textureKey;

    // bewegen of niet
    public boolean moves;

    private float moveSpeed;
    private float moveRange;
    private float startY;
    private boolean moveUp = true;

    public PlatformClass(float x, float y,
                         float width, float height,              // ⭐ HITBOX (bijv. 300 × 70)
                         float textureWidth, float textureHeight, // ⭐ SPRITE (bijv. 300 × 120)
                         String textureKey,
                         boolean moves,
                         float moveSpeed,
                         float moveRange) {

        this.x = x;
        this.y = y;

        // SPRITE (120 hoog)
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        // HITBOX (70 hoog)
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
        // sprite tekenen (120 hoog)
        GameApp.drawTexture(textureKey, x, y, textureWidth, textureHeight);
    }

    // oude helper, mag blijven
    public boolean playerIsOnTop(PlayerClass player) {
        float px = 120;
        float pw = player.spriteWidth;

        boolean horizontal = px + pw > x && px < x + width;

        float feet = player.yPlayer;
        float platformTop = y + height; // ⭐ collision-top = 70, niet 120

        boolean vertical = feet >= platformTop - 10 && feet <= platformTop + 20;

        return horizontal && vertical;
    }
}