package nl.saxion.game.yourgamename;

public class managerClass {

    public boolean spawnEnenmy = false;
    public boolean spawnPowerup = false;
    public boolean spawnObstacle = false;

    public boolean powerupActive = false;
    public boolean enemyActive = false;
    public boolean obstacleActive = false;


    float enemytimer = 0f;


    public float enemyCooldown = 0f;
    public boolean enemyJustSpawned = false;

    public boolean inParkourPhase = true;
    public boolean spawnInterParkourEnemy = false;


    public int parkourToSpawn = 0;
    public int parkourSpawned = 0;

    public int enemiesToSpawn = 0;
    public int enemiesSpawned = 0;

}
