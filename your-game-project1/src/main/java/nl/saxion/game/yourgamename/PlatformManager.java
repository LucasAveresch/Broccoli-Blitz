package nl.saxion.game.yourgamename;

import java.util.ArrayList;
import java.util.Random;

public class PlatformManager {

    public ArrayList<PlatformClass> platforms = new ArrayList<>();
    private Random rng = new Random();
    private int baseGroundLevel;

    public PlatformManager(int baseGroundLevel) {
        this.baseGroundLevel = baseGroundLevel;
    }

    public void spawnPlatform(float worldX) {

        float randomHeight = baseGroundLevel + 100 + rng.nextInt(250);
        float randomWidth = 250 + rng.nextInt(300);

        boolean moves = rng.nextFloat() < 0.4f;

        float randomMoveSpeed = 30 + rng.nextInt(50);
        float randomMoveRange = 40 + rng.nextInt(80);

        float textureWidth = randomWidth;
        float textureHeight = 120; // jouw platform sprite hoogte

        PlatformClass p = new PlatformClass(
                worldX,
                randomHeight,
                textureWidth,      // maakt niet meer uit, hitbox = sprite
                textureHeight,
                textureWidth,
                textureHeight,
                "platform",
                moves,
                randomMoveSpeed,
                randomMoveRange
        );

        platforms.add(p);

        // Coin bovenop het platform
        int coinX = (int) (p.x + p.width / 2f - 50);
        int coinY = (int) (p.y + p.height + 10);

        Methodes_Rutger.coins.add(new CoinClass(coinX, coinY));
    }

    public void update(float delta) {
        for (PlatformClass p : platforms) {
            p.update(delta);
        }

        platforms.removeIf(p -> p.x + p.width < 0);
    }

    public void draw() {
        for (PlatformClass p : platforms) {
            p.draw();
        }
    }
}