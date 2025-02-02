package utilz;

import main.Game;

public class Constants {
    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANI_SPEED=25;



    public static class EnemyConstants{
        public static final int CRABBY=0;


        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT= 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public  static final int CRABBY_HEIGHT_DEFAULT= 32;

        public  static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public  static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_X=(int)(26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y=(int)(9 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state){

            switch (enemy_type){
                case CRABBY:
                    switch (enemy_state){
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
            }
            return 0;
        }
        public static int GetMaxHealth(int enemy_type){
            switch (enemy_type){
                case CRABBY:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemy_type){
            switch (enemy_type) {
                case CRABBY:
                    return 15;
                default:
                    return 0;
            }
        }





        ////////////////// end of enemy constants //////////////////
    }
    public static class Environment{
        public  static final int MOUNTAIN_WIDTH_DEFAULT = 350;
        public  static final int MOUNTAIN_HEIGHT_DEFAULT = 400;

        public  static final int SMALL_CLOUD_WIDTH_DEFAULT = 100;
        public  static final int SMALL_CLOUD_HEIGHT_DEFAULT = 40;

        public  static final int MOUNTAIN_WIDTH = (int)(MOUNTAIN_WIDTH_DEFAULT * Game.SCALE);
        public  static final int MOUNTAIN_HEIGHT = (int)(MOUNTAIN_HEIGHT_DEFAULT * Game.SCALE);

        public  static final int SMALL_CLOUD_WIDTH = (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public  static final int SMALL_CLOUD_HEIGHT = (int)(SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);

    }

    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public  static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);

        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT= 42;
            public  static  final int SOUND_SIZE =(int)(SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons{
            public static  final int URM_DEFAULT_SIZE =56;
            public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH=215;

            public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);

        }

    }
    public static class Directions{
        public static final int LEFT =0;
        public static final int UP =1;
        public static final int RIGHT =2;
        public static final int DOWN =3;

    }
    public static class PlayerConstants{
        //WE USE THIS FOR DIFFERENT PLAYER MOVEMENTS, KEYBOARD CONTROLS I GUESS....
        // PUBLIC STATIC FINAL (DATA TYPE) (NAME) = (ID); IT ALSO COUNTS HOW MANY SPRITES TO USE

        public static final int IDLE = 0;
        public static final int RUNNING = 3;
        public static final int JUMP = 7;
        public static final int FALLING = 5;
        public static final int DEAD = 4;
        public static final int HIT= 9;
        public static final int ATTACK_1 = 1;
        public static final int ATTACK_JUMP_1 = 2;
        public static final int RESTING = 6;
        public static final int GetSpriteAmount (int player_action){
            switch (player_action){
                case RUNNING:
                    return 3;//RETURN NO. OF SPRITES
                case IDLE:
                    return 5;// 5 SPRITES
                case HIT:
                    return 3;// 3 SPRITES
                case JUMP, FALLING:
                    return 2;//both have 2 sprites
                case ATTACK_1:
                    return 3;
                case ATTACK_JUMP_1:
                    return 5;
                case RESTING:
                    return 10;
                case DEAD:
                    return 6;// ONLY 6 SPRITES
                default:// to prevent weird shit from happening
                    return 1;// HEH, ONLY 1 SPRITE
            }
        }



    }
}
