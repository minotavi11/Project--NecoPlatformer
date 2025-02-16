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
import static utilz.Constants.LevelConstants.*;

public class LowerLayerManager {
    private Playing playing;
    private LevelManager levelManager;
    private Player player;

    private ArrayList<NextLevel> nextLevel;
    private ArrayList<PreviousLevel> previousLevel;
    private ArrayList<Big_Tree> bigTree;
    private ArrayList<BackdropTombstone> backdropTombstones;
    private ArrayList<Tree_Medium> mediumTree;
    private ArrayList<Tree_Small> smallTree;


    private BufferedImage[][] bigTreeImg;
    private BufferedImage backdropTombstonesImg;
    private BufferedImage mediumTreeImg;
    private BufferedImage smallTreeImg;
    private BufferedImage nextLevelImg;
    private BufferedImage previousLevelImg;

    public LowerLayerManager(Playing playing) {
        this.playing = playing;
        this.levelManager = playing.getLevelManager(); // Add this line
        this.player = playing.getPlayer();
        loadImgs();
    }



    public void loadObjects(Level newLevel) {
        bigTree = newLevel.getBigTree();
        backdropTombstones = newLevel.getBigTreeVerdant();
        mediumTree = newLevel.getMediumTree();
        smallTree = newLevel.getSmallTree();
        nextLevel = newLevel.getNextLevels();
        previousLevel = newLevel.getPreviousLevels();
    }

    private void loadImgs() {
        BufferedImage bigTreeSprite = LoadSave.GetSpriteAtlas(LoadSave.TREE_BIG);
        bigTreeImg = new BufferedImage[1][5];
        for (int j = 0; j < bigTreeImg.length; j++)
            for (int i = 0; i < bigTreeImg[j].length; i++)
                bigTreeImg[j][i] = bigTreeSprite.getSubimage(128 * i, 128 * j, 128, 128);

        backdropTombstonesImg = LoadSave.GetSpriteAtlas(LoadSave.BACKDROPTOMBSTONE);
        mediumTreeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_MEDIUM);
        smallTreeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_SMALL);
        previousLevelImg =LoadSave.GetSpriteAtlas(LoadSave.PREVIOUS_LEVEL_IMG);
        nextLevelImg=LoadSave.GetSpriteAtlas(LoadSave.NEXT_LEVEL_IMG);
    }

    public void checkNextLevelEntered(Rectangle2D.Float hitbox){
        if (player == null) {
            System.err.println("Error: Player is null in LowerLayerManager!");
            return;
        }

        for (NextLevel nx: nextLevel) {
            if (nx.isActive() && hitbox.intersects(nx.getHitbox()) && player.isInteracting()) {
                loadNextLevel(nx);
                player.setInteraction(false);
                return;
            }
        }
    }


    public void checkPreviousLevelEntered(Rectangle2D.Float hitbox){
        if (player == null) {
            System.err.println("Error: Player is null in LowerLayerManager!");
            return;
        }

        for (PreviousLevel pv: previousLevel) {
            if (pv.isActive() && hitbox.intersects(pv.getHitbox()) && player.isInteracting()) {
                loadPreviousLevel(pv);
                player.setInteraction(false);
                return;
            }
        }
    }

    private void loadPreviousLevel(PreviousLevel pv) {
        playing.resetAll();
        levelManager.loadPreviousLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
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
        drawBackdropTombstones(g, xLvlOffset);
        drawBigTrees(g, xLvlOffset);
        drawMediumTrees(g, xLvlOffset);
        drawSmallTrees(g, xLvlOffset);
        drawNextLevel(g,xLvlOffset);
        drawPreviousLevel(g,xLvlOffset);
    }
    private void drawNextLevel(Graphics g, int xLvlOffset) {
        for (NextLevel nx : nextLevel)
            g.drawImage(nextLevelImg, (int) (nx.getHitbox().x - xLvlOffset + nx.getxDrawOffset()),
                    (int) (nx.getHitbox().y + nx.getyDrawOffset()),NEXT_LEVEL_WIDTH, NEXT_LEVEL_HEIGHT, null);
    }

    private void drawPreviousLevel(Graphics g, int xLvlOffset) {
        for (PreviousLevel pv : previousLevel)
            g.drawImage(previousLevelImg, (int) (pv.getHitbox().x - xLvlOffset + pv.getxDrawOffset()),
                    (int) (pv.getHitbox().y + pv.getyDrawOffset()),PREVIOUS_LEVEL_WIDTH, PREVIOUS_LEVEL_HEIGHT, null);
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

    private void drawBackdropTombstones(Graphics g, int xLvlOffset) {
        for (BackdropTombstone t : backdropTombstones)
            g.drawImage(backdropTombstonesImg, (int) (t.getHitbox().x - xLvlOffset + t.getxDrawOffset()),
                    (int) (t.getHitbox().y + t.getyDrawOffset()), BACKDROP_TOMBSTONE_WIDTH, BACKDROP_TOMBSTONE_HEIGHT, null);
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
