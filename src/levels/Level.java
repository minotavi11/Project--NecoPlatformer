package levels;

import entities.Crabby;
import main.Game;
import objects.*;
import utilz.HelpMethods;

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
    private ArrayList<Spike> spikes;
    private ArrayList<Living_Flesh> livingFlesh;

    private ArrayList<NextLevel> nextLevels;
    private ArrayList<PreviousLevel> previousLevels;


    private ArrayList<Big_Tree> bigTree;
    private ArrayList<BackdropTombstone> bigTreeVerdant;
    private ArrayList<Tree_Medium> mediumTree;
    private ArrayList<Tree_Small> smallTree;
    private ArrayList<Gravestone_Big> gravestoneBig;
    private ArrayList<Gravestone_Small> gravestoneSmall;

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

        createSpikes();
        createLivingFlesh();

        createBigTree();
        createBigTreeVerdant();
        createMediumTree();
        createSmallTree();
        createGravestoneBig();
        createGravestoneSmall();

        createNextLevel();
        createPreviousLevel();

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
    private void createSpikes() { spikes = HelpMethods.GetSpikes(img);}
    private void createLivingFlesh () {livingFlesh =HelpMethods.GetLivingFlesh(img);}
    private void createBigTree() { bigTree = HelpMethods.GetTreeBig(img);}
    private void createBigTreeVerdant() { bigTreeVerdant = HelpMethods.GetTreeBigVerdant(img);}
    private void createMediumTree() { mediumTree = HelpMethods.GetTreeMedium(img);}
    private void createSmallTree() { smallTree = HelpMethods.GetTreeSmall(img);}
    private void createGravestoneBig() { gravestoneBig = HelpMethods.GetGravestoneBig(img);}
    private void createGravestoneSmall() { gravestoneSmall = HelpMethods.GetGravestoneSmall(img);}
    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    private void createNextLevel(){ nextLevels = HelpMethods.GetNextLevel(img);}
    private void createPreviousLevel(){previousLevels = HelpMethods.GetPreviousLevel(img);}

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
    public BufferedImage getLevelImage() {
        return img;
    }

    public void setPlayerSpawn(Point spawn) {
        this.playerSpawn = spawn;
    }

    public ArrayList<Potion> getPotions(){return potions;}

    public ArrayList<GameContainer> getContainers(){return containers;}

    public ArrayList<NextLevel>  getNextLevels(){return nextLevels;}
    public ArrayList<PreviousLevel> getPreviousLevels(){return previousLevels;}

    public ArrayList<Spike> getSpikes(){return spikes;}
    public ArrayList<Living_Flesh> getLivingFlesh(){return livingFlesh;}
    public ArrayList<Gravestone_Big> getGravestoneBig(){return gravestoneBig;}
    public ArrayList<Gravestone_Small> getGravestoneSmall(){return gravestoneSmall;}
    public ArrayList<Big_Tree> getBigTree(){return bigTree;}
    public ArrayList<Tree_Medium> getMediumTree(){return mediumTree;}
    public ArrayList<Tree_Small> getSmallTree(){return smallTree;}
    public ArrayList<BackdropTombstone> getBigTreeVerdant() { return bigTreeVerdant;}
}
