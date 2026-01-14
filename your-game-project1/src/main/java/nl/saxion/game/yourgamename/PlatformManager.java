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

        // ⭐ Random: 40% kans dat een platform beweegt
        boolean moves = rng.nextFloat() < 0.4f;

        float randomMoveSpeed = 30 + rng.nextInt(50);
        float randomMoveRange = 40 + rng.nextInt(80);

        PlatformClass p = new PlatformClass(
                worldX,
                randomHeight,
                randomWidth,
                40,
                randomWidth,
                60,
                "platform",
                moves,
                randomMoveSpeed,
                randomMoveRange
        );

        platforms.add(p);

        // ⭐ NIEUW: Coin bovenop het platform spawnen
        // Coin staat in het midden van het platform
        int coinX = (int) (p.x + p.width / 2f - 50);
        int coinY = (int) (p.y + p.height + 10);

        Methodes_Rutger.coins.add(new CoinClass(coinX, coinY));
    }

    public void update(float delta) {
        for (PlatformClass p : platforms) {
            p.update(delta);
        }

        // platforms verwijderen die uit beeld zijn
        platforms.removeIf(p -> p.x + p.width < 0);
    }

    public void draw() {
        for (PlatformClass p : platforms) {
            p.draw();
        }
    }
}