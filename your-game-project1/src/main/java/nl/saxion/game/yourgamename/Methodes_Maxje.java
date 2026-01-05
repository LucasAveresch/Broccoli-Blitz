package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.game.yourgamename.PlayerClass.*;

import javax.swing.border.EmptyBorder;
import java.util.Random;

public class Methodes_Maxje {


    public static void updateEnemies(float delta, EnemyClass enemyClass) {
        if (enemyClass.allEnemies.size() == 1) {
            if (enemyClass.allEnemies.isEmpty()) return;
            EnemyClass enemy = enemyClass.allEnemies.get(0);
            if (enemy.type == 1) {
                enemy.enemyXPos -= enemy.enemyspeed * delta;

                if (enemy.enemyXPos < 0) {
                    enemyClass.allEnemies.remove(0);
                    return;


                }

                GameApp.drawTexture(enemy.textureKey, enemy.enemyXPos, enemy.enemyYPos);
            }

             else if (enemy.type == 2) {
                enemy.enemyXPos -= enemy.enemyspeed * delta;

                if (enemy.enemyXPos < 0) {
                    enemyClass.allEnemies.remove(0);
                    return;
                }

                GameApp.drawTexture(enemy.textureKey2, enemy.enemyXPos, enemy.enemyYPos);
            }
        }
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

            EnemyClass enemyClass1 = new EnemyClass("img/chef.png", "chef","img/enemy2.png","enemy2",1100, 150, 50);
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
                    300
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

    public static void checkCollisionEnemy(PlayerClass playerClass, EnemyClass enemyClass) {
        if(enemyClass.type == 1) {
            boolean collisionX = false;
            boolean collisionY = false;
            if (enemyClass.enemyXPos < 100 + playerClass.spriteWidth && enemyClass.enemyXPos > 100) {
                collisionX = true;
            }
            if (enemyClass.enemyYPos < playerClass.yPlayer + playerClass.spriteHeight && enemyClass.enemyYPos > playerClass.yPlayer) {
                collisionY = true;
            }
            if (collisionX && collisionY) {
                GameApp.switchScreen("DeathScreen");
            }
        } else if (enemyClass.type == 2){
            boolean collisionX = false;
            boolean collisionY = false;
            if (enemyClass.enemyXPos < 100 + playerClass.spriteWidth && enemyClass.enemyXPos > 100) {
                collisionX = true;
            }
            if (enemyClass.enemyYPos < playerClass.yPlayer + playerClass.spriteHeight && enemyClass.enemyYPos > playerClass.yPlayer) {
                collisionY = true;
            }
            if (collisionX && collisionY) {
                GameApp.switchScreen("DeathScreen");
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
    public static void updateunlimitedKogels(float delta, PowerupClass powerUp,unlimitedAmmoPowerupClass unlimitedAmmoPowerupClass) {
        if (!powerUp.powerupPickedup && powerUp.type == 2) {
            powerUp.xPosition -= powerUp.speed * delta;

            if (powerUp.xPosition < 0) {
                return;
            }
            GameApp.drawTexture(powerUp.texurename2, powerUp.xPosition, powerUp.yposition);
        }
        if (powerUp.powerupPickedup && powerUp.type == 2) {
            unlimitedAmmoPowerupClass.isactive = true;
            powerUp.xPosition = -100;
        }
    }
    public static void unlimitedKogelsLogic(float delta, unlimitedAmmoPowerupClass unlimitedAmmoPowerupClass,
                                            PlayerClass player,PowerupClass powerupClass){

        if(unlimitedAmmoPowerupClass.isactive) {
            player.maxAmmo = 100;
            player.ammo = 100;
            if (unlimitedAmmoPowerupClass.currentTime < unlimitedAmmoPowerupClass.maxTime) {
                unlimitedAmmoPowerupClass.currentTime += delta;
                System.out.println(unlimitedAmmoPowerupClass.currentTime);
            } else if (unlimitedAmmoPowerupClass.currentTime >= unlimitedAmmoPowerupClass.maxTime) {
                unlimitedAmmoPowerupClass.isactive = false;
                player.ammo = 5;
                player.maxAmmo = 5;
                unlimitedAmmoPowerupClass.currentTime = 0f;
                powerupClass.powerupPickedup = false;

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