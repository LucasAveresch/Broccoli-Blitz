package nl.saxion.game.yourgamename;

import java.util.ArrayList;
import java.util.Random;

public class SegmentGenerator {

    private final Random rng = new Random();
    private final int baseGroundLevel;

    public SegmentGenerator(int baseGroundLevel) {
        this.baseGroundLevel = baseGroundLevel;
    }

    public GeneratedSegment generate(float startX, EnemyClass enemyClass) {

        GeneratedSegment seg = new GeneratedSegment();
        int pattern = rng.nextInt(7);

        switch (pattern) {

            case 0:
                PlatformClass p0 = new PlatformClass(startX + 200, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0);
                seg.platforms.add(p0);

                seg.obstacles.add(new ObstacleClass(startX + 550, baseGroundLevel, 150, 70, 150, 150, "spike"));

                PlatformClass p1 = new PlatformClass(startX + 800, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0);
                seg.platforms.add(p1);

                // Spawn enemy on p1
                addStationaryEnemy(seg, enemyClass, p1.x + p1.width / 2f - 75, p1.y + p1.height);
                break;

            case 1:
                seg.platforms.add(new PlatformClass(startX + 150, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 500, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 800, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 1150, baseGroundLevel, 150, 70, 150, 150, "spike"));
                break;

            case 2:
                seg.platforms.add(new PlatformClass(startX + 100, baseGroundLevel, 350, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 500, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                seg.obstacles.add(new ObstacleClass(startX + 950, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                seg.platforms.add(new PlatformClass(startX + 1400, baseGroundLevel, 350, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 3:
                seg.platforms.add(new PlatformClass(startX + 150, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 450, baseGroundLevel + 100, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 750, baseGroundLevel + 200, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 1100, baseGroundLevel + 200, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 1400, baseGroundLevel + 100, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 1700, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 4:
                seg.obstacles.add(new ObstacleClass(startX + 300, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.obstacles.add(new ObstacleClass(startX + 550, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 850, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 5:
                seg.platforms.add(new PlatformClass(startX + 200, baseGroundLevel, 400, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 700, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                seg.platforms.add(new PlatformClass(startX + 1200, baseGroundLevel, 400, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 6:
                seg.obstacles.add(new ObstacleClass(startX + 350, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 650, baseGroundLevel, 200, 70, 300, 120, "floorRaise", false, 0, 0));
                break;
        }

        return seg;
    }

    public static class GeneratedSegment {
        public ArrayList<PlatformClass> platforms = new ArrayList<>();
        public ArrayList<ObstacleClass> obstacles = new ArrayList<>();
    }

    private void addStationaryEnemy(GeneratedSegment seg, EnemyClass enemyClass, float x, float y) {
        EnemyClass e = new EnemyClass(
                "img/chef.png",
                "chef",
                "img/ketchup.png",
                "enemy2",
                x,
                y,
                0
        );

        e.type = 4;
        e.hp = 3;

        enemyClass.allEnemies.add(e);
    }
}