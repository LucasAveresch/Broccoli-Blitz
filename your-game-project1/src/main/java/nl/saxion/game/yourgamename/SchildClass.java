package nl.saxion.game.yourgamename;

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
    String getFilepath2;
    boolean isactive;

    public SchildClass(String filepath, String spriteName, String spriteName2, String Filepath2){
        this.spriteName = spriteName;
        this.filepath = filepath;
        this.getFilepath2 = Filepath2;
        this.spritename2 = spriteName2;


    }
}
