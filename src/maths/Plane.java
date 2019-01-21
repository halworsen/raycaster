package maths;

public class Plane extends GeoShape {

	private Vector3D normal;
	private Vector3D refPoint;
	
	public Plane(Vector3D normal, Vector3D refPoint) {
		this.normal = normal;
		this.refPoint = refPoint;
	}
	
	public boolean contains(Vector3D point) {
		return (normal.dot(point) == 0);
	}
	
	public Vector3D getIntersection(Ray ray) {
		if(this.normal.dot(ray.getDirection()) == 0) {
			if(this.contains(ray.getOrigin())) {
				return ray.getOrigin();
			}

			return null;
		}
		
		double t = 0.0;
		
		double d = this.refPoint.dot(this.normal.neg());
		double constant = ray.getOrigin().mul(this.normal).componentSum();
		double coefficient = ray.getDirection().mul(this.normal).componentSum();
		
		t = -(d + constant) / coefficient;
		
		return ray.getParametricPoint(t);
	}
	
	public Vector3D getIntersectionPositive(Ray ray) {
		if(this.normal.dot(ray.getDirection()) == 0) {
			if(this.contains(ray.getOrigin())) {
				return ray.getOrigin();
			}
		
			return null;
		}
		
		double t = 0.0;
		
		double d = this.refPoint.dot(this.normal.neg());
		double constant = ray.getOrigin().mul(this.normal).componentSum();
		double coefficient = ray.getDirection().mul(this.normal).componentSum();
		
		t = -(d + constant) / coefficient;
		
		// the intersection happened "behind" the ray's origin
		if(t < 0) {
			return null;
		}
		
		return ray.getParametricPoint(t);
	}
}
