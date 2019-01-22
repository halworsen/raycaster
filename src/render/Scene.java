package render;

import maths.GeoShape;
import render.Light;

import java.util.ArrayList;

public class Scene {

	private ArrayList<Light> lights;
	private ArrayList<GeoShape> shapes;
	
	public Scene() {
		this.lights = new ArrayList<Light>();
		this.shapes = new ArrayList<GeoShape>();
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public void addGeoShape(GeoShape shape) {
		shapes.add(shape);
	}
	
	public GeoShape[] getShapes() {
		GeoShape[] shapes = new GeoShape[this.shapes.size()];
		shapes = this.shapes.toArray(shapes);
		
		return shapes;
	}
	
	public Light[] getLights() {
		Light[] lights = new Light[this.lights.size()];
		lights = this.lights.toArray(lights);
				
		return lights;
	}
	
	public int getShapeCount() {
		return this.shapes.size();
	}
	
	public int getLightCount() {
		return this.lights.size();
	}
	
}
