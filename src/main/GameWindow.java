package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow  {
    private JFrame jframe;
    public GameWindow( GamePanel gamePanel){
        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// when I click the X, to terminate the program...
        jframe.add(gamePanel);
        jframe.setResizable(false);//we dont wanna resize the game window
        jframe.pack();// fit the size of window to the preferred size we wrote
        jframe.setLocationRelativeTo(null);// THIS CODE MUST STAY HERE, OR ELSE THE WINDOW IS NOT CENTERED, IDK WHY
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });

    }
}
