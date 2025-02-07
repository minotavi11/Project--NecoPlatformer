package objects;

import main.Game;

public class Big_Tree extends GameObject{
    public Big_Tree(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE );//edit here
        hitbox.y += yDrawOffset + (int)(Game.SCALE *(-205));
        hitbox.x -= xDrawOffset/2;
    }
    public void update(){
        updateAnimationTick();
    }
}
