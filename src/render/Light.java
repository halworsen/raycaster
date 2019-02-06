package render;

import maths.Vector3D;
import maths.GeoShape;
import maths.Ray;
import render.RenderScene;

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
	
	// Performs a ray trace back to the light source to determine if the point is being occluded by something else in the given scene
	public double computeOccludedColorIntensity(RenderScene scene, GeoShape renderingShape, Vector3D point) {
		// create a ray going from the light origin and through the point
		Vector3D direction = new Vector3D(origin.sub(point));
		Ray ray = new Ray(point, direction);
		
		double intensity = computeColorIntensity(point);

		// current issue: the ray intersects with points contained inside other objects, causing the intensity to decrease even though the light never even reaches said point
		for(GeoShape shape : scene.getShapes()) {
			// Don't try to find intersections with the object we're rendering
			if(shape.equals(renderingShape)) {
				continue;
			}
			
			Vector3D intersect = shape.getIntersection(ray);
			if(intersect == null) {
				continue;
			}
			
			intensity /= 1.5;
		}
		
		return intensity;
	}
}
