package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class SchildClass {
    float Xposition;
    float YPosition;
    float speed;
    int HP = 2;
    float maxTime = 10f;
    float currentTime = 0f;
    String spriteName;
    String filepath;
    String spritename2;
    String filepath2;
    boolean isactive;

    public SchildClass(String filepath, String spriteName, String spriteName2, String Filepath2){
        this.spriteName = spriteName;
        this.filepath = filepath;
        this.filepath2 = Filepath2;
        this.spritename2 = spriteName2;


        GameApp.addTexture(spriteName,filepath);
        GameApp.addTexture(spriteName2,Filepath2);
    }
}
