package render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

import maths.Vector3D;
import maths.Plane;
import maths.Sphere;

import render.Camera;
import render.CameraControl;
import render.Scene;
import render.Light;

public class MainWindow extends JPanel {
	
	private boolean painting = false;
	private BufferedImage canvas;
	private Camera cam;
	private RayCaster renderer;
	
	public MainWindow(int width, int height, Vector3D camPos) {
		this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.cam = new Camera(camPos, 60, 60);
		
		this.renderer = new RayCaster(this.cam, width, height);
	}
	
	public Camera getCamera() {
		return this.cam;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(canvas, null, null);
    }
	
	public void paint() {
		for(int y = 0; y < canvas.getHeight(); y++) {
			for(int x = 0; x < canvas.getWidth(); x++) {
				canvas.setRGB(x, y, (new Color(255, 255, 255)).getRGB());
			}
		}
		
		if(painting) {
			return;
		}
		painting = true;
		
		// geometry to draw
		Plane plane = new Plane(new Vector3D(1, 0, 0), new Vector3D(0,0,0));
		Sphere sphere = new Sphere(new Vector3D(0, 0, 0), 1);
		
		// light sources
		Light lightSrc = new Light(new Vector3D(-1.5, 1.5, 2), 3, 1.5);
		
		Scene renderScene = new Scene();
		renderScene.addGeoShape(plane);
		renderScene.addGeoShape(sphere);
		renderScene.addLight(lightSrc);
		
		int[][] colors = renderer.getColors(renderScene);
		
		for(int y = 0; y < canvas.getHeight(); y++) {
			for(int x = 0; x < canvas.getWidth(); x++) {
				canvas.setRGB(x, y, colors[x][y]);
			}
		}
		
		// debug info
		Graphics2D g2d = canvas.createGraphics();
		g2d.setPaint(Color.white);
		
		String camInfo = cam.getOrigin().toString() + " -> " + cam.getDir().toString();
		g2d.drawString("Camera: " + camInfo, 5, 15);
		
		String geoInfo = "origin: " + sphere.getOrigin() + ", r = " + sphere.getRadius();
		g2d.drawString("Geometry: " + geoInfo, 5, 30);
		
		g2d.dispose();
		
		painting = false;
	}

	public static void main(String[] args) {
		JFrame window = new JFrame("thing drawer");

		MainWindow drawer = new MainWindow(400, 400, new Vector3D(-10, 0, 0));
		drawer.paint();
		
		window.addKeyListener(new CameraControl(drawer.getCamera(), drawer, window));
		
		window.add(drawer);
		window.pack();
		window.setSize(400, 400);
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
