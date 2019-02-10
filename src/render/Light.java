package render;

import maths.Vector3D;

public class Light {

	private Vector3D origin;
	// TODO directional lighting
	private Vector3D direction;
	
	private double radius;
	private double intensity;
	
	public Light(Vector3D origin, double radius, double intensity) {
		this.origin = origin;
		this.radius = radius;
		this.intensity = intensity;
	}
	
	public void setOrigin(Vector3D vec) {
		this.origin = vec;
	}
	
	public void setRadius(double radius) {
		if(radius == 0) {
			throw new IllegalArgumentException("Light radius cannot be 0");
		}
		
		this.radius = radius;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	public double computeColorIntensity(Vector3D point) {
		if(point == null) {
			return 0;
		}
		
		double distance = this.origin.sub(point).length();
		return Math.min((1 - (Math.min(distance, this.radius) / this.radius)) * this.intensity, 1);
	}
}
