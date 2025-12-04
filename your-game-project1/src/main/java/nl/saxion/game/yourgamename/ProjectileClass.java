package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

import java.util.ArrayList;

public class ProjectileClass {
    public float yposition;
    public float xposition;
    public float speed;
    public float spawnTimer;
    public float spawnInterval;
    public String textureKey;
    public String filepath;
    public boolean projectileOffScreen;
    public ArrayList<ProjectileClass> projectiles = new ArrayList<>();

    public ProjectileClass(String filepath, String textureKey, float xposition, float yposition, float speed){
        this.speed = speed;
        this.xposition = xposition;
        this.yposition = yposition;
        this.textureKey = textureKey;
        this.spawnInterval = 3.5f;
        this.spawnTimer = 3f;

        GameApp.addTexture(textureKey, filepath);
    }
}
