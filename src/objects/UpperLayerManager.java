package objects;

import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utilz.Constants.Environment.*;
/// I was too lazy to fix this code so i asked gpt to clean it for me, don't hate me, im only human ahhahahahah
public class UpperLayerManager {
    private int xLvlOffset;

    private Random rnd = new Random();

    public UpperLayerManager(Playing playing) {
        loadImgs();
    }

    /// Loads all necessary images
    private void loadImgs() {

    }

    /// Draws the foreground
    private void drawForeground(Graphics g) {

    }

    /// Draws all objects
    public void draw(Graphics g, int xLvlOffset) {
        this.xLvlOffset = xLvlOffset;
        drawForeground(g);
    }

    /// Loads upper objects when a new level is loaded
    public void loadUpperObjects(Level newLevel) {
        // Implement object loading logic here if needed
    }

    /// Updates objects
    public void update() {
        // Implement update logic here if needed
    }

    /// Resets all objects
    public void resetAllObjects() {
        // Implement reset logic here if needed
    }
}
