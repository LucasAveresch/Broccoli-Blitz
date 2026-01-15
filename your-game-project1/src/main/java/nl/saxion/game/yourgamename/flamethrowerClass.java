package nl.saxion.game.yourgamename;

import nl.saxion.gameapp.GameApp;

public class flamethrowerClass {
public float interval =0.1f;
public float timer;
public int frame= 0;
public float x,y;
public float speed = 750f;
public boolean hasstarted = false;
    public boolean fired = false;
    public flamethrowerClass(float x, float y) {
        this.x = x;
        this.y = y;

GameApp.addTexture("flame1","img/Flame/flame1.png");
GameApp.addTexture("flame2","img/Flame/flame2.png");
GameApp.addTexture("flame3","img/Flame/flame3.png");
GameApp.addTexture("flame4","img/Flame/flame4.png");
GameApp.addTexture("flame5","img/Flame/flame5.png");
GameApp.addTexture("flame6","img/Flame/flame6.png");
GameApp.addTexture("flame7","img/Flame/flame7.png");
GameApp.addTexture("flame8","img/Flame/flame8.png");
    }
}
