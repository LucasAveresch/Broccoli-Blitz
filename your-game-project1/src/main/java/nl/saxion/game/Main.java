package nl.saxion.game;

import nl.saxion.game.yourgamename.Methodes;
import nl.saxion.game.yourgamename.YourGameScreen;
import nl.saxion.game.yourgamename.MainMenuScreen;
import nl.saxion.gameapp.GameApp;
import static nl.saxion.game.yourgamename.Methodes.*;

public class Main {
    public static void main(String[] args) {
        // Add screens
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen());
        GameApp.addScreen("YourGameScreen", new YourGameScreen());

        // Start game loop and show main menu screen
        GameApp.start("Your Game Name", 800, 450, 60, false, "MainMenuScreen");
        john();
        john();
        System.out.println("3!");
        rutger();

    }
}
