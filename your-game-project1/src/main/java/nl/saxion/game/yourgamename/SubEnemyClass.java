package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

import java.util.ArrayList;

public class SubEnemyClass {
    public float enemyXPos;
    public float enemyYPos;
    public float enemyspeed;
    public String textureKey;
    public float currentTimer = 0f;
    public float spawnInterval = 2f;
    ArrayList<SubEnemyClass> subEnemies = new ArrayList<>();

    public SubEnemyClass(String filepath, String textureKey, float startX, float startY, float speed) {
        this.textureKey = textureKey;
        this.enemyXPos = startX;
        this.enemyYPos = startY;
        this.enemyspeed = speed;

        GameApp.addTexture(textureKey, filepath);
    }
}
