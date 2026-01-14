package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.game.yourgamename.PlayerClass.*;

import javax.swing.border.EmptyBorder;
import java.util.Random;

public class Methodes_Maxje {


    public static void updateEnemies(float delta, EnemyClass enemyClass, SubEnemyClass subEnemyClass) {

        if (enemyClass.allEnemies.isEmpty()) return;
        EnemyClass enemy = enemyClass.allEnemies.get(0);
        if (enemy.type == 1) {
            enemy.enemyXPos -= enemy.enemyspeed * delta;

            if (enemy.enemyXPos < 0) {
                enemyClass.allEnemies.remove(0);
                enemy.type = 0;
                return;


            }

            GameApp.drawTexture(enemy.textureKey, enemy.enemyXPos, enemy.enemyYPos);
        } else if (enemy.type == 2) {
            enemy.enemyXPos -= enemy.enemyspeed * delta;

            if (enemy.enemyXPos < 0) {
                enemyClass.allEnemies.remove(0);
                enemy.type = 0;
                return;

            }

            GameApp.drawTexture(enemy.textureKey2, enemy.enemyXPos, enemy.enemyYPos, 175, 175);

            if (subEnemyClass.subEnemies.isEmpty()) {
                subEnemyClass.currentTimer += delta;
                if (subEnemyClass.currentTimer >= subEnemyClass.spawnInterval) {
                    subEnemyClass.currentTimer = 0f;
                    SubEnemyClass subEnemyClass1 = new SubEnemyClass("img/stokbrood.png", "StokbroodNoLegs", enemy.enemyXPos, enemy.enemyYPos, 100);
                    subEnemyClass.subEnemies.add(subEnemyClass1);
                }

            }

        }
    }

    public static void updateSubEnemies(SubEnemyClass subEnemyClass, float delta, EnemyClass enemyClass) {
        if (subEnemyClass.subEnemies.isEmpty()) {
            return;
        }
        SubEnemyClass subenemy = subEnemyClass.subEnemies.get(0);
        if (!subEnemyClass.subEnemies.isEmpty()) {
            subenemy.enemyYPos -= (subEnemyClass.enemyspeed) * delta;
            if (subenemy.enemyYPos <= 100) {
                subenemy.enemyYPos = 100;
            }
        }
        if (subenemy.enemyYPos <= 100) {
            subenemy.enemyXPos -= subEnemyClass.enemyspeed * delta;
            if (subenemy.enemyXPos < 0) {
                subEnemyClass.subEnemies.remove(subenemy);
            }
        }
        GameApp.drawTexture(subEnemyClass.textureKey, subenemy.enemyXPos, subenemy.enemyYPos);

    }


    public static void selectEnemyWillekeurig(float delta, EnemyClass enemyClass) {
        Random r = new Random();
        boolean spawnNewEnemy = false;

        enemyClass.currentTimer += delta;

        if (enemyClass.currentTimer >= enemyClass.spawnInterval) {
            spawnNewEnemy = true;
            enemyClass.currentTimer = 0f;
        }
        if (enemyClass.allEnemies.isEmpty() && spawnNewEnemy) {
            int randomnumber = r.nextInt(1, 3);

            EnemyClass enemyClass1 = new EnemyClass("img/chef.png", "chef", "img/ketchup.png", "enemy2", 1100, 150, 200);
            enemyClass1.type = randomnumber;


            enemyClass.allEnemies.add(enemyClass1);
            spawnNewEnemy = false;
        }
    }


    public static void addMes(float delta, ProjectileClass projectileClass, EnemyClass enemyClass) {
        if (enemyClass.allEnemies.isEmpty() || enemyClass.allEnemies.get(0).type != 1) return;

        projectileClass.spawnTimer += delta;
        if (projectileClass.spawnTimer > projectileClass.spawnInterval) {
            projectileClass.spawnTimer = 0f;

            ProjectileClass newknife = new ProjectileClass(
                    "img/mes.png", "mes",
                    enemyClass.allEnemies.get(0).enemyXPos,
                    enemyClass.enemyYPos + 30,
                    600
            );

            projectileClass.projectiles.add(newknife);
            GameApp.drawTexture(newknife.textureKey, newknife.xposition, newknife.yposition);
        }
    }

    public static void updateMes(float delta, ProjectileClass projectileClass, EnemyClass enemyClass) {
        for (ProjectileClass knife : projectileClass.projectiles) {
            knife.xposition -= knife.speed * delta;
            GameApp.drawTexture(knife.textureKey, knife.xposition, knife.yposition);
        }
    }

    public static void checkCollsionMes(ProjectileClass projectileClass, PlayerClass playerClass) {

        // Stop als er geen messen zijn
        if (projectileClass.projectiles.isEmpty()) return;

        // We checken alleen het eerste mes (zoals jij al doet)
        ProjectileClass knife = projectileClass.projectiles.get(0);

        boolean collisionX =
                knife.xposition > 100 + playerClass.spriteWidth &&
                        knife.xposition < 120 + playerClass.spriteWidth;

        boolean collisionY =
                knife.yposition < playerClass.yPlayer + playerClass.spriteHeight &&
                        knife.yposition + 16 > playerClass.yPlayer;

        if (collisionX && collisionY) {

            // 🟩 BLOCK → mes tegenhouden
            if (playerClass.isBlocking) {

                GameApp.addSound("block", "Sounds/block.mp3");
                GameApp.playSound("block", 5.0f);

                projectileClass.projectiles.remove(0); // gewoon simpel verwijderen
                return;
            }

            // 🟥 GEEN BLOCK → dood
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

                    // ❗ Schild gebroken → timer stoppen
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
        }
        if (!subEnemyClass.subEnemies.isEmpty()) {
            SubEnemyClass subenemy = subEnemyClass.subEnemies.get(0);
            boolean collisionX =
                    subenemy.enemyXPos < 125 + playerClass.spriteWidth &&
                            subenemy.enemyXPos + subenemy.enemyXPos + 100 > 100;

            boolean collisionY =
                    subenemy.enemyYPos < playerClass.yPlayer + playerClass.spriteHeight &&
                            subenemy.enemyYPos + subenemy.enemyYPos + 50 > playerClass.yPlayer;


            if (collisionX && collisionY && schildClass.HP == 0) {
                GameApp.switchScreen("DeathScreen");
            } else if (collisionY && collisionX) {
                schildClass.HP--;
                subEnemyClass.subEnemies.remove(0);
                GameApp.addSound("SchildPickup", "Sounds/shieldPickup.mp3");
                GameApp.playSound("SchildPickup");
            }
        }
    }

    public static void genereerRandomPowerup(PowerupClass powerupClass, float delta) {
        powerupClass.spawntimer += delta;
        if (powerupClass.spawntimer >= powerupClass.spawninterval) {
            Random r = new Random();
            int randomint = r.nextInt(1, 3);
            powerupClass.type = randomint;
            powerupClass.xPosition = 1200;
            powerupClass.spawntimer = 0f;
        }
    }


    public static void updateSchildPowerup(float delta, PowerupClass powerUp, SchildClass schildClass) {

        // 1. Tekenen zolang hij nog niet is opgepakt
        if (!powerUp.powerupPickedup && powerUp.type == 1) {
            powerUp.xPosition -= powerUp.speed * delta;

            if (powerUp.xPosition > -200) {
                GameApp.drawTexture(powerUp.textureName, powerUp.xPosition, powerUp.yposition);
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
            GameApp.drawTexture(powerUp.texurename2, powerUp.xPosition, powerUp.yposition);
        }

        if (powerUp.powerupPickedup && powerUp.type == 2) {
            unlimitedAmmoPowerupClass.isactive = true;

            // ❗ FIX: reload direct stoppen
            player.isReloading = false;
            player.reloadStartTime = 0;

            // ❗ FIX: ammo instellen op powerup waarden
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


    public static void checkForPowerupPickup(PlayerClass player, PowerupClass powerUp) {
        boolean yCollision = player.yPlayer < powerUp.yposition + powerUp.spriteHeight &&
                player.yPlayer + player.spriteHeight > powerUp.yposition;

        boolean xCollision = player.groundLevel < powerUp.xPosition + powerUp.spriteWidth &&
                player.groundLevel + player.groundLevel > powerUp.xPosition;

        if (yCollision && xCollision) {
            powerUp.powerupPickedup = true;
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

    public static void tekenFlamethrower(float delta, flamethrowerClass flame, EnemyClass enemy) {

        // If no enemies AND the flame hasn't fired yet → reset
        if (enemy.allEnemies.isEmpty() && !flame.fired) {
            flame.frame = 0;
            flame.fired = false;
            flame.timer = 0f;
            return;
        }

        EnemyClass e = null;

        // Only read enemy if it exists
        if (!enemy.allEnemies.isEmpty()) {
            e = enemy.allEnemies.get(0);
        }

        // -------------------------
        // PHASE 1: Frames 1–7 (enemy must exist AND be type 2)
        // -------------------------
        if (!flame.fired) {

            // If enemy disappeared or changed type → reset and stop
            if (e == null || e.type != 2) {
                flame.frame = 0;
                flame.fired = false;
                flame.timer = 0f;
                return;
            }

            flame.timer += delta;

            if (flame.timer >= flame.interval) {
                flame.timer = 0f;
                flame.frame++;

                if (flame.frame >= 7) {
                    flame.fired = true;

                    // Projectile starts at enemy position
                    flame.x = e.enemyXPos;
                    flame.y = e.enemyYPos;
                }
            }

            String frameName = "flame" + (flame.frame + 1);
            GameApp.drawTexture(frameName, e.enemyXPos - 20, e.enemyYPos, 100, 100);
            return;
        }

        // -------------------------
        // PHASE 2: Projectile (ALWAYS continues, even if enemy is gone)
        // -------------------------
        flame.x -= flame.speed * delta;

        GameApp.drawTexture("flame8", flame.x, flame.y, 100, 100);

        // Reset when projectile leaves screen
        if (flame.x < -100) {
            flame.frame = 0;
            flame.fired = false;
            flame.timer = 0f;
        }
    }}
