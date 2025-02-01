package entities;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{
    private float xDelta=100, yDelta=100;

    private int frames = 0;
    private long lastCheck = 0;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction=IDLE; //WE USE WHATS FROM THE GLOBAL VARIABLE
    private int playerDir = -1;
    private boolean moving =false , attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] lvlData;
    private float xDrawOffset =10* Game.SCALE; //the placement of the hitbox on player width
    private float yDrawOffset =35* Game.SCALE;// the placement of the hitbox in player height

   //USE THIS FOR JUMPING OR FALLING! USE TIS FOR GRAVITY!
    private float airSpeed= 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;//can jump 2 squares and a quarter
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir=false;

    // UI status bar!
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    // attack box
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private Playing playing;


    public Player (float x, float y, int width, int height, Playing playing){
        super(x,y, width, height); //this is a super class, very cool!
        this.playing = playing;
        loadAnimations();
        initHitbox(x,y, (int)(20*Game.SCALE),(int) (28* Game.SCALE));
        initAttackBox();
    }

    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x= x;
        hitbox.y = y;
    }

    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(x, y, (int)( 20* Game.SCALE), (int)(20* Game.SCALE));
    }
    public void update() {

        updateHealthBar();

        if (currentHealth <= 0){
            playing.setGameOver(true);
            return;
            }

        updateAttackBox();

        updatePos();
        if(attacking)
            checkAttack();
        // int frameIndex = (int) ((System.currentTimeMillis() / 100) % idleAni.length);
        updateAnimationTick();
        setAnimation();


    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }
    private void updateAttackBox() {
        if(right)
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE *10);
         else if (left)
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE *10);

        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth =(int)((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int lvlOffset){
//        g.drawImage(animations[playerAction][aniIndex] , (int)x, (int)y,2*64, 2*40,null); //THE ORIGINAL


//        g.drawImage(animations[playerAction][aniIndex] ,
//                (int)(hitbox.x - xDrawOffset) - lvlOffset + flipX,
//                (int)(hitbox.y - yDrawOffset),(int)(40* Game.SCALE) * flipW,
//                (int)(64*Game.SCALE),null);// THE WORSE ONE

        width = (int)(40*Game.SCALE);
        height = (int)(64*Game.SCALE);
        g.drawImage(animations[playerAction][aniIndex] ,
                (int)(hitbox.x - xDrawOffset) - lvlOffset + flipX, // maybe we can add a more fluid turning animation?
                (int)(hitbox.y - yDrawOffset),width * flipW,
                height,null);// THE BETTER ONE

        // width 40, height 64
        drawHitbox(g, lvlOffset );
        drawAttackBox(g, lvlOffset);
        drawUI(g);


    }

    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.blue);
        g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
                attackChecked= false;
            }
        }
    }



    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if (attacking) {
            playerAction = ATTACK_1;
            if (startAni != ATTACK_1) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if (startAni != playerAction)
            resetAniTick();
    }


    private void resetAniTick() {
        aniTick=0;
        aniIndex = 0;
    }


    private void updatePos() {//this allows us to move in any direction

        moving = false;
        if(jump){
            jump();
        }
//        if(!left && !right && !inAir){
//            return;
//        }
        if (!inAir) {
            if ((!left && !right) || (right && left))
                return;
        }
        float xSpeed=0;

        if (left ) {
            xSpeed -= playerSpeed;
            //inAir=false;// keep in case of errors!
            flipX = width;
            flipW = -1;
        }
        if (right ) {
            xSpeed += playerSpeed;
//            if (inAir){
//                System.out.println("falling?"); // in case of errors or bugs
//            }
            flipX =0;
            flipW = 1;
        }
        if(!inAir)
            if(!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else{
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed >0){
                    resetInAir();
                }else{
                     airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }

        }else{
            updateXPos(xSpeed);
        }
        moving = true;
    }
    private void jump() {
        if(inAir){
            return;
        }
        inAir = true;
        airSpeed =jumpSpeed;

    }

    private void resetInAir() {
        inAir= false;
        airSpeed=0;
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;
        }else{
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);


        }
    }

    public void changeHealth(int value){
        currentHealth += value;
        if(currentHealth <= 0){
            currentHealth = 0;
           // gameOver();
        }else if (currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
    }


    //THE BETTER ONE
    private void loadAnimations() {


            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[10][10];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 40, j * 64, 40, 64);


            statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int [][] lvlData){
        this.lvlData=lvlData;
        if(!IsEntityOnFloor(hitbox,lvlData)){
            inAir = true;
        }
    }
    public void resetDirBooleans(){
        left=false;
        right =false;
        up=false;
        down=false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;


    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }


    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }


    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }


    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    public void setJump(boolean jump){
        this.jump = jump;
    }


    public void resetAll() {
        resetDirBooleans();
        inAir=false;
        attacking = false;
        moving=false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y=y;

        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir=true;

    }
}


