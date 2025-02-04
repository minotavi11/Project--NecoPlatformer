package objects;

import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import entities.Player;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.Constants.ObjectConstants.*;


public class ObjectManager {
    private Playing playing;
    private BufferedImage [][] potionImgs, containerImgs;
    private BufferedImage spikeImg;
    private BufferedImage bigTreeImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Big_Tree> bigTree;

    public ObjectManager(Playing playing){
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox){
        for(Potion p: potions){
            if(p.isActive()){
                if(hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
        }
    }
    public void  applyEffectToPlayer(Potion p){
            if(p.getObjType() == RED_POTION){
                playing.getPlayer().changeHealth(RED_POTION_VALUE);
            } else
                playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }
    public void checkObjectHit(Rectangle2D.Float attackbox){
        for(GameContainer gc: containers){
            if(gc.isActive() && !gc.doAnimation ) {
                if (gc.getHitbox().intersects(attackbox)) {
                    gc.setAnimation(true);
                    int type;
                    if (gc.getObjType() == BARREL) {
                        type = 1;
                        potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2),
                                (int) (gc.getHitbox().y + gc.getHitbox().height / 4),
                                type));
                        return;
                    } else if (gc.getObjType() == BOX) {
                        type = 0;
                        potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2),
                                (int) (gc.getHitbox().y - gc.getHitbox().height / 2),
                                type));
                    }
                }
            }
    }}

    /// ///////MAKING SURE NEW ITEMS LOAD WHEN I LOAD A NEW LEVEL!!!!!!!!!!!!/////////
    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers()) ;
        spikes= newLevel.getSpikes();
        bigTree = newLevel.getBigTree();


    }
    /// //LOAD OBJECTS///////////////////
    private void loadImgs() {
        //POTIONS
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for (int j = 0; j < potionImgs.length; j++)
            for (int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
        //CONTAINERS
        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
        //WHATEVER OBJECT IS NEXT
        spikeImg =LoadSave.GetSpriteAtlas(LoadSave.RUSTY_SPIKES);
        bigTreeImg = LoadSave.GetSpriteAtlas((LoadSave.TREE_BIG));

    }

    public void update(){
        //update potions
        for(Potion p : potions ) {
            if (p.isActive())
                p.update();
        }
        //update containers
        for(GameContainer gc : containers ) {
            if (gc.isActive())
                gc.update();
        }
        //
    }


    /// DRAW ALL ITEMS /////////////////////////////////////
    public void draw(Graphics g,int xLvlOffset){
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawSpikes(g, xLvlOffset);
        drawBigTrees(g, xLvlOffset);
    }

    private void drawSpikes(Graphics g, int xLvlOffset) {
        for(Spike s: spikes)
            g.drawImage(spikeImg, (int)(s.getHitbox().x- xLvlOffset),(int)(s.getHitbox().y - s.getyDrawOffset()),SPIKE_WIDTH,SPIKE_HEIGHT,null );
    }

    private void drawBigTrees(Graphics g, int xLvlOffset) {
        for (Big_Tree t : bigTree) {
            int x = t.getxDrawOffset();
            int y = t.getyDrawOffset();
//            System.out.println("Rendering Tree at: x = " + x + ", y = " + y + ", Adjusted X: " + (x - xLvlOffset));

            g.drawImage(bigTreeImg, x - xLvlOffset, y, BIGTREE_WIDTH, BIGTREE_HEIGHT, null);
        }
    }





    private void drawPotions(Graphics g, int xLvlOffset) {
        for(Potion p : potions ) {
            if (p.isActive()){
                int type=0;
                if(p.getObjType() == RED_POTION ) {
                    type = 1;
                }
                g.drawImage(potionImgs[type][p.getAniIndex()],
                        (int)(p.getHitbox().x- p.getxDrawOffset() - xLvlOffset),
                        (int)(p.getHitbox().y- p.getyDrawOffset()),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null);
            }
            }

    }
    private void drawContainers(Graphics g, int xLvlOffset) {
        for(GameContainer gc : containers ) {
            if (gc.isActive()){
                int type = 0;
                if(gc.getObjType() == BARREL ) {
                    type = 1;
                }
                g.drawImage(containerImgs[type][gc.getAniIndex()],
                        (int)(gc.getHitbox().x- gc.getxDrawOffset() - xLvlOffset),
                        (int)(gc.getHitbox().y- gc.getyDrawOffset()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
        }
    }

     public void resetAllObjects(){
        loadObjects(playing.getLevelManager().getCurrentLevel());
         for(GameContainer gc : containers ) {
             gc.reset();
             for (Potion p : potions)
                 p.reset();
         }
   }

}
