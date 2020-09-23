public class Water {

    private int[][] DEPTH;
    /**
     * Water constructor, when called populated the water data in tha landscape
     * to Zero values before the start of the simulation
     * @param x_POS
     * @param y_POS
     */
    public Water(int x_POS, int y_POS) {

        DEPTH = new int[x_POS][y_POS];
        for(int x=0; x<x_POS; x++){
            for(int y=0 ; y<y_POS; y++){
                DEPTH[x][y] = 0;
            }
        }
    }

    /**
     * Clears water in the indicated cordinate positions
     * @param x_pos
     * @param y_pos
     */
    synchronized public void clearWater(int x_pos, int y_pos){
       this.setDEPTH(x_pos,y_pos,0);
    }

    /**
     *
     * @param x
     * @param y
     * @return the depth of water at the x and y cordinates on the grid
     */
    synchronized public int getDEPTH(int x, int y) {
        return DEPTH[x][y];
    }

    /**
     * Sets the depth of water at the given cordinates
     * @param x
     * @param y
     * @param new_depth
     */
    synchronized public void setDEPTH(int x, int y, int new_depth) {
        this.DEPTH[x][y] = new_depth;
    }

    /**
     * Calculates the water surface
     * @param x
     * @param y
     * @param terrain
     * @return the calculated surface value at the given gridpoint
     */
    synchronized  public float calculateWaterSurface(int x, int y, Terrain terrain){
        float depth = this.getDEPTH(x,y) * 0.01f ;
        float elevation = terrain.height[x][y];
        return depth+elevation;
    }

    /**
     * Increments the water depth given grid positions
     * @param x
     * @param y
     */
    synchronized  public void increment(int x, int y){
        int depth = this.getDEPTH(x,y);
        this.setDEPTH(x,y,depth + 1);
    }

    /**
     * Decrements the water depth given the grid positions and checks the
     * value is always set to zero incase it goes negative
     * @param x
     * @param y
     */
    synchronized  public void decrement(int x, int y){
        int depth = this.getDEPTH(x,y);
        if(depth-1 <= 0)
            this.setDEPTH(x,y,0);
        this.setDEPTH(x,y,depth - 1);
    }
}
