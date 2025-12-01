package nl.saxion.game.yourgamename;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import nl.saxion.gameapp.GameApp;
import org.w3c.dom.Text;

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
    public static Texture Player(int yPlayer, String filepath){
        Texture chefTexture = new Texture(Gdx.files.internal(filepath));
        GameApp.addTexture("spritechef", filepath);
        GameApp.drawTexture("spritechef", 100, yPlayer);
        return chefTexture;
}

    //Player springen
    public static void Jump(int yPlayer, Texture Texture){
        if (GameApp.isKeyPressed(32)){
            System.out.println("kaas");
        GameApp.drawTexture("spritechef",100, yPlayer+200);}

    }



}
