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

    private ArrayList<NextLevel> nextLevel;
    private ArrayList<Big_Tree> bigTree;
    private ArrayList<Big_Tree_Verdant> bigTreeVerdant;
    private ArrayList<Tree_Medium> mediumTree;
    private ArrayList<Tree_Small> smallTree;


    private BufferedImage[][] bigTreeImg;
    private BufferedImage bigTreeVerdantImg;
    private BufferedImage mediumTreeImg;
    private BufferedImage smallTreeImg;


    public LowerLayerManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }


    public void loadObjects(Level newLevel) {
        bigTree = newLevel.getBigTree();
        bigTreeVerdant = newLevel.getBigTreeVerdant();
        mediumTree = newLevel.getMediumTree();
        smallTree = newLevel.getSmallTree();
    }

    private void loadImgs() {
        BufferedImage bigTreeSprite = LoadSave.GetSpriteAtlas(LoadSave.TREE_BIG);
        bigTreeImg = new BufferedImage[1][5];
        for (int j = 0; j < bigTreeImg.length; j++)
            for (int i = 0; i < bigTreeImg[j].length; i++)
                bigTreeImg[j][i] = bigTreeSprite.getSubimage(128 * i, 128 * j, 128, 128);

        bigTreeVerdantImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_BIG_VERDANT);
        mediumTreeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_MEDIUM);
        smallTreeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_SMALL);
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

    public void update() {
        for (Big_Tree t : bigTree) {
            if (t.isActive())
                t.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBigTrees(g, xLvlOffset);
        drawBigTreesVerdant(g, xLvlOffset);
        drawMediumTrees(g, xLvlOffset);
        drawSmallTrees(g, xLvlOffset);
    }

    private void drawBigTrees(Graphics g, int xLvlOffset) {
        for (Big_Tree t : bigTree) {
            if (t.isActive()) {
                g.drawImage(bigTreeImg[0][t.getAniIndex()],
                        (int) (t.getHitbox().x - t.getxDrawOffset() - xLvlOffset),
                        (int) (t.getHitbox().y - t.getyDrawOffset()),
                        BIGTREE_WIDTH, BIGTREE_HEIGHT, null);
            }
        }
    }

    private void drawBigTreesVerdant(Graphics g, int xLvlOffset) {
        for (Big_Tree_Verdant t : bigTreeVerdant)
            g.drawImage(bigTreeVerdantImg, (int) (t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                    (int) (t.getHitbox().y + t.getyDrawOffset()), BIG_TREE_VERDANT_WIDTH, BIG_TREE_VERDANT_HEIGHT, null);
    }

    private void drawMediumTrees(Graphics g, int xLvlOffset) {
        for (Tree_Medium t : mediumTree)
            g.drawImage(mediumTreeImg, (int) (t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                    (int) (t.getHitbox().y + t.getyDrawOffset()), MEDIUMTREE_WIDTH, MEDIUMTREE_HEIGHT, null);
    }

    private void drawSmallTrees(Graphics g, int xLvlOffset) {
        for (Tree_Small t : smallTree)
            g.drawImage(smallTreeImg, (int) (t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                    (int) (t.getHitbox().y + t.getyDrawOffset()), SMALLTREE_WIDTH, SMALLTREE_HEIGHT, null);
    }

    public void resetAllObjects(){
        loadObjects(playing.getLevelManager().getCurrentLevel());

    }
}
