package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

    public class Methodes_Lucas {

        /**
         * Helper-methodes voor een simpele parallax achtergrond
         * met één laag die eindeloos herhaalt.
         *
         * Gebruik in je Screen:
         *  - LucasParallaxMethods.initParallax(0f); in show()
         *  - LucasParallaxMethods.drawParallaxBackground(cameraX, getWorldWidth()); in render()
         *  - LucasParallaxMethods.disposeParallax(); in hide()
         */
        public static class LucasParallaxMethods {

            // Namen voor de textures in de GameApp texture registry
            private static final String TEX_MID = "lucas_bg_mid";

            // Bestandsnamen in src/main/resources/img
            private static final String TEX_MID_PATH = "img/bg_mid.png";

            // Parallax snelheid (kleiner = lijkt verder weg)
            private static final float SPEED_MID = 0.5f;

            // Y-positie waarop de achtergrond getekend wordt
            private static float baseY = 0f;

            // Om te voorkomen dat we textures dubbel laden
            private static boolean initialized = false;

            /**
             * Roept je één keer aan bij het opstarten van je level.
             * Bijvoorbeeld in show() van je ScalableGameScreen.
             */
            public static void initParallax(float parallaxBaseY) {
                if (initialized) {
                    return;
                }

                baseY = parallaxBaseY;

                // Textures registreren in de globale texture registry
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

                GameApp.disposeTexture(TEX_MID);

                initialized = false;
            }

            /**
             * Tekent de parallax achtergrond met één laag die eindeloos herhaalt.
             * Roept je elke frame aan nadat GameApp.startSpriteRendering() is aangeroepen.
             */
            public static void drawParallaxBackground(float cameraX, float worldWidth) {
                if (!initialized) {
                    initParallax(baseY);
                }

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

        /**
         * LucasLevelSegments:
         * segment-gebaseerde platforms (keuken-obstakels) die naar links schuiven.
         * De speler staat altijd op x = 100; de wereld beweegt.
         */
        public static class LucasLevelSegments {

            // Één platform (bijv. een aanrecht / pan / keukenkast)
            private static class Platform {
                float x;
                float y;
                float width;
                float height;
                String textureKey;

                Platform(float x, float y, float width, float height, String textureKey) {
                    this.x = x;
                    this.y = y;
                    this.width = width;
                    this.height = height;
                    this.textureKey = textureKey;
                }
            }

            private static final int PLAYER_X = 100;           // vaste X van broccoli-speler
            private static final float PLATFORM_HEIGHT = 40f;
            private static final float SMALL_PLATFORM_WIDTH = 80f;
            private static final float BIG_PLATFORM_WIDTH = 140f;

            // Afstand tussen segmenten (in meters)
            private static final float BASE_SEGMENT_DISTANCE = 6f;
            private static final float MIN_SEGMENT_DISTANCE = 3f;
            private static final float MAX_SEGMENT_DISTANCE = 10f;

            // Waar we platforms weggooien (links buiten beeld)
            private static final float DESPAWN_X = -200f;

            private static final ArrayList<Platform> platforms = new ArrayList<>();
            private static final Random random = new Random();

            private static float metersSinceLastSegment = 0f;

            private static boolean initialized = false;

            // Keuken-platform textures laden (keukenapparatuur)
            private static void init() {
                if (initialized) return;

                // Zorg dat je deze bestanden in src/main/resources/img zet:
                //  - platform_small.png (bijv. pan / krat)
                //  - platform_big.png   (bijv. groot aanrecht)
                GameApp.addTexture("platform_small", "img/platform_small.png");
                GameApp.addTexture("platform_big", "img/platform_big.png");

                initialized = true;
            }

            public static void dispose() {
                if (!initialized) return;

                GameApp.disposeTexture("platform_small");
                GameApp.disposeTexture("platform_big");

                platforms.clear();
                metersSinceLastSegment = 0f;
                initialized = false;
            }

            /**
             * Roept je elke frame vanuit YourGameScreen.render().
             * - Spawnt nieuwe segmenten op basis van distanceTravelled (difficulty)
             * - Beweegt platforms naar links
             * - Tekent platforms
             */
            public static void updateAndDrawSegments(PlayerClass player, float delta) {
                init(); // textures één keer laden

                // 1. Difficulty obv afstand
                float difficulty = 1f + (float) (player.distanceTravelled / 30.0); // hoe verder, hoe moeilijker
                float targetDistance = BASE_SEGMENT_DISTANCE / difficulty;

                // Clamp min/max afstand
                if (targetDistance < MIN_SEGMENT_DISTANCE) targetDistance = MIN_SEGMENT_DISTANCE;
                if (targetDistance > MAX_SEGMENT_DISTANCE) targetDistance = MAX_SEGMENT_DISTANCE;

                // 2. Meters sinds laatste segment optellen
                metersSinceLastSegment += player.speed * delta;

                // 3. Nieuw segment maken als we ver genoeg zijn
                if (metersSinceLastSegment >= targetDistance) {
                    metersSinceLastSegment = 0f;
                    spawnSegment(player);
                }

                // 4. Platforms updaten + tekenen
                float screenSpeed = (float) (player.speed * 5.0); // zelfde als munten

                Iterator<Platform> it = platforms.iterator();
                while (it.hasNext()) {
                    Platform p = it.next();

                    // naar links bewegen
                    p.x -= screenSpeed;

                    // tekenen
                    GameApp.drawTexture(p.textureKey, p.x, p.y, p.width, p.height);

                    // despawn als platform uit beeld is
                    if (p.x + p.width < DESPAWN_X) {
                        it.remove();
                    }
                }
            }

            // === SEGMENT-GENERATIE ===

            private static void spawnSegment(PlayerClass player) {
                float startX = GameApp.getWorldWidth() + 50f;

                int templateIndex = random.nextInt(3); // drie basis-templates

                switch (templateIndex) {
                    case 0:
                        spawnLowDouble(startX, player);
                        break;
                    case 1:
                        spawnHighPlatform(startX, player);
                        break;
                    default:
                        spawnStairs(startX, player);
                        break;
                }
            }

            // Template 1: twee lage "keukenobjecten" op de grond (er OVERheen springen)
            private static void spawnLowDouble(float startX, PlayerClass player) {
                float groundY = player.groundLevel;

                float w1 = SMALL_PLATFORM_WIDTH;
                float h1 = PLATFORM_HEIGHT;
                platforms.add(new Platform(startX, groundY, w1, h1, "platform_small"));

                float gap = 80f; // afstand ertussen
                float w2 = SMALL_PLATFORM_WIDTH;
                platforms.add(new Platform(startX + w1 + gap, groundY, w2, h1, "platform_small"));
            }

            // Template 2: lage keukentafel + hoger blok om op te landen
            private static void spawnHighPlatform(float startX, PlayerClass player) {
                float groundY = player.groundLevel;

                // lage keukentafel
                float w1 = BIG_PLATFORM_WIDTH;
                platforms.add(new Platform(startX, groundY, w1, PLATFORM_HEIGHT, "platform_big"));

                // hoog keukenblok
                float gap = 60f;
                float highY = groundY + 80f;
                float w2 = SMALL_PLATFORM_WIDTH;
                platforms.add(new Platform(startX + w1 + gap, highY, w2, PLATFORM_HEIGHT, "platform_small"));
            }

            // Template 3: trappetje van keukenobjecten
            private static void spawnStairs(float startX, PlayerClass player) {
                float groundY = player.groundLevel;
                float stepHeight = 35f;
                float stepWidth = SMALL_PLATFORM_WIDTH;
                float gap = 40f;

                platforms.add(new Platform(startX, groundY, stepWidth, PLATFORM_HEIGHT, "platform_small"));
                platforms.add(new Platform(startX + stepWidth + gap, groundY + stepHeight, stepWidth, PLATFORM_HEIGHT, "platform_small"));
                platforms.add(new Platform(startX + 2 * (stepWidth + gap), groundY + 2 * stepHeight, stepWidth, PLATFORM_HEIGHT, "platform_small"));
            }

            /**
             * Geeft terug wat de "grondhoogte" is onder de speler:
             * - standaard player.groundLevel
             * - of de bovenkant van het hoogste platform onder de speler
             */
            public static int getGroundYAtPlayer(PlayerClass player) {
                int ground = player.groundLevel;

                int playerLeft = PLAYER_X;
                int playerRight = PLAYER_X + player.spriteWidth;

                for (Platform p : platforms) {
                    float platformLeft = p.x;
                    float platformRight = p.x + p.width;

                    // horizontale overlap met speler?
                    if (playerRight >= platformLeft && playerLeft <= platformRight) {
                        int platformTop = (int) (p.y + p.height);

                        // neem alleen platforms boven de normale grond
                        if (platformTop > ground && platformTop <= ground + 200) {
                            ground = platformTop;
                        }
                    }
                }

                return ground;
            }
        }
    }
    
