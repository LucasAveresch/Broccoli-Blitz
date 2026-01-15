package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;
import java.util.ArrayList;

public class ProjectileClass {

    // Position & movement
    public float xposition;
    public float yposition;
    public float speed;

    // Spawning
    public float spawnTimer;
    public float spawnInterval;

    // Animation
    public String[] frames;
    public int currentFrame;
    public float frameTimer;
    public float frameInterval;

    // State
    public boolean remove;

    // Projectile list
    public ArrayList<ProjectileClass> projectiles = new ArrayList<>();

    // 🔹 Constructor for animated projectile
    public ProjectileClass(String[] frames, float xposition, float yposition, float speed) {
        this.frames = frames;
        this.xposition = xposition;
        this.yposition = yposition;
        this.speed = speed;

        this.currentFrame = 0;
        this.frameTimer = 0f;
        this.frameInterval = 0.08f; // animation speed

        this.spawnInterval = 3.5f;
        this.spawnTimer = 3f;

        // Load all textures
        for (int i = 0; i < frames.length; i++) {
            GameApp.addTexture("knife_" + i, frames[i]);
        }
    }

    public String getCurrentTexture() {
        return "knife_" + currentFrame;
    }
}
