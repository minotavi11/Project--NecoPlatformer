package objects;

import main.Game;

public class Tree_Small extends GameObject{
    public Tree_Small(int x, int y, int objType) {
        super(x, y, objType);
        xDrawOffset =x; //edit here
        yDrawOffset= (int)( y- Game.SCALE * 16 );//edit here
    }
}
