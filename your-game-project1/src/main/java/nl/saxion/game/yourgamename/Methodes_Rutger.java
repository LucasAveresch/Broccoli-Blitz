package nl.saxion.game.yourgamename;

import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;

public class Methodes_Rutger {

    public static void john(){
        System.out.println("Hoi");
    }
    public static void Underground(){
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0, 0, 1280, 100, Color.GREEN);
        GameApp.endShapeRendering();

    }
}
