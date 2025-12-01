package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import nl.saxion.game.yourgamename.PlayerClass.*;

public class Methodes_Maxje {


    public static void updateEnenmy(float delta, EnemyClass enemyClass) {
        while (enemyClass.enemyIsDead) {
            enemyClass.enemyXPos -= enemyClass.enemyspeed * delta;

            GameApp.drawTexture(enemyClass.textureKey, enemyClass.enemyXPos, enemyClass.enemyYPos);

            if (enemyClass.enemyXPos <= 500) {
                enemyClass.enemyIsDead = true;

            }

        }
        GameApp.disposeTexture(enemyClass.textureKey);
    }
}
