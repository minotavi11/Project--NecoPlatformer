package objects;

import main.Game;

public class Tree_Medium extends GameObject{
    public Tree_Medium(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 47 *(-1) );//edit here
        hitbox.y += yDrawOffset;
    }
}
