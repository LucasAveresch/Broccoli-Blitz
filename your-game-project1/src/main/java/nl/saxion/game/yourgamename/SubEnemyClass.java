package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

import java.util.ArrayList;

public class SubEnemyClass {
    public float enemyXPos;
    public float enemyYPos;
    public float enemyspeed;
    public boolean enemyIsDead = false;
    public String textureKey;
    public String textureKey2;
    public int type = 1;
    public float currentTimer = 5f;
    public float spawnInterval = 10f;
    ArrayList<EnemyClass> subEnemies = new ArrayList<>();

    public SubEnemyClass(String filepath, String textureKey,String filepath2, String textureKey2, float startX, float startY, float speed) {
        this.textureKey = textureKey;
        this.textureKey2 = textureKey2;
        this.enemyXPos = startX;
        this.enemyYPos = startY;
        this.enemyspeed = speed;

        GameApp.addTexture(textureKey, filepath);
        GameApp.addTexture(textureKey2,filepath2);
    }
}
