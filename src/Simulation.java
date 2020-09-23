import java.awt.*;
import java.awt.image.BufferedImage;

public class Simulation extends  Thread {
    public static Terrain terrain;
    public static Water water ;
    public static int low;
    public static int high;

    /**
     * This is the constructor of the simulation thread, it takes in four parameters
     * @param terrain
     * @param water
     * @param low
     * @param high
     */
    public Simulation(Terrain terrain,Water water, int low, int high) {
        Simulation.terrain = terrain;
        Simulation.water = water;
        Simulation.low = low;
        Simulation.high = high;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
            for(int i=low; i< high;i++) {
                int[] location = new int[2];
                terrain.getPermute(i,location);

                int x_cord = location[0];
                int y_cord = location[1];

                int from_x = Math.max(0, x_cord-1);
                int to_x = Math.min(x_cord+1, terrain.dimx-1);
                int from_y = Math.max(0, y_cord-1);
                int to_y = Math.min(y_cord+1, terrain.dimy-1);

                //Find if there is water in this location
                BufferedImage water_img = terrain.getWaterImage();
                int color = water_img.getRGB(x_cord,y_cord);
                if(color == Color.blue.getRGB()){

//                    if(water.getDEPTH(x_cord,y_cord) <= 0){
//                        return ;
//                    }

                    float surface = water.calculateWaterSurface(x_cord,y_cord,terrain);
                    float small_surface = surface;
                    int x_=0, y_=0, d=0;
                    for(int r= from_x; r<to_x ;r++){
                        for(int c= from_y; c<to_y ;c++){
                            float surface2 = water.calculateWaterSurface(r,c,terrain);
                            int ck = Float.compare(surface2, small_surface);
                            if(ck < 0){
                                small_surface = surface2 ;
                                x_ = r ;
                                y_= c;
                                d = water.getDEPTH(r,c);
                            }

                        }
                    }
                    int check = Float.compare(surface, small_surface);
                    if(check > 0) {
                        water.increment(x_,y_);
                        if(water.getDEPTH(x_cord,y_cord)-1 > 0){
                          water.decrement(x_cord,y_cord);
                        }else if(water.getDEPTH(x_cord,y_cord)-1 <= 0){
                            water.decrement(x_cord,y_cord);
                            Color c = new Color(0,0,0,0);
                            water_img.setRGB(x_cord,y_cord, c.getRGB());
                        }
                        water_img.setRGB(x_,y_, Color.blue.getRGB());
                    }
                }
            }

    }

}
