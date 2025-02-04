package objects;

import main.Game;

public class Gravestone_Big extends GameObject{
    public Gravestone_Big(int x, int y, int objType) {
        super(x, y, objType);
        xDrawOffset =x; //edit here
        yDrawOffset= (int)( y- Game.SCALE * 16 );//edit here

    }
}
