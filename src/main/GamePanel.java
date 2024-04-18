package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import  static main.Game.GAME_WIDTH;
import  static main.Game.GAME_HEIGHT;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;





    public GamePanel(Game game){

        mouseInputs = new MouseInputs(this);
        this.game =game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener( mouseInputs );
        addMouseMotionListener(mouseInputs);

    }









    private void setPanelSize() {
        // we use this to adjust the game window properly,
        // jframe.setSize(400 , 400); is odd and inefficient!
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Size: "+ GAME_WIDTH +" : "+ GAME_HEIGHT);

    }






    public void updateGame(){


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);

    }


    public Game getGame(){
        return game;
    }




}
