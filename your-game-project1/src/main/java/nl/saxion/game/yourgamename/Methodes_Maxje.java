package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.game.yourgamename.PlayerClass.*;

import javax.swing.border.EmptyBorder;

public class Methodes_Maxje {


    public static void updateEnenmy(float delta, EnemyClass enemyClass) {
        enemyClass.enemyXPos -= enemyClass.enemyspeed * delta;

        if (enemyClass.enemyXPos < 0) {
            enemyClass.enemyIsDead = true;
            return;

        }

        GameApp.drawTexture(enemyClass.textureKey, enemyClass.enemyXPos, enemyClass.enemyYPos);
    }

    public static void addMes(float delta, ProjectileClass projectileClass, EnemyClass enemyClass) {
        // Stop als enemy dood is
        if (enemyClass.enemyIsDead) return;

        projectileClass.spawnTimer += delta;
        if (projectileClass.spawnTimer > projectileClass.spawnInterval) {
            projectileClass.spawnTimer = 0f;

            ProjectileClass newknife = new ProjectileClass(
                    "img/mes.png", "mes",
                    enemyClass.enemyXPos,
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
        if (collisionX && collisionY) ;
    }

    public static void updateSchildPowerup(float delta, PowerupClass powerUp, PlayerClass player, SchildClass schildClass) {
        if (!powerUp.powerupPickedup) {
            powerUp.xPosition -= powerUp.speed * delta;

            if (powerUp.xPosition < 0) {
                return;
            }
            GameApp.drawTexture(powerUp.textureName, powerUp.xPosition, powerUp.yposition);
        }
        if (powerUp.powerupPickedup) {
            schildClass.isactive = true;
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

    public static void checkShieldCollisionKnife(SchildClass schildClass, ProjectileClass projectileClass) {
        if (schildClass.isactive) {
            for (ProjectileClass p : projectileClass.projectiles) {

                float mesRechts = p.xposition + 50;
                float mesLinks = p.xposition;
                float mesTop = p.yposition + 50;
                float mesBottom = p.yposition;

                float schildRechts = schildClass.Xposition + 250;
                float schildLinks = schildClass.Xposition;
                float schildTop = schildClass.YPosition + 250;
                float schildBottom = schildClass.YPosition;

                boolean hit =
                        mesLinks < schildRechts &&
                                mesRechts > schildLinks &&
                                mesBottom < schildTop &&
                                mesTop > schildBottom;

                if (hit) {
                    schildClass.HP--;
                    p.remove = true;
                }
            }

            projectileClass.projectiles.removeIf(p -> p.remove);
        }
    }
}