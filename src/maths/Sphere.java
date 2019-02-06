package maths;

import maths.Vector3D;
import maths.Ray;

public class Sphere extends GeoShape {
	private Vector3D origin;
	private double radius;
	
	public Sphere(Vector3D origin, double radius) {
		this.origin = origin;
		this.radius = radius;
	}
	
	public boolean contains(Vector3D point) {
		return (point.sub(origin).length() <= radius);
	}
	
	public Vector3D getOrigin() {
		return this.origin;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public Vector3D getIntersection(Ray ray) {
		double projectionLength = ray.getDirection().dot(this.origin.sub(ray.getOrigin()));
		Vector3D closestPoint = ray.getParametricPoint(projectionLength);
		
		double distToRay = closestPoint.sub(origin).length();
		// no intersection
		if(distToRay > radius) {
			return null;
		}
		
		// ray is tangent to the sphere
		if(distToRay == radius) {
			return closestPoint;
		}
		
		double toIntersect = Math.sqrt(Math.pow(radius, 2) - Math.pow(distToRay, 2));
		
		return ray.getParametricPoint(projectionLength - toIntersect);
	}
}
