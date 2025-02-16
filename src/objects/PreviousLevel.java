package objects;

import main.Game;

public class PreviousLevel extends GameObject{
    public PreviousLevel(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(120,110);
        xDrawOffset =0;
        yDrawOffset= (int)(Game.SCALE *(-38));
        hitbox.y += yDrawOffset;
    }
}
