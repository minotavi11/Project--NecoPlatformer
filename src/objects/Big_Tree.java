package objects;

import main.Game;

public class Big_Tree extends GameObject{
    public Big_Tree(int x, int y, int objType) {
        super(x, y, objType);
        xDrawOffset =x; //edit here
        yDrawOffset= (int)( y- Game.SCALE * 150 );//edit here
    }
}
