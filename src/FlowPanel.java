import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JPanel;

public class FlowPanel extends JPanel implements Runnable {
	static Terrain land;
	public static Water water ;
	public static AtomicInteger generations;
	public static AtomicBoolean reset;
	public static AtomicBoolean paused;
	public static AtomicBoolean end;
	Simulation simulation,simulation2,simulation3,simulation4;

	/**
	 * FlowPanel constructor
	 * @param terrain
	 */
	FlowPanel(Terrain terrain) {
		paused = new AtomicBoolean(true);
		end = new AtomicBoolean(false);
		reset = new AtomicBoolean(false);
		generations = new AtomicInteger(0);
		land = terrain;
		water = new Water(land.dimx, land.dimy);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				int from_r = Math.max(0, x-4);
				int to_r = Math.min(x+4, land.dimy-1);
				int from_c = Math.max(0, y-4);
				int to_c = Math.min(y+4, land.dimx-1);
				BufferedImage img = land.getWaterImage();
				for(int i=from_r; i<to_r ;i++){
					for(int j=from_c ; j<to_c; j++){
						img.setRGB(i,j, Color.blue.getRGB());
						water.setDEPTH(i,j,3);
					}
				}
				repaint();
				System.out.println("Water added");
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

	/**
	 * Controls the GUI water show depending on the waterdata form the Water model
	 * @param x
	 * @param y
	 */
	public static void paint(int x, int y){
		BufferedImage img = land.getWaterImage();
		int depth = water.getDEPTH(x,y);
		if(depth > 0){
			img.setRGB(x, y, Color.blue.getRGB());
		}
		else{
			Color color = new Color(0, 0, 0, 0);
			img.setRGB(x, y, color.getRGB());
		}
	}

	/**
	 * draw the landscape in greyscale as an image
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
		}
		if(land.getWaterImage() != null){
			g.drawImage(land.getWaterImage(), 0, 0, null);
		}
	}

	/**
	 * Resets the landscape water image to transparent and clears all the water
	 */
	public static void reset() {
		reset.set(true);
		paused.set(true);
		generations.set(0);
		BufferedImage img = land.getWaterImage();
		int dimx = land.dimx;
		int dimy = land.dimy;
		for(int x=0; x < dimx; x++) {
			for (int y = 0; y < dimy; y++) {
				Color color = new Color(0, 0, 0, 0);
				img.setRGB(x, y, color.getRGB());
				water.clearWater(x,y);
			}
		}

	}

	/**
	 * pauses the simulation
	 */
	public static void pause() {
		paused.set(true);
		System.out.println("Status: " + paused);
	}

	/**
	 * starts the simulation
	 */
	public static void play() {
		paused.set(false);
		reset.set(false);
		System.out.println("Status: " + paused);
	}

	/**
	 * ends the simulation and exits the program
	 */
	public static void endSimulation() {
		paused.set(true);
		System.exit(0);
	}

	public void run() {
		// display loop here
		// to do: this should be controlled by the GUI
		// to allow stopping and starting
		int dim = land.dim();
		int low_1 = 0, low_2 = dim / 4, low_3 = dim / 2, low_4 = dim / 2 + dim / 4;
		int high_1 = dim / 4, high_2 = dim / 2, high_3 = dim / 2 + dim / 4, high_4 = dim;

		while(!end.get()){
			if(!paused.get()){
				//Clear water at the boundaries
				water.clearWater(0,0);
				water.clearWater(land.dimx-1,land.dimy-1);
				//create my threads here
				simulation = new Simulation(land, water, low_1, high_1);
				simulation2 = new Simulation(land, water, low_2, high_2);
				simulation3 = new Simulation(land, water, low_3, high_3);
				simulation4 = new Simulation(land, water, low_4, high_4);

				Simulation[] t = new Simulation[4];
				t[0] = simulation;
				t[1] = simulation2;
				t[2] = simulation3;
				t[3] = simulation4;
				for (Simulation thread : t) {
					thread.start();
				}
//				for (Simulation thread : t) {
//					try {
//						thread.join();
//					}catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				generations.getAndIncrement();
				Flow.incrementGenerations(generations);
				repaint();
			}
			repaint();
		}
	}
}