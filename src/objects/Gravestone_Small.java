package objects;

import main.Game;

public class Gravestone_Small extends GameObject{
    public Gravestone_Small(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 4);//edit here
        hitbox.y += yDrawOffset;//edit here
    }
}
