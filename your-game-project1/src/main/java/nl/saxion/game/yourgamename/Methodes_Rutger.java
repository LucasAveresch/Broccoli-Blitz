package nl.saxion.game.yourgamename;

import com.badlogic.gdx.graphics.Color;
import nl.saxion.gameapp.GameApp;

import java.security.PublicKey;

public class Methodes_Rutger {
    public static void john(){
        System.out.println("Hoi");
    }

    //Ondergrond toevoegen
    public static void Underground(){
        GameApp.startShapeRenderingFilled();
        GameApp.drawRect(0, 0, 1280, 100, Color.GREEN);
        GameApp.endShapeRendering();

}
    //Player maken
    public static Integer Player(int yPlayer){
        GameApp.addTexture("spritechef", "img/spritechef.png");
        GameApp.drawTexture("spritechef", 100, yPlayer);
        return yPlayer;
}

    //Player springen
    public static void Jump(int yPlayer){

    }



}
