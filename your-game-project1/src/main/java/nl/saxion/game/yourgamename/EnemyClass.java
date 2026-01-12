package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import nl.saxion.gameapp.GameApp;

import java.util.ArrayList;

public class EnemyClass {
    public float enemyXPos;
    public float enemyYPos;
    public float enemyspeed;
    public boolean enemyIsDead = false;
    public String textureKey;
    public String textureKey2;
    public int type = 2;
    public float currentTimer = 0f;
    public float spawnInterval = 5f;
    ArrayList<EnemyClass> allEnemies = new ArrayList<>();

    public EnemyClass(String filepath, String textureKey,String filepath2, String textureKey2, float startX, float startY, float speed) {
        this.textureKey = textureKey;
        this.textureKey2 = textureKey2;
        this.enemyXPos = startX;
        this.enemyYPos = startY;
        this.enemyspeed = speed;

        GameApp.addTexture(textureKey, filepath);
        GameApp.addTexture(textureKey2,filepath2);
    }
}

