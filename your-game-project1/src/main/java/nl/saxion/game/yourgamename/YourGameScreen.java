package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import nl.saxion.gameapp.GameApp;
import nl.saxion.gameapp.screens.ScalableGameScreen;
import static nl.saxion.game.yourgamename.Methodes_Rutger.*;
import static nl.saxion.game.yourgamename.Methodes_Maxje.*;
import static nl.saxion.game.yourgamename.Methodes_Lucas.*;

public class YourGameScreen extends ScalableGameScreen {
    public PlayerClass player;
    public EnemyClass enemyClass;
    public ProjectileClass projectileClass;
    PlayerClass Player = new PlayerClass();
    public YourGameScreen(PlayerClass player) {
        super(1280, 720);
        this.player = player; // resolutie 1280x720

    }

    @Override
    public void show() {
        Methodes_Lucas.LucasParallaxMethods.initParallax(0);
        // Geen extra setup nodig
        GameApp.addTexture("kogel", "img/kogel.png");
        enemyClass = new EnemyClass("img/chef.png","chef", 1100, 100, 100);
        projectileClass = new ProjectileClass("img/mes.png","mes", enemyClass.enemyXPos,enemyClass.enemyYPos + 30,300);

    }
    private float Worldx;
    @Override


    public void render(float delta) {
        super.render(delta);

        GameApp.addTexture("brocolli", "img/brocolli.png");


            // Scherm volledig zwart maken
            GameApp.clearScreen("black");

            GameApp.startSpriteRendering();
            Worldx += 300 * delta;
            Methodes_Lucas.LucasParallaxMethods.drawParallaxBackground(Worldx, getWorldWidth());
            update(Player, Player.filepath);
            updateEnenmy(delta, enemyClass);
            addProjectile(delta, projectileClass, enemyClass);
            updateProjectiles(delta, projectileClass, enemyClass);
            //updateBullets(player);
        GameApp.endSpriteRendering();


    }


    @Override
    public void hide() {
        Methodes_Lucas.LucasParallaxMethods.disposeParallax();
        // Geen cleanup nodig
    }
}