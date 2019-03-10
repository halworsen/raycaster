package render;

import maths.GeoShape;
import render.Light;

import java.util.ArrayList;

public class RenderScene {

	private ArrayList<Light> lights;
	private ArrayList<GeoShape> shapes;
	
	public RenderScene() {
		this.lights = new ArrayList<Light>();
		this.shapes = new ArrayList<GeoShape>();
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public void addGeoShape(GeoShape shape) {
		shapes.add(shape);
	}
	
	public ArrayList<GeoShape> getShapes() {
		return shapes;
	}
	
	public ArrayList<Light> getLights() {
		return lights;
	}
	
	public int getShapeCount() {
		return this.shapes.size();
	}
	
	public int getLightCount() {
		return this.lights.size();
	}
	
	public void clear() {
		this.lights.clear();
		this.shapes.clear();
	}
	
}
