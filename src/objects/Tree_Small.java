package objects;

import main.Game;

public class Tree_Small extends GameObject{
    public Tree_Small(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 9 *(-1) );//edit here
        hitbox.y += yDrawOffset;
    }
}
