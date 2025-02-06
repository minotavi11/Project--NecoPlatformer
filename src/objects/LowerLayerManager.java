package objects;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import levels.LevelManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

public class LowerLayerManager {
        private Playing playing;
        private LevelManager levelManager;
        private Player player;
        private BufferedImage bigTreeImg;
        private BufferedImage bigTreeVerdantImg;
        private BufferedImage mediumTreeImg;
        private BufferedImage smallTreeImg;
        private BufferedImage gravestoneBigImg;
        private BufferedImage gravestoneSmallImg;

        private ArrayList<NextLevel> nextLevel;
        private ArrayList<Gravestone_Big> gravestoneBig;
        private ArrayList<Gravestone_Small> gravestoneSmall;
        private ArrayList<Big_Tree> bigTree;
        private ArrayList<Big_Tree_Verdant> bigTreeVerdant;
        private ArrayList<Tree_Medium> mediumTree;
        private ArrayList<Tree_Small> smallTree;

        public LowerLayerManager(Playing playing){
            this.playing = playing;
            loadImgs();
        }



        public void checkNextLevelEntered(Rectangle2D.Float hitbox){
            for(NextLevel nx: nextLevel){
                if(nx.isActive()){
                    if(hitbox.intersects(nx.getHitbox()) && player.isInteracting()) {
                        loadNextLevel(nx);
                    }
                }
            }
        }

    private void loadNextLevel(NextLevel nx) {
        playing.resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }


    /// ///////MAKING SURE NEW ITEMS LOAD WHEN I LOAD A NEW LEVEL!!!!!!!!!!!!/////////
        public void loadObjects(Level newLevel) {
            gravestoneBig = newLevel.getGravestoneBig();
            gravestoneSmall=newLevel.getGravestoneSmall();
            bigTree = newLevel.getBigTree();
            bigTreeVerdant =newLevel.getBigTreeVerdant();
            mediumTree = newLevel.getMediumTree();
            smallTree = newLevel.getSmallTree();

        }
        /// //LOAD OBJECTS///////////////////
        private void loadImgs() {
            //WHATEVER OBJECT IS NEXT
            bigTreeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_BIG);
            bigTreeVerdantImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_BIG_VERDANT);
            mediumTreeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_MEDIUM);
            smallTreeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_SMALL);
            gravestoneBigImg = LoadSave.GetSpriteAtlas(LoadSave.GRAVESTONE_BIG);
            gravestoneSmallImg = LoadSave.GetSpriteAtlas(LoadSave.GRAVESTONE_SMALL);

        }

        public void update(){
            //update anything IF needed
        }


        /// DRAW ALL ITEMS /////////////////////////////////////
        public void draw(Graphics g, int xLvlOffset){
            drawBigTrees(g, xLvlOffset);
            drawBigTreesVerdant(g, xLvlOffset);
            drawMediumTrees(g, xLvlOffset);
            drawSmallTrees(g, xLvlOffset);
            drawGravestoneBig(g, xLvlOffset);
            drawGravestoneSmall(g, xLvlOffset);
        }



        private void drawBigTrees(Graphics g, int xLvlOffset) {
            for (Big_Tree t : bigTree)
                g.drawImage(bigTreeImg, (int)(t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                        (int)(t.getHitbox().y + t.getyDrawOffset()), BIGTREE_WIDTH, BIGTREE_HEIGHT, null);

        }
        private void drawBigTreesVerdant(Graphics g, int xLvlOffset) {
            for (Big_Tree_Verdant t : bigTreeVerdant)
                g.drawImage(bigTreeVerdantImg, (int)(t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                        (int)(t.getHitbox().y + t.getyDrawOffset()), BIG_TREE_VERDANT_WIDTH, BIG_TREE_VERDANT_HEIGHT, null);

        }

        private void drawMediumTrees(Graphics g, int xLvlOffset) {
            for (Tree_Medium t : mediumTree)
                g.drawImage(mediumTreeImg, (int)(t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                        (int)(t.getHitbox().y + t.getyDrawOffset()), MEDIUMTREE_WIDTH, MEDIUMTREE_HEIGHT, null);

        }

        private void drawSmallTrees(Graphics g, int xLvlOffset) {
            for (Tree_Small t : smallTree)
                g.drawImage(smallTreeImg, (int)(t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                        (int)(t.getHitbox().y + t.getyDrawOffset()), SMALLTREE_WIDTH, SMALLTREE_HEIGHT, null);

        }

        private void drawGravestoneBig(Graphics g, int xLvlOffset) {
            for (Gravestone_Big gb : gravestoneBig)
                g.drawImage(gravestoneBigImg, (int)(gb.getHitbox().x - xLvlOffset + gb.getxDrawOffset()),
                        (int)(gb.getHitbox().y + gb.getyDrawOffset()), BIGGRAVESTONE_WIDTH, BIGGRAVESTONE_HEIGHT, null);

        }

        private void drawGravestoneSmall(Graphics g, int xLvlOffset) {
            for (Gravestone_Small gb : gravestoneSmall)
                g.drawImage(gravestoneSmallImg, (int)(gb.getHitbox().x - xLvlOffset + gb.getxDrawOffset()),
                        (int)(gb.getHitbox().y + gb.getyDrawOffset()), SMALLGRAVESTONE_WIDTH, SMALLGRAVESTONE_HEIGHT, null);

        }








        public void resetAllObjects(){
            loadObjects(playing.getLevelManager().getCurrentLevel());

        }

}

