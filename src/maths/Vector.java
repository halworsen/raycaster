package maths;

public class Vector {

	protected double[] coordinates;
	
	public Vector(int dim) {
		this.coordinates = new double[dim];
		for(int i = 0; i < dim; i++) {
			this.coordinates[i] = 0.0;
		}
	}
	
	public Vector(double... coordinates) {
		this.coordinates = new double[coordinates.length];
		this.coordinates = coordinates;
	}
	
	public Vector(Vector vec) {
		this.coordinates = new double[vec.dim()];
		for(int i = 0; i < vec.dim(); i++) {
			this.setCoord(i, vec.getCoord(i));
		}
	}
	
	public static Vector Vector2D(double x, double y) {
		Vector v2d = new Vector(2);
		
		v2d.coordinates[0] = x;
		v2d.coordinates[1] = y;
		
		return v2d;
	}
	
	public double getCoord(int coord) {
		return this.coordinates[coord];
	}
	
	public void setCoord(int i, double newCoord) {
		this.coordinates[i] = newCoord;
	}

	public void setCoords(double... newCoordinates) {
		if(newCoordinates.length != dim()) {
			throw new IllegalArgumentException("Attempt to set " + newCoordinates.length + " coordinates on a " + dim() + "D vector");
		}
		
		for(int i = 0; i < dim(); i++) {
			this.setCoord(i, newCoordinates[i]);;
		}
	}
	
	public int dim() {
		return this.coordinates.length;
	}

	public double length() {
		double sqSum = 0;
		for(int i = 0; i < this.dim(); i++) {
			sqSum += Math.pow(this.getCoord(i), 2);
		}
		
		return Math.sqrt(sqSum);
	}
	
	public void normalize() {
		for(int i = 0; i < this.dim(); i++) {
			this.coordinates[i] /= this.length();
		}
	}
	
	public Vector neg() {
		Vector result = new Vector(this);
		
		for(int i = 0; i < this.dim(); i++) {
			result.coordinates[i] = -result.getCoord(i);
		}
		
		return result;
	}
	
	public Vector add(Vector vec) {
		Vector result = new Vector(this);
		
		for(int i = 0; i < this.dim(); i++) {
			result.coordinates[i] += vec.getCoord(i);
		}
		
		return result;
	}
	
	public Vector sub(Vector vec) {
		Vector result = new Vector(this);
		
		for(int i = 0; i < this.dim(); i++) {
			result.coordinates[i] -= vec.getCoord(i);
		}
		
		return result;
	}
	
	public Vector div(Vector vec) {
		Vector result = new Vector(this);
		
		for(int i = 0; i < this.dim(); i++) {
			result.coordinates[i] /= vec.getCoord(i);
		}
		
		return result;
	}
	
	public Vector mul(Vector vec) {
		Vector result = new Vector(this);
		
		for(int i = 0; i < this.dim(); i++) {
			result.coordinates[i] *= vec.getCoord(i);
		}
		
		return result;
	}
	
	public Vector mul(Matrix mat) {
		if(this.dim() != mat.getRowCount()) {
			throw new IllegalArgumentException("Dimension-column length mismatch between vector and matrix (" + this.dim() + ", " + mat.getColumnCount() + ")");
		}
		
		Vector result = new Vector(this.dim());
		
		for(int i = 0; i < this.dim(); i++) {
			Vector col = mat.getColumn(i);
			result.setCoord(i, this.dot(col));
		}
		
		return result;
	}
	
	public boolean equals(Vector vec) {
		if(this.dim() != vec.dim()) {
			return false;
		}
		
		for(int i = 0; i < this.dim(); i++) {
			if(this.getCoord(i) != vec.getCoord(i)) {
				return false;
			}
		}
		
		return true;
	}
	
	public double componentSum() {
		double sum = 0.0;
		
		for(int i = 0; i < this.dim(); i++) {
			sum += this.getCoord(i);
		}
		
		return sum;
	}

	public double dot(Vector vec) {
		double result = 0;
		
		for(int i = 0; i < this.dim(); i++) {
			result += (this.getCoord(i) * vec.getCoord(i));
		}
		
		return result;
	}
	
	public String toString() {
		String strRep = "Vector" + this.dim() + "D[(";
		for(int i = 0; i < (this.dim() - 1); i++) {
			strRep += (this.getCoord(i) + ", ");
		}
		
		strRep += this.coordinates[this.dim() - 1];
		strRep += ")]";
		
		return strRep;
	}
	
}
