package objects;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

public class UpperLayerManager {

    private Playing playing;
    private BufferedImage[][] containerImgs;

    private ArrayList<GameContainer> containers;


    public UpperLayerManager(Playing playing){
        this.playing = playing;
        loadImgs();
    }





    /// ///////MAKING SURE NEW ITEMS LOAD WHEN I LOAD A NEW LEVEL!!!!!!!!!!!!/////////
    public void loadUpperObjects(Level newLevel) {
        containers = newLevel.getContainers();

    }
    /// //LOAD OBJECTS///////////////////
    private void loadImgs() {

        //CONTAINERS
        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
        //WHATEVER OBJECT IS NEXT

    }

    public void update(){

        //update containers
        for(GameContainer gc : containers ) {
            if (gc.isActive())
                gc.update();
        }
        //
    }


    /// DRAW ALL ITEMS /////////////////////////////////////
    public void draw(Graphics g, int xLvlOffset){
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
    }


    private void drawPotions(Graphics g, int xLvlOffset) {

    }
    private void drawContainers(Graphics g, int xLvlOffset) {
        for(GameContainer gc : containers ) {
            if (gc.isActive()){
                int type = 1;
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
        for(GameContainer gc : containers )
            gc.reset();
    }


}
