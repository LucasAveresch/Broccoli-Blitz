package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class Methodes_Lucas {

    public static class LucasParallaxMethods {

        private static final String TEX_WALL = "bg_wall";
        private static final String TEX_FLOOR = "bg_floor";

        private static final String TEX_WALL_PATH = "img/bg_mid.png";
        private static final String TEX_FLOOR_PATH = "img/bg_close.png";

        private static final float FLOOR_HEIGHT_RATIO = 0.18f;
        private static final float SEAM_OVERLAP = 6f;

        private static boolean initialized = false;
        private static float baseY = 0f;

        public static void initParallax(float parallaxBaseY) {
            if (initialized) return;

            baseY = parallaxBaseY;

            GameApp.addTexture(TEX_WALL, TEX_WALL_PATH);
            GameApp.addTexture(TEX_FLOOR, TEX_FLOOR_PATH);

            initialized = true;
        }

        public static void disposeParallax() {
            if (!initialized) return;

            GameApp.disposeTexture(TEX_WALL);
            GameApp.disposeTexture(TEX_FLOOR);

            initialized = false;
        }

        // ✅ LET OP: nu 3 parameters: cameraX, worldWidth, worldHeight
        public static void drawParallaxBackground(float cameraX, float worldWidth, float worldHeight) {
            if (!initialized) initParallax(baseY);

            float playTime = PlayerClass.totalPlayTime;
            float speedMultiplier = 1f + (playTime / 60f);

            float floorH = worldHeight * FLOOR_HEIGHT_RATIO;
            float floorY = baseY;

            float wallY = floorY + floorH - SEAM_OVERLAP;
            float wallH = (baseY + worldHeight) - wallY;

            drawLayerScaled(TEX_WALL, 0.3f * speedMultiplier, cameraX, worldWidth, wallY, wallH);
            drawLayerScaled(TEX_FLOOR, 0.3f * speedMultiplier, cameraX, worldWidth, floorY, floorH);
        }

        private static void drawLayerScaled(String textureName,
                                            float speed,
                                            float cameraX,
                                            float worldWidth,
                                            float y,
                                            float targetHeight) {

            float texW = GameApp.getTextureWidth(textureName);
            float texH = GameApp.getTextureHeight(textureName);
            if (texW <= 0 || texH <= 0 || targetHeight <= 0) return;

            float scale = targetHeight / texH;
            float tileW = texW * scale;

            float startX = -(cameraX * speed) % tileW;
            if (startX > 0) startX -= tileW;

            for (float x = startX; x < worldWidth + tileW; x += tileW) {
                GameApp.drawTexture(textureName, x, y, tileW, targetHeight);
            }
        }
    }
}
    
