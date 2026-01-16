package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.game.yourgamename.PlayerClass.*;

import javax.swing.border.EmptyBorder;
import java.util.Random;

public class Methodes_Maxje {


    public static boolean DEBUG_FLAME = false;
    // Breedte van de hitbox per frame (pixels)
    public static final float[] flameHitboxWidth = {
            0,    // frame 0 (unused)
            150,  // frame 1
            260,  // frame 2
            360,  // frame 3
            450,  // frame 4
            500,  // frame 5 (max flame length)
            450,  // frame 6
            360,  // frame 7
            260,  // frame 8
            150,  // frame 9
            50    // frame 10 (small tail)
    };

    public static void updateEnemies(float delta, EnemyClass enemyClass, SubEnemyClass subEnemyClass, flamethrowerClass flame,managerClass managerClass) {

        if (enemyClass.allEnemies.isEmpty()) return;
        EnemyClass enemy = enemyClass.allEnemies.get(0);
        if (enemy.type == 1) {
            enemy.enemyXPos -= enemy.enemyspeed * delta;

            if (enemy.enemyXPos < 0) {
                enemyClass.allEnemies.remove(0);
                enemy.type = 0;
                managerClass.enemyActive = false;
                return;
            }

            GameApp.drawTexture(enemy.textureKey, enemy.enemyXPos, enemy.enemyYPos- 50, 150,250);
        } else if (enemy.type == 2) {
            enemy.enemyXPos -= enemy.enemyspeed * delta;

            if (enemy.enemyXPos < 0) {
                enemyClass.allEnemies.remove(0);
                enemy.type = 0;
                managerClass.enemyActive = false;
                return;
            }

            GameApp.drawTexture(enemy.textureKey2, enemy.enemyXPos, enemy.enemyYPos, 175, 175);

            // 🔥 FLAME SLECHTS ÉÉN KEER STARTEN
            if (!enemy.hasShotFlame && enemy.enemyXPos < 900) {
                flame.x = enemy.enemyXPos + 40;
                flame.y = enemy.enemyYPos + 40;

                enemy.hasShotFlame = true;
            }
        } else if(enemy.type == 3){
            enemy.enemyXPos -= enemy.enemyspeed * delta;

            if (enemy.enemyXPos < 0) {
                enemyClass.allEnemies.remove(0);
                enemy.type = 0;
                managerClass.enemyActive = false;
                return;
            }
            if(enemy.hp == 3){
                GameApp.drawTexture("tank1",enemy.enemyXPos,enemy.enemyYPos - 50, 200, 200);
            }
            else if(enemy.hp == 2){
                GameApp.drawTexture("tank2",enemy.enemyXPos,enemy.enemyYPos - 50 , 200, 200);
            }
            else if(enemy.hp == 1){
                GameApp.drawTexture("tank3",enemy.enemyXPos,enemy.enemyYPos - 50,200,200);
            }
        }
    }
    public static void selectEnemyWillekeurig(float delta, EnemyClass enemyClass,managerClass managerClass) {
        Random r = new Random();
        boolean spawnNewEnemy = false;



        if (managerClass.spawnEnenmy) {
            spawnNewEnemy = true;
            enemyClass.currentTimer = 0f;
            managerClass.spawnEnenmy = false;
        }
        if (enemyClass.allEnemies.isEmpty() && spawnNewEnemy) {
            int randomnumber = r.nextInt(1, 4);
            EnemyClass enemyClass1 = new EnemyClass("img/chef.png", "chef", "img/ketchup.png", "enemy2", 1400, 150, 200);
            enemyClass1.type = randomnumber;
            enemyClass1.hp = 3;
            managerClass.enemyActive = true;
            enemyClass.allEnemies.add(enemyClass1);
            spawnNewEnemy = false;
        }
    }


    public static void addMes(float delta, ProjectileClass projectileClass, EnemyClass enemyClass) {
        if (enemyClass.allEnemies.isEmpty() || enemyClass.allEnemies.get(0).type != 1) return;

        projectileClass.spawnTimer += delta;
        if (projectileClass.spawnTimer > projectileClass.spawnInterval) {
            projectileClass.spawnTimer = 0f;

            String[] knifeFrames = {
                    "img/knife1.png",
                    "img/knife2.png",
                    "img/knife3.png",
                    "img/knife4.png",
                    "img/knife5.png",
                    "img/knife6.png",
                    "img/knife7.png",
                    "img/knife8.png"
            };

            ProjectileClass knife = new ProjectileClass(
                    knifeFrames,
                    enemyClass.allEnemies.get(0).enemyXPos,
                    enemyClass.enemyYPos + 30,
                    500
            );

            projectileClass.projectiles.add(knife);
            GameApp.addSound("mes","sounds/knife.mp3");
            GameApp.playSound("mes");
        }
    }


    public static void updateMes(float delta, ProjectileClass projectileClass) {

        for (int i = 0; i < projectileClass.projectiles.size(); i++) {
            ProjectileClass knife = projectileClass.projectiles.get(i);

            knife.xposition -= knife.speed * delta;

            knife.frameTimer += delta;
            if (knife.frameTimer >= knife.frameInterval) {
                knife.frameTimer = 0f;
                knife.currentFrame++;

                if (knife.currentFrame >= knife.frames.length) {
                    knife.currentFrame = 0;
                }
            }

            GameApp.drawTexture(
                    knife.getCurrentTexture(),
                    knife.xposition,
                    knife.yposition,80,80
            );

            if (knife.xposition < -100) {
                projectileClass.projectiles.remove(i);
                i--;
            }
        }
    }


    public static void checkCollsionMes(ProjectileClass projectileClass, PlayerClass playerClass) {

        if (projectileClass.projectiles.isEmpty()) return;

        ProjectileClass knife = projectileClass.projectiles.get(0);

        boolean collisionX =
                knife.xposition > 100 + playerClass.spriteWidth &&
                        knife.xposition < 120 + playerClass.spriteWidth;

        boolean collisionY =
                knife.yposition < playerClass.yPlayer + playerClass.spriteHeight &&
                        knife.yposition + 16 > playerClass.yPlayer;

        if (collisionX && collisionY) {

            if (playerClass.isBlocking) {

                GameApp.addSound("block", "Sounds/block.mp3");
                GameApp.playSound("block", 5.0f);

                projectileClass.projectiles.remove(0); // gewoon simpel verwijderen
                return;
            }

            GameApp.switchScreen("DeathScreen");
        }
    }

    public static void addSpatel(float delta, ProjectileClass projectileClass, EnemyClass enemyClass) {
        if (enemyClass.allEnemies.isEmpty() || enemyClass.allEnemies.get(0).type != 3) return;

        projectileClass.spawnTimer += delta;
        if (projectileClass.spawnTimer > projectileClass.spawnInterval) {
            projectileClass.spawnTimer = 0f;

            String[] knifeFrames = {
                    "img/spatel1.png",
                    "img/spatel2.png",
                    "img/spatel3.png",
                    "img/spatel4.png",
                    "img/spatel5.png",
                    "img/spatel6.png",
                    "img/spatel7.png",
                    "img/spatel8.png"
            };

            ProjectileClass knife = new ProjectileClass(
                    knifeFrames,
                    enemyClass.allEnemies.get(0).enemyXPos,
                    enemyClass.enemyYPos + 30,
                    500
            );

            projectileClass.projectiles.add(knife);
            GameApp.addSound("mes","sounds/knife.mp3");
            GameApp.playSound("mes");
        }
    }


    public static void updateSpatel(float delta, ProjectileClass projectileClass) {

        for (int i = 0; i < projectileClass.projectiles.size(); i++) {
            ProjectileClass knife = projectileClass.projectiles.get(i);

            knife.xposition -= knife.speed * delta;

            knife.frameTimer += delta;
            if (knife.frameTimer >= knife.frameInterval) {
                knife.frameTimer = 0f;
                knife.currentFrame++;

                if (knife.currentFrame >= knife.frames.length) {
                    knife.currentFrame = 0;
                }
            }

            GameApp.drawTexture(
                    knife.getCurrentTexture(),
                    knife.xposition,
                    knife.yposition,80,80
            );

            if (knife.xposition < -100) {
                projectileClass.projectiles.remove(i);
                i--;
            }
        }
    }


    public static void checkCollsionSpatel(ProjectileClass projectileClass, PlayerClass playerClass) {

        if (projectileClass.projectiles.isEmpty()) return;

        ProjectileClass knife = projectileClass.projectiles.get(0);

        boolean collisionX =
                knife.xposition > 100 + playerClass.spriteWidth &&
                        knife.xposition < 120 + playerClass.spriteWidth;

        boolean collisionY =
                knife.yposition < playerClass.yPlayer + playerClass.spriteHeight &&
                        knife.yposition + 16 > playerClass.yPlayer;

        if (collisionX && collisionY) {

            if (playerClass.isBlocking) {

                GameApp.addSound("block", "Sounds/block.mp3");
                GameApp.playSound("block", 5.0f);

                projectileClass.projectiles.remove(0); // gewoon simpel verwijderen
                return;
            }

            GameApp.switchScreen("DeathScreen");
        }
    }

    public static void checkCollisionEnemy(PlayerClass playerClass, EnemyClass enemyClass, SubEnemyClass subEnemyClass, SchildClass schildClass, PowerupClass powerUp) {
        if (!enemyClass.allEnemies.isEmpty()) {
            EnemyClass enemy = enemyClass.allEnemies.get(0);
            if (enemy.type == 1) {
                boolean collisionX =
                        enemy.enemyXPos < 100 + playerClass.spriteWidth &&
                                enemy.enemyXPos + 50 > 100;

                boolean collisionY =
                        enemy.enemyYPos < playerClass.yPlayer + playerClass.spriteHeight &&
                                enemy.enemyYPos + 150 > playerClass.yPlayer;


                if (collisionX && collisionY && schildClass.HP == 0) {
                    GameApp.switchScreen("DeathScreen");
                } else if (collisionY && collisionX) {

                    schildClass.HP--;

                    if (schildClass.HP <= 0) {
                        schildClass.isactive = false;

                        powerUp.hasTimer = false;
                        powerUp.timeLeft = 0;
                        powerUp.timerStarted = false;
                    }

                    enemyClass.allEnemies.remove(0);
                    GameApp.addSound("SchildPickup", "Sounds/shieldPickup.mp3");
                    GameApp.playSound("SchildPickup");
                }
            } else if (enemy.type == 2) {
                boolean collisionX = false;
                boolean collisionY = false;
                if (enemy.enemyXPos < 100 + playerClass.spriteWidth && enemy.enemyXPos > 100) {
                    collisionX = true;
                }
                if (enemy.enemyYPos < playerClass.yPlayer + playerClass.spriteHeight && enemy.enemyYPos > playerClass.yPlayer) {
                    collisionY = true;
                }
                if (collisionX && collisionY && schildClass.HP == 0) {
                    GameApp.switchScreen("DeathScreen");
                } else if (collisionY && collisionX) {
                    schildClass.HP--;
                    enemyClass.allEnemies.remove(enemy);
                    GameApp.addSound("SchildPickup", "Sounds/shieldPickup.mp3");
                    GameApp.playSound("SchildPickup");
                }
            }
            if (enemy.type == 3) {
                boolean collisionX =
                        enemy.enemyXPos < 100 + playerClass.spriteWidth &&
                                enemy.enemyXPos + 50 > 100;

                boolean collisionY =
                        enemy.enemyYPos < playerClass.yPlayer + playerClass.spriteHeight &&
                                enemy.enemyYPos + 150 > playerClass.yPlayer;


                if (collisionX && collisionY && schildClass.HP == 0) {
                    GameApp.switchScreen("DeathScreen");
                } else if (collisionY && collisionX) {

                    schildClass.HP--;

                    if (schildClass.HP <= 0) {
                        schildClass.isactive = false;

                        powerUp.hasTimer = false;
                        powerUp.timeLeft = 0;
                        powerUp.timerStarted = false;
                    }

                    enemyClass.allEnemies.remove(0);
                    GameApp.addSound("SchildPickup", "Sounds/shieldPickup.mp3");
                    GameApp.playSound("SchildPickup");
                }
            }
        }
    }

    public static void genereerRandomPowerup(PowerupClass powerupClass,float delta) {
        powerupClass.spawntimer += delta;
        if (powerupClass.spawntimer > powerupClass.spawninterval) {
            Random r = new Random();
            int randomint = r.nextInt(1, 3);
            powerupClass.type = randomint;
            powerupClass.xPosition = 1200;
            powerupClass.spawntimer = 0f;
            System.out.println("i run");
        }
    }


    public static void updateSchildPowerup(float delta, PowerupClass powerUp, SchildClass schildClass) {

        // 1. Tekenen zolang hij nog niet is opgepakt
        if (!powerUp.powerupPickedup && powerUp.type == 1) {
            powerUp.xPosition -= powerUp.speed * delta;

            if (powerUp.xPosition > -200) {
                GameApp.drawTexture(powerUp.textureName, powerUp.xPosition, powerUp.yposition,110,110);
            }
        }

        // 2. Powerup is opgepakt → activeer shield éénmalig
        if (powerUp.powerupPickedup && powerUp.type == 1 && !powerUp.timerStarted) {

            schildClass.isactive = true;

            // Timer starten
            powerUp.hasTimer = true;
            powerUp.duration = 10f;
            powerUp.timeLeft = powerUp.duration;
            powerUp.timerStarted = true;
            schildClass.HP = 2;

            // Powerup verstoppen
            powerUp.xPosition = -9999;
            powerUp.yposition = -9999;
        }
    }

    public static void updateunlimitedKogels(float delta, PowerupClass powerUp,
                                             unlimitedAmmoPowerupClass unlimitedAmmoPowerupClass, PlayerClass player) {

        if (!powerUp.powerupPickedup && powerUp.type == 2) {
            powerUp.xPosition -= powerUp.speed * delta;

            if (powerUp.xPosition < 0) {
                return;
            }
            GameApp.drawTexture(powerUp.texurename2, powerUp.xPosition, powerUp.yposition,110,110);
        }

        if (powerUp.powerupPickedup && powerUp.type == 2) {
            unlimitedAmmoPowerupClass.isactive = true;


            player.isReloading = false;
            player.reloadStartTime = 0;

            player.ammo = 25;

            powerUp.xPosition = -100;
            powerUp.powerupPickedup = false;
        }
    }

    public static void unlimitedKogelsLogic(float delta, unlimitedAmmoPowerupClass unlimitedAmmoPowerupClass,
                                            PlayerClass player, PowerupClass powerupClass) {

        if (unlimitedAmmoPowerupClass.isactive) {

            if (player.ammo == 0) {
                unlimitedAmmoPowerupClass.isactive = false;
                unlimitedAmmoPowerupClass.currentTime = 0f;
                powerupClass.powerupPickedup = false;
                unlimitedAmmoPowerupClass.isactive = false;
            }

        }
    }


    public static void checkForPowerupPickup(PlayerClass player, PowerupClass powerUp,managerClass managerClass) {
        boolean yCollision = player.yPlayer < powerUp.yposition + powerUp.spriteHeight &&
                player.yPlayer + player.spriteHeight > powerUp.yposition;

        boolean xCollision = player.groundLevel < powerUp.xPosition + powerUp.spriteWidth &&
                player.groundLevel + player.groundLevel > powerUp.xPosition;

        if (yCollision && xCollision) {
            powerUp.powerupPickedup = true;
            managerClass.powerupActive = false;
        }

    }

    public static void activeSchildUpdate(SchildClass schildClass, PlayerClass playerClass) {
        if (schildClass.isactive) {
            GameApp.getSpriteBatch().setColor(1, 1, 1, 0.5f);
            if (schildClass.HP == 2) {
                GameApp.drawTexture(schildClass.spriteName, 65, playerClass.yPlayer - 10);
            } else if (schildClass.HP == 1) {
                GameApp.drawTexture(schildClass.spritename2, 65, playerClass.yPlayer - 10);
            } else {
                schildClass.isactive = false;
            }
            GameApp.getSpriteBatch().setColor(1, 1, 1, 1);
        }


    }

    public static void checkShieldCollisionKnife(SchildClass schildClass, ProjectileClass projectileClass, PlayerClass playerClass) {

        if (schildClass.isactive) {
            for (ProjectileClass p : projectileClass.projectiles) {

                float mesRechts = p.xposition + 50;
                float mesLinks = p.xposition;
                float mesTop = p.yposition + 50;
                float mesBottom = p.yposition;

                float schildRechts = 65 + 250;
                float schildLinks = 65 + 75;
                float schildTop = playerClass.yPlayer + 100;
                float schildBottom = playerClass.yPlayer;

                boolean hit =
                        mesLinks < schildRechts &&
                                mesRechts > schildLinks &&
                                mesBottom < schildTop &&
                                mesTop > schildBottom;

                if (hit) {
                    schildClass.HP--;
                    p.remove = true;
                    GameApp.addSound("SchildPickup", "Sounds/shieldPickup.mp3");
                    GameApp.playSound("SchildPickup");

                }
            }

            projectileClass.projectiles.removeIf(p -> p.remove);
        }
    }
    public static void tekenFlamethrower(float delta, flamethrowerClass flame, EnemyClass enemyClass) {
        if (flame == null || enemyClass.allEnemies.isEmpty()) return;
        EnemyClass enemy = enemyClass.allEnemies.get(0);

        // Alleen enemy type 2 gebruikt flame
        if (enemy.type != 2) return;

        // Flame volgt enemy
        flame.x = enemy.enemyXPos + 40;
        flame.y = enemy.enemyYPos + 40;

        // Animatie
        flame.frameTimer += delta;
        if (flame.frameTimer >= flamethrowerClass.FRAME_DURATION) {
            flame.frameTimer = 0;
            flame.frame++;
            if (flame.frame > 10) flame.frame = 1;
        }

        // 🔥 VISUELE FLAME (groeit van rechts naar links)
        GameApp.drawTexture("flame" + flame.frame, flame.x - 450, flame.y, 500, 100);
    }

    public static void checkFlamethrowerCollision(flamethrowerClass flame,
                                                  PlayerClass player,
                                                  SchildClass shield) {

        if (flame == null || flame.frame == 1) return;

        int f = flame.frame;

        float drawX = flame.x - 450;
        float drawY = flame.y;

        float flameRight  = drawX + 500;
        float flameLeft   = flameRight - flameHitboxWidth[f];
        float flameTop    = drawY + 100;
        float flameBottom = drawY;

        float playerLeft   = 100;
        float playerRight  = 100 + player.spriteWidth;
        float playerTop    = player.yPlayer + player.spriteHeight;
        float playerBottom = player.yPlayer;

        boolean overlap =
                flameLeft < playerRight &&
                        flameRight > playerLeft &&
                        flameBottom < playerTop &&
                        flameTop > playerBottom;

        if (!overlap) return;

        // 🛡 BLOCKING → speler blijft leven
        if (player.isBlocking) {

            // Als shield actief is → shield verliest HP
            if (shield.isactive && shield.HP > 0) {
                shield.HP--;
                if (shield.HP <= 0) shield.isactive = false;
            }

            // Flame resetten zodat hij niet blijft raken
            flame.frame = 1;
            return;
        }

        // 🟥 GEEN BLOCK → normale damage
        if (!shield.isactive || shield.HP <= 0) {
            GameApp.switchScreen("DeathScreen");
            return;
        }

        // Shield absorbeert hit
        shield.HP--;
        if (shield.HP <= 0) shield.isactive = false;

        flame.frame = 1;
    }



    public static void generateGameLogic(managerClass managerClass, float delta) {

        if (managerClass.spawnObstacle || managerClass.spawnPowerup || managerClass.spawnEnenmy) return;
        if (managerClass.powerupActive || managerClass.enemyActive || managerClass.obstacleActive) return;

        Random r = new Random();
        int random = r.nextInt(1, 3);

        if (random == managerClass.lastEvent) {
            managerClass.repeatCount++;
            if (managerClass.repeatCount >= 3) {
                // force switch
                random = (random == 1) ? 2 : 1;
                managerClass.repeatCount = 0;
            }
        } else {
            managerClass.repeatCount = 0;
        }

        managerClass.lastEvent = random;


        switch (random) {
            case 1:
                if (managerClass.enemytimer > 3) {
                    managerClass.spawnEnenmy = true;
                    managerClass.enemytimer = 0;
                }
                break;

            case 2:
                managerClass.spawnObstacle = true;
                break;
        }
    }

        public static  void updateManagerTimer(managerClass managerClass,float delta){
        if(!managerClass.enemyActive) {
            managerClass.enemytimer += delta;
        }


        }

        }





