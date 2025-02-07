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
    private BufferedImage gravestoneBigImg;
    private BufferedImage gravestoneSmallImg;

    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Gravestone_Big> gravestoneBig;
    private ArrayList<Gravestone_Small> gravestoneSmall;

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

    public void applyEffectToPlayer(Potion p){
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
        }
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
        gravestoneBig = newLevel.getGravestoneBig();
        gravestoneSmall = newLevel.getGravestoneSmall();
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for (int j = 0; j < potionImgs.length; j++)
            for (int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.RUSTY_SPIKES);
        gravestoneBigImg = LoadSave.GetSpriteAtlas(LoadSave.GRAVESTONE_BIG);
        gravestoneSmallImg = LoadSave.GetSpriteAtlas(LoadSave.GRAVESTONE_SMALL);
    }

    public void update(){
        for(Potion p : potions ) {
            if (p.isActive())
                p.update();
        }
        for(GameContainer gc : containers ) {
            if (gc.isActive())
                gc.update();
        }
    }

    public void draw(Graphics g,int xLvlOffset){
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawSpikes(g, xLvlOffset);
        drawGravestoneBig(g, xLvlOffset);
        drawGravestoneSmall(g, xLvlOffset);
    }

    private void drawSpikes(Graphics g, int xLvlOffset) {
        for(Spike s: spikes)
            g.drawImage(spikeImg, (int)(s.getHitbox().x- xLvlOffset),(int)(s.getHitbox().y - s.getyDrawOffset()),SPIKE_WIDTH,SPIKE_HEIGHT,null );
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
                int type = gc.getObjType() == BARREL ? 1 : 0;
                g.drawImage(containerImgs[type][gc.getAniIndex()],
                        (int)(gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                        (int)(gc.getHitbox().y - gc.getyDrawOffset()),
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
