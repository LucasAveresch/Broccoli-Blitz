package nl.saxion.game.yourgamename;

import com.badlogic.gdx.math.Interpolation;
import nl.saxion.gameapp.GameApp;

public class PowerupClass {
    float xPosition = 500;
    float yposition = 150;
    float speed = 100;
    float spriteWidth = 100;
    float spriteHeight = 100;
    String filepath;
    String textureName;
    String filepath2;
    String texurename2;
    int type = 2;
    boolean powerupPickedup;
    public boolean hasTimer = false;
    public float duration = 0f;
    public float timeLeft = 0f;
    public boolean timerStarted = false;

    public PowerupClass(String filepath, String textureName, String filepath2, String texurename2){
        this.textureName = textureName;
        this.filepath = filepath;
        this.filepath2 = filepath2;
        this.texurename2 = texurename2;

        GameApp.addTexture(textureName, filepath);
        GameApp.addTexture(texurename2,filepath2);
    }

}
