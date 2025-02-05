package objects;

import main.Game;

public class Statue extends GameObject{
    public Statue(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 16 );//edit here
        hitbox.y += yDrawOffset;//edit here
    }
}
