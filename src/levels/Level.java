package levels;

import entities.Crabby;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import utilz.HelpMethods;
import utilz.LoadSave;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.HelpMethods.*;

public class Level {

    private BufferedImage img;

    private int[][] lvlData;

    private ArrayList<Crabby> crabs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private int lvlTilesWide ;
    private int maxTilesOffset ;
    private int maxLvlOffsetX ;
    private Point playerSpawn ;

    public Level(BufferedImage img ){
        this.img=img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();

        calcLvlOffsets();
        calcPlayerSpawn();
    }


    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        crabs= GetCrabs(img);
    }
    private void createContainers() { containers = HelpMethods.GetContainers(img);
    }
    private void createPotions() { potions = HelpMethods.GetPotions(img);}

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }

    public int[][] getLevelData(){
        return lvlData;
    }
    public int getLvlOffset(){
        return maxLvlOffsetX;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    public Point getPlayerSpawn(){
        return playerSpawn;
    }

    public ArrayList<Potion> getPotions(){return potions;}

    public ArrayList<GameContainer> getContainers(){return containers;}

}
