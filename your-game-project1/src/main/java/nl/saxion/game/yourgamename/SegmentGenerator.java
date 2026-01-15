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

        int pattern = rng.nextInt(5);

        switch (pattern) {

            case 0:
                // ⭐ PATTERN: verhoogde vloer van 2 blokken
                seg.platforms.add(new PlatformClass(startX + 200, baseGroundLevel, 300, 120, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 500, baseGroundLevel, 300, 120, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 1:
                // ⭐ PATTERN: verhoogde vloer + spike ertussen
                seg.platforms.add(new PlatformClass(startX + 150, baseGroundLevel, 300, 120, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 480, baseGroundLevel, 150, 70, 150, 150, "spike"));
                seg.platforms.add(new PlatformClass(startX + 650, baseGroundLevel, 300, 120, 300, 120, "floorRaise", false, 0, 0));
                break;

            case 2:
                // ⭐ PATTERN: vloer + triple spike
                seg.obstacles.add(new ObstacleClass(startX + 300, baseGroundLevel, 330, 70, 450, 150, "spikeTriple"));
                break;

            case 3:
                // ⭐ PATTERN: raise + spike bovenop
                seg.platforms.add(new PlatformClass(startX + 300, baseGroundLevel, 300, 120, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 350, baseGroundLevel + 120, 110, 70, 150, 150, "spike"));
                break;

            case 4:
                // ⭐ PATTERN: lange verhoogde vloer met spike aan het eind
                seg.platforms.add(new PlatformClass(startX + 150, baseGroundLevel, 300, 120, 300, 120, "floorRaise", false, 0, 0));
                seg.platforms.add(new PlatformClass(startX + 450, baseGroundLevel, 300, 120, 300, 120, "floorRaise", false, 0, 0));
                seg.obstacles.add(new ObstacleClass(startX + 780, baseGroundLevel, 150, 70, 150, 150, "spike"));
                break;
        }

        return seg;
    }

    public static class GeneratedSegment {
        public ArrayList<PlatformClass> platforms = new ArrayList<>();
        public ArrayList<ObstacleClass> obstacles = new ArrayList<>();
    }
}