package objects;

import main.Game;

public class Gravestone_Big extends GameObject{
    public Gravestone_Big(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(16,16);
        xDrawOffset =0; //edit here
        yDrawOffset= (int)(Game.SCALE * 3 *(-1));//edit here
        hitbox.y += yDrawOffset;//edit here

    }
}
