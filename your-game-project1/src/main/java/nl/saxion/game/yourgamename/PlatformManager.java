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
                randomMoveSpeed,
                randomMoveRange
        );

        platforms.add(p);
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