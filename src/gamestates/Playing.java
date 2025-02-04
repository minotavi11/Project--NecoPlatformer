package gamestates;

import entities.EnemyManager;
import entities.Entity;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utilz.Constants.Environment.*;


public class Playing extends State implements  Statemethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private Entity entity;
    private PauseOverlay pauseOverlay ;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false;
    private int xLvlOffset;
    private int yLvlOffset;
    private int leftBorder = (int)(0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.5 * Game.GAME_WIDTH);
//    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
//    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH
    private int maxLvlOffsetX;
    private BufferedImage backgroundImg, mountain, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();
    private boolean gameOver;
    private boolean lvlCompleted =false;
    protected boolean canMove = true;
    private boolean playerCanHeal = true;


    public Playing(Game game) {
        super(game);
        innitClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
        mountain = LoadSave.GetSpriteAtlas(LoadSave.MOUNTAINS);
        smallCloud= LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos= new int[150];// render lenght for the clouds texture sheet/ aka it repeats 100 times
        for (int i = 0; i <smallCloudsPos.length; i++){
            smallCloudsPos[i] = (int)(85*Game.SCALE) + rnd.nextInt((int)(105* Game.SCALE));// something between 90 and 150
        }

        calcLvlOffset();
        loadStartLevel();
    }
    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {

        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void innitClasses() {
        levelManager= new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);

        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE),this);//spawn point(on screen)
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay =new PauseOverlay(this);
        gameOverOverlay=new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);

    }

    public void unpauseGame(){
        paused = false;
    }

    public void windowFocusLost(){
        player.resetDirBooleans();
    }


    public Player getPlayer(){
        return player;
    }

    public EnemyManager getEnemyManager(){
        return enemyManager;
    }
    public ObjectManager getObjectManager(){
        return objectManager;
    }




    @Override
    public void update() {
        if(paused ){
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        }else if(!gameOver){
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }

    }

    private void checkCloseToBorder() {
        int playerX = (int)(player.getHitbox().x);
        int diff = playerX - xLvlOffset;

        if(diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if(diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset=maxLvlOffsetX;
        else if (xLvlOffset <0)
            xLvlOffset = 0;
    }



    //DRAW IMAGES ON THE SCREEN, THE UI AND OTHER STUFF!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    public void draw(Graphics g) {

        //THE ORDER IN WHICH YOU CALL THE METHODS CORRESPONDS WITH THE LAYERS OF THE GAME'S GRAPHICS

        g.drawImage(backgroundImg, 0,0,2* Game.GAME_WIDTH, 2*Game.GAME_HEIGHT, null);
        drawBackground(g);
        levelManager.draw(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);


// use me when you want to change specific levels! I can be a switch as well!!!
//        if (levelManager.getLvlIndex() == 1) {
//            drawBackground(g);
//        }

        player.render(g, xLvlOffset);
        // ///////////////////////////////////////// //
        if(paused) {
            g.setColor(new Color(0,0,0,100));//darken the background when paused
            g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);// fills the darkening to the screen size
            pauseOverlay.draw(g);
        }else if(gameOver) {
            gameOverOverlay.draw(g);
        }
        else if(lvlCompleted) {
            levelCompletedOverlay.draw(g);
        }
    }

    private void drawBackground(Graphics g) {
        for (int i = 0 ; i <100; i++)// MOUNTAIN render length
            g.drawImage(mountain, 0 + i * MOUNTAIN_WIDTH -(int)(xLvlOffset*0.3),(int)(204*Game.SCALE), MOUNTAIN_WIDTH, MOUNTAIN_HEIGHT,null);
        for(int i = 0; i < smallCloudsPos.length; i ++) {
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH*i   -(int)(xLvlOffset*0.45), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }
        // -(int)(xLvlOffset*0.3) in our code above, gives illusion of depth in out background sky!


    }
    public void resetAll(){

        gameOver = false;
        paused=false;
        lvlCompleted=false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();

    }

    public void setLevelCompleted(boolean levelCompleted){
        this.lvlCompleted= levelCompleted;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkPotionTouched(Rectangle2D.Float hitbox){
        objectManager.checkObjectTouched(hitbox);
    }
    public void checkObjectHit(Rectangle2D.Float attackBox){
        objectManager.checkObjectHit(attackBox);
    }

    public void setMaxLvlOffset(int lvlOffset){
        this.maxLvlOffsetX = lvlOffset;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver)
            if(e.getButton()==MouseEvent.BUTTON1)
            player.setAttacking(true);

    }

    public void mouseDragged(MouseEvent e){
        if(!gameOver) {
            if (paused)
                pauseOverlay.mouseDragged(e);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverOverlay.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (canMove) player.setUp(true);
                    break;
                case KeyEvent.VK_A:
                    if (canMove) player.setLeft(true);
                    break;
                case KeyEvent.VK_S:
                    if (canMove) player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                    if (canMove) player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    if (canMove) player.setJump(true);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    Gamestate.state = Gamestate.MENU;
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
                case KeyEvent.VK_SHIFT:
                    if (playerCanHeal && !isMoving()) { // Only trigger resting if no movement keys are pressed
                        player.setResting(true);
                        canMove = false; // Prevent movement while resting
                        playerCanHeal = false; // Prevent further healing until SHIFT is released
                    }
                    break;
            }
        }
    }

    private boolean isMoving() {
        return player.isUp() || player.isLeft() || player.isDown() || player.isRight();
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) { // Prevent movement if game is over
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }

        // Allow movement and reset healing flag when SHIFT is released
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            player.setResting(false);
            canMove = true; // Allow movement again after resting
            playerCanHeal = true; // Re-enable healing when SHIFT is released
        }
    }






}
