package maths;

public class Vector3D extends Vector {

	public Vector3D() {
		this.coordinates = new double[3];
		
		for(int i = 0; i < 3; i++) {
			this.coordinates[i] = 0.0;
		}
	}
	
	public Vector3D(double x, double y, double z) {
		this.coordinates = new double[3];
		
		this.coordinates[0] = x;
		this.coordinates[1] = y;
		this.coordinates[2] = z;
	}
	
	public Vector3D(Vector3D vec) {
		this.coordinates = new double[3];
		for(int i = 0; i < 3; i++) {
			this.setCoord(i, vec.getCoord(i));
		}
	}
	
	public Vector3D(Vector vec) {
		this.coordinates = new double[3];
		for(int i = 0; i < 3; i++) {
			this.setCoord(i, vec.getCoord(i));
		}
	}
	
	public double getX() {
		return this.coordinates[0];
	}
	
	public double getY() {
		return this.coordinates[1];
	}
	
	public double getZ() {
		return this.coordinates[2];
	}
	
	public Vector3D cross(Vector3D vec) {
		Vector3D result3d = new Vector3D();
		
		double x = (this.getY() * vec.getZ()) - (this.getZ() * vec.getY());
		double y = (this.getX() * vec.getZ()) - (this.getZ() * vec.getX());
		double z = (this.getX() * vec.getY()) - (this.getY() * vec.getX());
		
		result3d.setCoords(x, y, z);
		
		return result3d;
	}
}
