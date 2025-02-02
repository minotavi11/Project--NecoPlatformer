package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {



    protected int state;
    protected int aniTick, aniIndex;
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected float airSpeed;
    protected boolean inAir=false;


    protected int maxHealth ;
    protected int currentHealth;

    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;
    protected int Heal;
    protected int healCharges;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    protected void initHitbox( int width, int height) {
        hitbox = new Rectangle2D.Float(x, y,(int)(width *Game.SCALE), (int)(height*Game.SCALE));
    }

    protected void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.blue);
        g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }
    protected void drawHitbox(Graphics g, int xLvlOffset) {
        // For debugging the hitbox
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public int getAniIndex(){
        return aniIndex;
    }


    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
    public int getEnemyState(){
        return state;
    }

}
