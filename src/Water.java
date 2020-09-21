public class Water {

    private int X_POS, Y_POS, DEPTH;

    public Water(int x_POS, int y_POS, int DEPTH) {
        this.X_POS = x_POS;
        this.Y_POS = y_POS;
        this.DEPTH = DEPTH;
    }
    synchronized public void clearWater(int x_pos, int y_pos){
        this.setDEPTH(0);
    }

    synchronized public int getX_POS() {
        return X_POS;
    }

    synchronized public void setX_POS(int x_POS) {
        X_POS = x_POS;
    }

    synchronized public int getY_POS() {
        return Y_POS;
    }
    synchronized public void setY_POS(int y_POS) {
        Y_POS = y_POS;
    }
    synchronized public int getDEPTH() {
        return DEPTH;
    }
    synchronized public void setDEPTH(int DEPTH) {
        this.DEPTH = DEPTH;
    }
    synchronized  public float calculateWaterSurface(Terrain terrain){
        float depth = this.getDEPTH() * 0.01f ;
        float elevation = terrain.height[this.getX_POS()][this.getY_POS()];
        return depth+elevation;
    }
    synchronized  public void increment(){
        int depth = this.getDEPTH();
        this.setDEPTH(depth + 1);
    }
    synchronized  public void decrement(){
        int depth = this.getDEPTH();
        this.setDEPTH(depth - 1);
    }
}
