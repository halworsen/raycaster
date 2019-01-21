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
	
	public static double[] solveQuadratic(double a, double b, double c) {
		double x0, x1;
		
		double discr = b*b - 4*a*c;
		
		if(discr < 0) {
			return null;
		}else if(discr == 0) {
			x0 = (double)(-0.5 * b/a);
			x1 = (double)(-0.5 * b/a);
		}else {
			double q = (double) ((b > 0) ? (-0.5 * (b + Math.sqrt(discr))) : (-0.5 * (b - Math.sqrt(discr))));
			x0 = q/a;
			x1 = c/q;
		}
		
		if(x0 > x1) {
			double temp = x0;
			x0 = x1;
			x1 = temp;
		}
		
		return (new double[] {x0, x1});
	}
	
	public Vector3D getIntersection(Ray ray) {
		double a = ray.getDirection().dot(ray.getDirection());
		double b = (2 * (ray.getDirection().dot(ray.getOrigin()) - ray.getDirection().dot(this.origin)));
		double c = (ray.getOrigin().dot(ray.getOrigin()) + this.origin.dot(this.origin) - 2*ray.getOrigin().dot(this.origin) - Math.pow(this.radius, 2));
		
		double solution[] = solveQuadratic(a,b,c);
		if(solution == null) {
			return null;
		}
		
		if(solution[0] < 0) {
			solution[0] = solution[1];
			if(solution[0] < 0) {
				return null;
			}
		}
		
		return ray.getParametricPoint(solution[0]);
 	}
}
