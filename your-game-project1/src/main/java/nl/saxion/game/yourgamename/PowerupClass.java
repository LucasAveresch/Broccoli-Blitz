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
    boolean powerupPickedup;

    public PowerupClass(String filepath, String textureName){
        this.textureName = textureName;
        this.filepath = filepath;

        GameApp.addTexture(textureName, filepath);
    }

}
