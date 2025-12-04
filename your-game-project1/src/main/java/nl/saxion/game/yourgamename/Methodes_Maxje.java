package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.game.yourgamename.PlayerClass.*;

public class Methodes_Maxje {


    public static void updateEnenmy(float delta, EnemyClass enemyClass) {
            enemyClass.enemyXPos -= enemyClass.enemyspeed * delta;

            if(enemyClass.enemyXPos < 0){
                enemyClass.enemyIsDead = true;
                return;

            }

            GameApp.drawTexture(enemyClass.textureKey, enemyClass.enemyXPos, enemyClass.enemyYPos);
    }
    public static void addProjectile(float delta, ProjectileClass projectileClass, EnemyClass enemyClass){
        projectileClass.spawnTimer += delta;
        if(projectileClass.spawnTimer > projectileClass.spawnInterval){
            projectileClass.spawnTimer = 0f;
        }

        ProjectileClass newknife = new ProjectileClass("img/mes.png","mes", enemyClass.enemyXPos,enemyClass.enemyYPos + 30,300);
        projectileClass.projectiles.add(newknife);



        GameApp.drawTexture(projectileClass.textureKey,projectileClass.xposition,projectileClass.yposition);
    }
    public static void updateProjectiles(float delta, ProjectileClass projectileClass, EnemyClass enemyClass){
        for(ProjectileClass knife : projectileClass.projectiles){
            knife.xposition -= knife.speed * delta;
            GameApp.drawTexture(knife.textureKey, knife.xposition, knife.yposition );
        }
    }
}
