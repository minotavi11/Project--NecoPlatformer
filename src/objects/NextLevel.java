package objects;

import main.Game;

public class NextLevel extends GameObject{
    public NextLevel(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32,16);
        xDrawOffset =0;
        yDrawOffset= (int)(Game.SCALE *16);
        hitbox.y += yDrawOffset;
    }
}
