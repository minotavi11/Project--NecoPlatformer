package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.UrmButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtns();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int vX= (int)(309*Game.SCALE);
        int vY= (int)(278*Game.SCALE);
        volumeButton = new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);



    }

    private void createUrmButtons() {
        int menuX = (int)( 313* Game.SCALE);
        int replayX = (int)( 387 * Game.SCALE);
        int unpauseX=(int)( 462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);//we just get the menu button, number 2
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);

    }

    private void createSoundButtns() {
        int soundX = (int)(450 *Game.SCALE);
        int musicY = (int)(140 *Game.SCALE); //the values must be known from the spritesheet, they are the no. of pixels....
        int sfxY =  (int)(186 *Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton =  new SoundButton(soundX,sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int)(backgroundImg.getWidth()* Game.SCALE);
        bgH = (int)(backgroundImg.getHeight()* Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int)(25* Game.SCALE);//Game.GAME_HEIGHT / 2 - bgY /2;
    }

    public void update(){
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();

    }
    public void draw(Graphics g){
        // background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
        //sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);
        // UrmButtons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        //volume buttons
        volumeButton.draw(g);

    }
    public void mouseClicked(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {
        if(isiN(e,musicButton))
            musicButton.setMousePressed(true);
        else if (isiN(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (isiN(e, menuB))
            menuB.setMousePressed(true);
        else if (isiN(e, unpauseB))
            unpauseB.setMousePressed(true);
        else if (isiN(e, replayB))
        replayB.setMousePressed(true);
        else if (isiN(e, volumeButton))
            volumeButton.setMousePressed(true);
    }
    public void mouseReleased(MouseEvent e) {
        if(isiN(e,musicButton))
            if(musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        if (isiN(e, sfxButton)){
            if(sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        }
        if (isiN(e, menuB)){
            if(menuB.isMousePressed()){
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        }
        if (isiN(e, replayB)){
            if(replayB.isMousePressed()){
                playing.resetAll();
                playing.unpauseGame();
            }
        }
        if (isiN(e, unpauseB)){
            if(unpauseB.isMousePressed())
                playing.unpauseGame();

        }



        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBols();
        replayB.resetBols();
        unpauseB.resetBols();
        volumeButton.resetBols();
    }
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if(isiN(e,musicButton))
            musicButton.setMouseOver(true);
        else if (isiN(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (isiN(e, menuB))
            menuB.setMouseOver(true);
        else if (isiN(e, replayB))
            replayB.setMouseOver(true);
        else if (isiN(e, unpauseB))
            unpauseB.setMouseOver(true);
        else if (isiN(e, volumeButton))
            volumeButton.setMouseOver(true);


    }
    public void mouseDragged(MouseEvent e){
        if(volumeButton.isMousePressed()){
            volumeButton.changeX(e.getX());
        }

    }

    private boolean isiN(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
