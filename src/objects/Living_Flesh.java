package objects;

import main.Game;

import java.awt.*;
import utilz.Constants.ObjectConstants.*;

import static utilz.Constants.ObjectConstants.LIVING_FLESH_HEIGHT_DEFAULT;
import static utilz.Constants.ObjectConstants.LIVING_FLESH_WIDTH_DEFAULT;

public class Living_Flesh extends GameObject {

    public Living_Flesh(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        // Initialize hitbox size
        initHitbox(20, 20);

// Calculate draw offsets to center the hitbox
        xDrawOffset = (int)((LIVING_FLESH_WIDTH_DEFAULT + hitbox.width -30) / 2);
        yDrawOffset = (int)((LIVING_FLESH_HEIGHT_DEFAULT + hitbox.height - 30) /2);

// Adjust hitbox position
        hitbox.x += xDrawOffset;
        hitbox.y += yDrawOffset - 5;

    }

    public void update(){
        updateAnimationTick();

    }
}
