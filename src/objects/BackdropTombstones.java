package objects;

import main.Game;

public class BackdropTombstones extends GameObject {
    public BackdropTombstones(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 3 *(-1));//edit here
        hitbox.y += yDrawOffset;//edit here
    }
}
