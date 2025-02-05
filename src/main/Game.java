package main;

import gamestates.Gamestate;
import gamestates.Playing;
import gamestates.Menu;
import utilz.LoadSave;

import java.awt.*;

// never use repaint only for game loop because of threading issues!
public class Game  implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private  final int UPS_SET =200;
    private Playing playing;
    private Menu menu;
    public final static int TILES_DEFAULT_SIZE=32;
    public final static float SCALE=2f; //scale of the game map!
    public final static int TILES_IN_WIDTH=26;//visible tiles
    public final static int TILES_IN_HEIGHT=14;//visible tiles
    public final static int TILES_SIZE =(int)(TILES_DEFAULT_SIZE *SCALE);
    public final static int GAME_WIDTH= TILES_IN_WIDTH*TILES_SIZE;
    public final static int GAME_HEIGHT =TILES_IN_HEIGHT*TILES_SIZE;

    public Game(){
//        LoadSave.GetAllLevels();
        innitClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    }

    private void innitClasses() {
        menu = new Menu(this);
        playing = new Playing(this);

    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        switch (Gamestate.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void render(Graphics g){

        switch (Gamestate.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }


    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0d / FPS_SET;
        double timePerUpdate = 1_000_000_000.0d / UPS_SET;


        long previousTime = System.nanoTime();

        int frames = 0;
        int updates=0;
        long lastCheck = System.currentTimeMillis();


        double deltaU=0;
        double deltaF=0;

        while(true){

            long currentTime = System.nanoTime();

            deltaU +=(currentTime- previousTime) / timePerUpdate;
            deltaF +=(currentTime- previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >=1){
                update();
                updates++;
                deltaU--;
            }

            if(deltaF>=1){
                gamePanel.repaint();
               frames++;
               deltaF--;
            }
//

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: "+ updates);
                frames = 0;
                updates=0;
            }
        }
    }



    public void windowFocusLost(){
        if(Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }
}


