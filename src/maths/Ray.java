package maths;

public class Ray {

	private Vector3D point;
	private Vector3D direction;
	
	public Ray(Vector3D point, Vector3D direction) {
		this.point = point;
		this.direction = direction;
		this.direction.normalize();
	}
	
	public Vector3D getOrigin() {
		return this.point;
	}
	
	public Vector3D getDirection() {
		return this.direction;
	}
	
	public Vector3D getParametricPoint(double t) {
		double x = this.point.getX() + (this.direction.getX() * t);
		double y = this.point.getY() + (this.direction.getY() * t);
		double z = this.point.getZ() + (this.direction.getZ() * t);
		
		return new Vector3D(x, y, z);
	}
	
	public String toString() {
		return "Ray[" + this.point + " -> " + this.direction + "]";
	}
}
