package objects;

import main.Game;

public class BackdropTombstone extends GameObject {
    public BackdropTombstone(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 10 *(-1) );//edit here
        hitbox.y += yDrawOffset;
    }
}
