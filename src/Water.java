import java.awt.*;
import java.awt.image.BufferedImage;

public class Water {


    private int[][] DEPTH;

    public Water(int x_POS, int y_POS) {

        DEPTH = new int[x_POS][y_POS];
        for(int x=0; x<x_POS; x++){
            for(int y=0 ; y<y_POS; y++){
                DEPTH[x][y] = 0;
            }
        }
    }

    synchronized public void clearWater(int x_pos, int y_pos){
       this.setDEPTH(x_pos,y_pos,0);
    }

    synchronized public int getDEPTH(int x, int y) {
        return DEPTH[x][y];
    }
    synchronized public void setDEPTH(int x, int y, int new_depth) {
        this.DEPTH[x][y] = new_depth;
    }
    synchronized  public float calculateWaterSurface(int x, int y, Terrain terrain){
        float depth = this.getDEPTH(x,y) * 0.01f ;
        float elevation = terrain.height[x][y];
        return depth+elevation;
    }
    synchronized  public void increment(int x, int y){
        int depth = this.getDEPTH(x,y);
        this.setDEPTH(x,y,depth + 1);
    }
    synchronized  public void decrement(int x, int y){
        int depth = this.getDEPTH(x,y);
        this.setDEPTH(x,y,depth - 1);
    }
    synchronized  public void paint(int x, int y, Color c, BufferedImage water_img){
        water_img.setRGB(x,y,c.getRGB());
    }
}
