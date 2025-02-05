package entities;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;

public class Crabby extends Enemy{
    //Attack Box

    private int attackBoxOffsetX;
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox( 22, 19);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y, (int)(82 * Game.SCALE), (int)(19 * Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE * 30);
    }


    public void update(int[][] lvlData, Player player){
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }


    private void updateBehavior(int[][] lvlData, Player player){
        if(firstUpdate)
            firstUpdateCheck(lvlData);
        if(inAir){
            updateInAir(lvlData);
        }else{
            //PATROLLING PART OF CODE FOR MOBS
            switch (state){
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    try {
                        // Code that may throw ArrayIndexOutOfBoundsException
                        // Example:
                        // someArray[index] = someValue;

                        if(canSeePlayer(lvlData,player)) {
                            turnTowardsPlayer(player);
                            if (isPlayerCloseForAttack(player))
                                newState(ATTACK);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // Handle the exception here

                        // Example:
                        // System.out.println("Index out of bounds!");
                        e.printStackTrace(); // This prints the exception trace, including the line number and method where the exception occurred
                        System.exit(1);
                    }


                   move(lvlData);
                    break;
                case ATTACK:
                    if(aniIndex == 0)
                        attackChecked = false;

                    if(aniIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox, player );
                    break;
                case HIT:
                    break;

            }
        }
    }





    public int flipX(){
        if (walkDir == RIGHT)
            return  width;
        else
            return 0;
    }
    public int flipW(){
        if (walkDir == RIGHT)
            return  -1;
        else
            return 1;
    }


}
