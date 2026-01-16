package nl.saxion.game.yourgamename;

import java.util.ArrayList;
import java.util.Random;

public class SegmentGenerator {

    private final Random rng = new Random();
    private final int baseGroundLevel;

    public SegmentGenerator(int baseGroundLevel) {
        this.baseGroundLevel = baseGroundLevel;
    }

    public GeneratedSegment generate(float startX) {

        GeneratedSegment seg = new GeneratedSegment();

        int pattern = rng.nextInt(7);


        switch (pattern) {

            case 0:
                seg.platforms.add(new PlatformClass(startX + 150, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 480, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 650, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 1100, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 1430, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 1600, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 1:
                // ⭐ PATTERN: verhoogde vloer + spike ertussen
                seg.platforms.add(new PlatformClass(startX + 150, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 480, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 650, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 2:
                // ⭐ PATTERN: vloer + triple spike
                seg.platforms.add(new PlatformClass(startX + 150, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 325, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                seg.platforms.add(new PlatformClass(startX + 650, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 3:
                // ⭐ PATTERN: raise + spike bovenop
                seg.platforms.add(new PlatformClass(startX + 200, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 500, baseGroundLevel + 100, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 800, baseGroundLevel + 200, 270, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 1100, baseGroundLevel + 100, 270, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 1400, baseGroundLevel, 270, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 1800, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                seg.platforms.add(new PlatformClass(startX + 2200, baseGroundLevel, 270, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 4:
                seg.platforms.add(new PlatformClass(startX + 200, baseGroundLevel, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 500, baseGroundLevel + 100, 300, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 650, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                seg.obstacles.add(new ObstacleClass(startX + 800, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                seg.platforms.add(new PlatformClass(startX + 1100, baseGroundLevel + 100, 270, 70, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 1400, baseGroundLevel, 270, 70, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 5:
                seg.obstacles.add(new ObstacleClass(startX + 480, baseGroundLevel, 150, 70, 150, 150, "spike"));
                break;
            case 6:
                seg.obstacles.add(new ObstacleClass(startX + 480, baseGroundLevel, 150, 70, 450, 150, "spikeTriple"));
                break;

        }

        return seg;
    }

    public static class GeneratedSegment {
        public ArrayList<PlatformClass> platforms = new ArrayList<>();
        public ArrayList<ObstacleClass> obstacles = new ArrayList<>();
    }
}