package maths;

import maths.Vector3D;
import maths.Ray;

public class Sphere extends GeoShape {
	private Vector3D origin;
	private double radius;
	
	public Sphere(Vector3D origin, double radius) {
		super();
		this.origin = origin;
		this.radius = radius;
	}
	
	public Vector3D getOrigin() {
		return this.origin;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public Vector3D getIntersection(Ray ray) {
		Vector3D toRayOrigin = new Vector3D(this.origin.sub(ray.getOrigin()));
		double projectionLength = ray.getDirection().dot(toRayOrigin);
		
		if(projectionLength < 0) {
			return null;
		}
		
		double distToRaySq = toRayOrigin.dot(toRayOrigin) - Math.pow(projectionLength, 2);
		
		// no intersection
		if(distToRaySq < 0 || distToRaySq > radius*radius) {
			return null;
		}
		
		double toIntersect = Math.sqrt(Math.pow(radius, 2) - distToRaySq);
		
		return ray.getParametricPoint(projectionLength - toIntersect);
 	}
}
