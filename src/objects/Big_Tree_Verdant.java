package objects;

import main.Game;

public class Big_Tree_Verdant extends GameObject {
    public Big_Tree_Verdant(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 134 *(-1) );//edit here
        hitbox.y += yDrawOffset;
    }
}
