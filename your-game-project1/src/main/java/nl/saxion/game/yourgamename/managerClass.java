package nl.saxion.game.yourgamename;

public class managerClass {

    public boolean spawnEnenmy = false;
    public boolean spawnPowerup = false;
    public boolean spawnObstacle = false;

    public boolean powerupActive = false;
    public boolean enemyActive = false;
    public boolean obstacleActive = false;

    public boolean spawnNewEnemyTime = false;

    float enemytimer = 0f;

    public int lastEvent = 0;      // 1 = enemy, 2 = obstacle, 3 = powerup (if you add it)
    public int repeatCount = 0;
}
