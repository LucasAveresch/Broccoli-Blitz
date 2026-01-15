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
    public int hp = 3;
    public float currentTimer = 0f;
    public float spawnInterval = 2f;
    ArrayList<EnemyClass> allEnemies = new ArrayList<>();
    public boolean hasShotFlame = false;


    public EnemyClass(String filepath, String textureKey, String filepath2, String textureKey2, float startX, float startY, float speed) {
        this.textureKey = textureKey;
        this.textureKey2 = textureKey2;
        this.enemyXPos = startX;
        this.enemyYPos = startY;
        this.enemyspeed = speed;

        GameApp.addTexture(textureKey, filepath);
        GameApp.addTexture(textureKey2, filepath2);
        GameApp.addTexture("tank1", "img/tank1.png");
        GameApp.addTexture("tank2", "img/tank2.png");
        GameApp.addTexture("tank3", "img/tank3.png");
    }
}