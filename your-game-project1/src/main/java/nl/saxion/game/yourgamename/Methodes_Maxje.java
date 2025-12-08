package nl.saxion.game.yourgamename;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import nl.saxion.gameapp.GameApp;
import nl.saxion.game.yourgamename.PlayerClass.*;

import javax.swing.border.EmptyBorder;

public class Methodes_Maxje {


    public static void updateEnenmy(float delta, EnemyClass enemyClass) {
        Texture enemysheet = new Texture("");
        TextureRegion[] loopanimatie = new TextureRegion[4];
        for (int i= 0; i < 4; i++ ){
            loopanimatie[i] = new TextureRegion(enemysheet, i * 128, 0, 128, 128);
        }
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
            if(knife.xposition > 100 + playerClass.spriteWidth && knife.xposition < 120 + playerClass.spriteWidth){
                collsionx = true;
            }
            if(knife.yposition < playerClass.yPlayer + playerClass.spriteHeight
            && knife.yposition  + 16 > playerClass.yPlayer ){
                collsiony = true;
            }
        }
        if(collsionx && collsiony){
            GameApp.switchScreen("DeathScreen");
        }
    }
    public static void checkCollisionEnemy(PlayerClass playerClass, EnemyClass enemyClass){
        boolean collisionX = false;
        boolean collisionY = false;
        if(enemyClass.enemyXPos < 100 + playerClass.spriteWidth && enemyClass.enemyXPos > 100){
            collisionX = true;
        }
        if (enemyClass.enemyYPos < playerClass.yPlayer + playerClass.spriteHeight && enemyClass.enemyYPos > playerClass.yPlayer){
            collisionY = true;
        }
        if(collisionX && collisionY){
            GameApp.switchScreen("MainMenuScreen");
        }
    }
}
