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


    public EnemyClass(String filepath, String textureKey, float startX, float startY, float speed) {
        this.textureKey = textureKey;
        this.enemyXPos = startX;
        this.enemyYPos = startY;
        this.enemyspeed = speed;
/
        GameApp.addTexture(textureKey, filepath);
    }
}

