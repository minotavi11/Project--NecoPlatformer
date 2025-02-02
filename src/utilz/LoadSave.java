package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    // external file importation, importation of images, storing them in a String!
    public static final String PLAYER_ATLAS = "neco-arc_sprites.png";
    public static final String LEVEL_ATLAS = "Backgrounds/outside_sprites_dusk.png";
   // public static final String LEVEL_ONE_DATA = "level_one_data.png";

   // public static final String LEVEL_ONE_DATA = "level_one_data_A.png";



    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND ="pause_menu.png";
    public static final String SOUND_BUTTONS ="sound_button.png";
    public static final String URM_BUTTONS ="urm_buttons.png";
    public static final String VOLUME_BUTTONS ="volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG ="Backgrounds/neco_background.png";
    public static final String PLAYING_BACKGROUND_IMG ="Backgrounds/Sky_Background.png";
    public static final String MOUNTAINS ="Backgrounds/Mountains_Background.png";
    public static final String SMALL_CLOUDS ="Backgrounds/Cloud_Background.png";
    public static final String CRABBY_SPRITE="crabby_sprite.png";//enemy 1
    public static final String STATUS_BAR ="health_power_bar.png";
    public static final String SLEEP_COUNT="sleep_count_cat.png ";
    public static final String COMPLETED_IMG ="completed_sprite.png";




    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img =null;
        InputStream is = LoadSave.class.getResourceAsStream("/"+ fileName);
        try {
             img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels(){
        URL url = LoadSave.class.getResource("/lvls"); //location for the resource
        File file = null;
        try {
            file = new File(url.toURI()); //identifier of a resource
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();


        File[] filesSorted = new File[files.length];
        for (int i = 0; i< filesSorted.length; i++){
            for(int j= 0; j< files.length; j++){
                if(files[j].getName().equals((i+1)+".png"))
                    filesSorted[i]=files[j];

            }
        }


        BufferedImage[] imgs = new BufferedImage[filesSorted.length];
        for(int i= 0; i< imgs.length; i++){
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgs;
    }




}
