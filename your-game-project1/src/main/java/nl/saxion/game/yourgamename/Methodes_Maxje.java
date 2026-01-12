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

                GameApp.drawTexture(enemy.textureKey2, enemy.enemyXPos, enemy.enemyYPos);

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
        public static void updateSubEnemies(SubEnemyClass subEnemyClass, float delta,EnemyClass enemyClass) {
        if(subEnemyClass.subEnemies.isEmpty()){ return; }
            SubEnemyClass subenemy  = subEnemyClass.subEnemies.get(0);
            if (!subEnemyClass.subEnemies.isEmpty()) {
                subenemy.enemyYPos -= (subEnemyClass.enemyspeed) * delta;
                if(subenemy.enemyYPos <= 100){
                    subenemy.enemyYPos = 100;
                }
            }
                if(subenemy.enemyYPos <= 100){
                subenemy.enemyXPos -= subEnemyClass.enemyspeed * delta;
                if(subenemy.enemyXPos < 0){
                    subEnemyClass.subEnemies.remove(subenemy);
                }
            }
            GameApp.drawTexture(subEnemyClass.textureKey, subenemy.enemyXPos, subenemy.enemyYPos);

        }


    public static void selectEnemyWillekeurig(float delta, EnemyClass enemyClass){
        Random r = new Random();
        boolean spawnNewEnemy = false;

        enemyClass.currentTimer += delta;

        if(enemyClass.currentTimer >= enemyClass.spawnInterval){
            spawnNewEnemy = true;
            enemyClass.currentTimer = 0f;
        }
        if(enemyClass.allEnemies.isEmpty() && spawnNewEnemy){
            int randomnumber = r.nextInt(1, 3);

            EnemyClass enemyClass1 = new EnemyClass("img/chef.png", "chef","img/enemy2.png","enemy2",1100, 150, 200);
            enemyClass1.type = randomnumber;
            if(enemyClass1.type == 2){
                enemyClass1.enemyYPos = enemyClass1.enemyYPos + 200;
            }

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
        boolean collsionx = false;
        boolean collsiony = false;
        for (ProjectileClass knife : projectileClass.projectiles) {
            if (knife.xposition > 100 + playerClass.spriteWidth && knife.xposition < 120 + playerClass.spriteWidth) {
                collsionx = true;
            }
            if (knife.yposition < playerClass.yPlayer + playerClass.spriteHeight
                    && knife.yposition + 16 > playerClass.yPlayer) {
                collsiony = true;
            }
        }
        if (collsionx && collsiony) {
            GameApp.switchScreen("DeathScreen");
        }
    }

    public static void checkCollisionEnemy(PlayerClass playerClass, EnemyClass enemyClass, SubEnemyClass subEnemyClass, SchildClass schildClass) {
        if(!enemyClass.allEnemies.isEmpty()){
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
            } else if (collisionY && collisionX){
                schildClass.HP--;
                enemyClass.allEnemies.remove(0);
                System.out.println(enemy.type);
                GameApp.addSound("SchildPickup","Sounds/shieldPickup.mp3");
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
            } else if ( collisionY && collisionX){
                schildClass.HP--;
                subEnemyClass.subEnemies.remove(0);
                GameApp.addSound("SchildPickup","Sounds/shieldPickup.mp3");
                GameApp.playSound("SchildPickup");
            }
        }
    }



    public static void updateSchildPowerup(float delta, PowerupClass powerUp, SchildClass schildClass) {
        if (!powerUp.powerupPickedup && powerUp.type == 1) {
            powerUp.xPosition -= powerUp.speed * delta;

            if (powerUp.xPosition < 0) {
                return;
            }
            GameApp.drawTexture(powerUp.textureName, powerUp.xPosition, powerUp.yposition);
        }
        if (powerUp.powerupPickedup && powerUp.type == 1) {
            schildClass.isactive = true;
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
                                            PlayerClass player,PowerupClass powerupClass){

        if(unlimitedAmmoPowerupClass.isactive) {

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
            }
            else {
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
                float schildTop = playerClass.yPlayer + 100 ;
                float schildBottom = playerClass.yPlayer;

                boolean hit =
                        mesLinks < schildRechts &&
                                mesRechts > schildLinks &&
                                mesBottom < schildTop &&
                                mesTop > schildBottom;

                if (hit) {
                    schildClass.HP--;
                    p.remove = true;
                    GameApp.addSound("SchildPickup","Sounds/shieldPickup.mp3");
                    GameApp.playSound("SchildPickup");

                }
            }

            projectileClass.projectiles.removeIf(p -> p.remove);
        }
    }
}