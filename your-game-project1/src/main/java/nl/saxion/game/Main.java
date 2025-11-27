package nl.saxion.game;

import nl.saxion.game.yourgamename.PlayerClass;
import nl.saxion.game.yourgamename.YourGameScreen;
import nl.saxion.game.yourgamename.MainMenuScreen;
import nl.saxion.gameapp.GameApp;
import static nl.saxion.game.yourgamename.Methodes_Rutger.*;
import static nl.saxion.game.yourgamename.Methodes_Maxje.*;
import static nl.saxion.game.yourgamename.Methodes_Lucas.*;

public class Main {
    public static void main(String[] args) {
        // Add screens
        PlayerClass player = new PlayerClass();
        GameApp.addScreen("MainMenuScreen", new MainMenuScreen(player));
        GameApp.addScreen("YourGameScreen", new YourGameScreen(player));

        // Start game loop and show main menu screen
        GameApp.start("Broccoli blitz", 1280, 720, 60, false, "MainMenuScreen");

    }
}
