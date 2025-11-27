package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class Methodes_Lucas {

    /**
     * Helper-methodes voor een simpele parallax achtergrond
     * met twee lagen die eindeloos herhalen.
     *
     * Gebruik in je Screen:
     *  - LucasParallaxMethods.initParallax(0f); in show()
     *  - LucasParallaxMethods.drawParallaxBackground(cameraX, getWorldWidth()); in render()
     *  - LucasParallaxMethods.disposeParallax(); in hide()
     */
    public class LucasParallaxMethods {

        // Namen voor de textures in de GameApp texture registry
        private static final String TEX_FAR = "lucas_bg_far";
        private static final String TEX_MID = "lucas_bg_mid";

        // Bestandsnamen in src/main/resources/textures
        private static final String TEX_FAR_PATH = "textures/bg_far.png";
        private static final String TEX_MID_PATH = "textures/bg_mid.png";

        // Parallax snelheden (kleiner = lijkt verder weg)
        private static final float SPEED_FAR = 0.2f;
        private static final float SPEED_MID = 0.5f;

        // Y-positie waarop de achtergrond getekend wordt
        private static float baseY = 0f;

        // Om te voorkomen dat we textures dubbel laden
        private static boolean initialized = false;

        /**
         * Roept je één keer aan bij het opstarten van je level.
         * Bijvoorbeeld in show() van je ScalableGameScreen.
         *
         * @param parallaxBaseY Y-positie waarop de achtergrond moet beginnen
         */
        public static void initParallax(float parallaxBaseY) {
            if (initialized) {
                return;
            }

            baseY = parallaxBaseY;

            // Textures registreren in de globale texture registry
            // Zie Saxion docs: GameApp.addTexture("naam", "textures/bestand.png")
            GameApp.addTexture(TEX_FAR, TEX_FAR_PATH);
            GameApp.addTexture(TEX_MID, TEX_MID_PATH);

            initialized = true;
        }

        /**
         * Ruimt de textures weer op.
         * Roept je aan in hide() van je Screen.
         */
        public static void disposeParallax() {
            if (!initialized) {
                return;
            }

            GameApp.disposeTexture(TEX_FAR);
            GameApp.disposeTexture(TEX_MID);

            initialized = false;
        }

        /**
         * Tekent de parallax achtergrond met twee lagen die eindeloos herhalen.
         * Roept je elke frame aan nadat GameApp.startSpriteRendering() is aangeroepen.
         *
         * @param cameraX   x-positie van camera of speler in wereld-coordinaten
         * @param worldWidth breedte van je wereld (bijv. getWorldWidth() in een ScalableGameScreen)
         */
        public static void drawParallaxBackground(float cameraX, float worldWidth) {
            if (!initialized) {
                // veiligheidsnet, maar normaal roep je zelf initParallax() in show() aan
                initParallax(baseY);
            }

            // Achtergrondlaag ver weg
            drawLayer(TEX_FAR, SPEED_FAR, cameraX, worldWidth);

            // Middenlaag dichterbij
            drawLayer(TEX_MID, SPEED_MID, cameraX, worldWidth);
        }

        // Interne hulpfunctie voor één laag
        private static void drawLayer(String textureName,
                                      float speed,
                                      float cameraX,
                                      float worldWidth) {

            // Breedte van de texture in pixels
            float texWidth = GameApp.getTextureWidth(textureName);

            // Parallax: beweegt langzamer dan de camera
            float worldX = -cameraX * speed;

            // Beginpositie binnen één tile-breedte
            float startX = worldX % texWidth;

            // Zorg dat startX niet positief is, zodat we links van 0 beginnen
            if (startX > 0) {
                startX -= texWidth;
            }

            // Tiles tekenen van links naar rechts tot de hele wereldbreedte gevuld is
            for (float x = startX; x < worldWidth; x += texWidth) {
                GameApp.drawTexture(textureName, x, baseY);
            }
        }
    }


}
