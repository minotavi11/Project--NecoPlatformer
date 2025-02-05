package utilz;

import entities.Crabby;
import main.Game;
import objects.*;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.ObjectConstants.*;


public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {

        int value = lvlData[yTile][xTile];
        if (value >= 48 || value < 0 || value != 11)
            return true;

        return false;
    }


    //public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed  ){
//    int currentTile =(int)(hitbox.x /Game.TILES_SIZE);
//    if (xSpeed >0){
//        //RIGHT
//        int tileXPos = currentTile * Game.TILES_SIZE;
//        int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
//        return tileXPos + xOffset - 1;
//    }else
//        //LEFT
//        return  currentTile * Game.TILES_SIZE;
//
//
//}
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            //FALLING - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            //JUMPING
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        //check pixel bellow bottom left and bottom right corners
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;
    }


    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if(xSpeed >0 )
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }


    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData){
        for (int i = 0; i < xEnd -xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))// [] lvlData acceptso nly one element, but [][]lvlData accepts two, and so on...
                return false;
            if (!IsTileSolid(xStart + i, y+1, lvlData))// [] lvlData acceptso nly one element, but [][]lvlData accepts two, and so on...
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXtile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXtile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXtile > secondXtile)
            return IsAllTilesWalkable(secondXtile,firstXtile,  yTile, lvlData);
        else
            return IsAllTilesWalkable(firstXtile, secondXtile, yTile, lvlData);


    }


    public static int[][] GetLevelData(BufferedImage img){

        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getRed();//basically, we look at the red value on RGB scale, and if it's of certain type, we select the respective tile from the tilesheet, in correspondence to the current value
                if (value >=48)
                    value =0;
                lvlData[j][i] = value;
            }
        }
        return  lvlData;
    }

    public static ArrayList<Crabby> GetCrabs( BufferedImage img){
        ArrayList<Crabby> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getGreen();// identifies on which color on the level map the crab will spawn
                if (value ==CRABBY)
                    list.add(new Crabby(i* Game.TILES_SIZE, j*Game.TILES_SIZE));
            }
        }
        return list;
    }


    public  static Point GetPlayerSpawn(BufferedImage img){
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getGreen();// identifies on which color on the level map the crab will spawn
                if (value ==100)
                    return new Point(i * Game.TILES_SIZE, j*Game.TILES_SIZE);
            }
        }
        return new Point(1 * Game.TILES_SIZE, 1*Game.TILES_SIZE);
    }


    public static ArrayList<Potion> GetPotions(BufferedImage img){
        ArrayList<Potion> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value ==RED_POTION || value == BLUE_POTION)
                    list.add(new Potion(i* Game.TILES_SIZE, j*Game.TILES_SIZE, value));

            }
        }
        return list;
    }

    public static ArrayList<GameContainer> GetContainers(BufferedImage img){
        ArrayList<GameContainer> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value ==BOX || value == BARREL)
                    list.add(new GameContainer(i* Game.TILES_SIZE, j*Game.TILES_SIZE, value));

            }
        }
        return list;
    }


    public static ArrayList<Spike> GetSpikes(BufferedImage img) {
        ArrayList<Spike> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value == RUSTY_SPIKES)
                    list.add(new Spike(i* Game.TILES_SIZE, j*Game.TILES_SIZE, RUSTY_SPIKES));

            }
        }
        return list;
    }

    public static ArrayList<Big_Tree> GetTreeBig(BufferedImage img) {
        ArrayList<Big_Tree> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value == TREE_BIG)
                    list.add(new Big_Tree(i* Game.TILES_SIZE, j*Game.TILES_SIZE, TREE_BIG));

            }
        }
        return list;
    }
    public static ArrayList<Gravestone_Big> GetGravestoneBig(BufferedImage img) {
        ArrayList<Gravestone_Big> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value == GRAVESTONE_BIG)
                    list.add(new Gravestone_Big(i* Game.TILES_SIZE, j*Game.TILES_SIZE, GRAVESTONE_BIG));

            }
        }
        return list;
    }
    public static ArrayList<Gravestone_Small> GetGravestoneSmall(BufferedImage img) {
        ArrayList<Gravestone_Small> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value == GRAVESTONE_SMALL)
                    list.add(new Gravestone_Small(i* Game.TILES_SIZE, j*Game.TILES_SIZE, GRAVESTONE_SMALL));

            }
        }
        return list;
    }
    public static ArrayList<Big_Tree_Verdant> GetTreeBigVerdant(BufferedImage img) {
        ArrayList<Big_Tree_Verdant> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value == TREE_BIG_VERDANT)
                    list.add(new Big_Tree_Verdant(i* Game.TILES_SIZE, j*Game.TILES_SIZE, TREE_BIG_VERDANT));

            }
        }
        return list;
    }

    public static ArrayList<Tree_Medium> GetTreeMedium(BufferedImage img) {
        ArrayList<Tree_Medium> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value == TREE_MEDIUM)
                    list.add(new Tree_Medium(i* Game.TILES_SIZE, j*Game.TILES_SIZE, TREE_MEDIUM));

            }
        }
        return list;
    }
    public static ArrayList<Tree_Small> GetTreeSmall(BufferedImage img) {
        ArrayList<Tree_Small> list = new ArrayList<>();
        for(int j=0; j< img.getHeight(); j++){
            for(int i=0; i<img.getWidth(); i++){
                Color color = new Color(img.getRGB(i,j));
                int value =color.getBlue();// identifies on which color on the level map the crab will spawn
                if (value == TREE_SMALL)
                    list.add(new Tree_Small(i* Game.TILES_SIZE, j*Game.TILES_SIZE, TREE_SMALL));

            }
        }
        return list;
    }
}






