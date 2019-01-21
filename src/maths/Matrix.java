package maths;

public class Matrix {
	Vector[] rows;

	public Matrix(Vector... rows) {
		this.rows = new Vector[rows.length];
		
		int dim = rows[0].dim();
		
		for(int i = 0; i < rows.length; i++) {
			if(rows[i].dim() != dim) {
				throw new IllegalArgumentException("Attempt to initialize matrix with vectors of different dimensions");
			}
			
			this.rows[i] = rows[i];
		}
	}
	
	public Matrix(int rows, int columns) {
		this.rows = new Vector[rows];
		
		for(int i = 0; i < rows; i++) {
			this.rows[i] = new Vector(columns);
		}
	}
	
	public static Matrix RotationMatrix3DX(double theta) {
		Vector3D vecA = new Vector3D(1, 0, 0);
		Vector3D vecB = new Vector3D(0, Math.cos(theta), -Math.sin(theta));
		Vector3D vecC = new Vector3D(0, Math.sin(theta), Math.cos(theta));
		
		return new Matrix(vecA, vecB, vecC);
	}
	
	public static Matrix RotationMatrix3DY(double theta) {
		Vector3D vecA = new Vector3D(Math.cos(theta), 0, -Math.sin(theta));
		Vector3D vecB = new Vector3D(0, 1, 0);
		Vector3D vecC = new Vector3D(Math.sin(theta), 0, Math.cos(theta));
	
		return new Matrix(vecA, vecB, vecC);
	}
	
	public static Matrix RotationMatrix3DZ(double theta) {
		Vector3D vecA = new Vector3D(Math.cos(theta), -Math.sin(theta), 0);
		Vector3D vecB = new Vector3D(Math.sin(theta), Math.cos(theta), 0);
		Vector3D vecC = new Vector3D(0, 0, 1);
	
		return new Matrix(vecA, vecB, vecC);
	}
	
	// Returns the amount of rows in the matrix
	public int getRowCount() {
		return this.rows.length;
	}
	
	// Returns the amount of columns in the matrix
	public int getColumnCount() {
		return this.rows[0].dim();
	}
	
	// Returns the given row
	public Vector getRow(int row) {
		return this.rows[row];
	}
	
	// Returns the given column
	public Vector getColumn(int col) {
		Vector column = new Vector(this.getRowCount());
		
		for(int i = 0; i < this.getRowCount(); i++) {
			column.setCoord(i, this.getRow(i).getCoord(col));
		}
		
		return column;
	}
	
	// Returns aij
	public double getEntry(int i, int j) {
		return this.getRow(i).getCoord(j);
	}
	
	// Sets the entry aij
	public void setEntry(int i, int j, double e) {
		this.getRow(i).setCoord(j, e);
	}
	
	// Sets the specified row to the given vector
	public void setRow(int row, Vector vec) {
		if(vec.dim() != this.getColumnCount()) {
			throw new IllegalArgumentException("Attempt to change row to one with different length");
		}
		
		this.rows[row] = vec;
	}
	
	// Sets the specified column to the given vector
	public void setColumn(int col, Vector vec) {
		if(vec.dim() != this.getRowCount()) {
			throw new IllegalArgumentException("Attempt to change column to one with different length");
		}
		
		for(int i = 0; i < this.getRowCount(); i++) {
			this.getRow(i).setCoord(col, vec.getCoord(i));
		}
	}
	
	// Computes [aij + a'ij]
	public Matrix add(Matrix mat) {
		if((mat.getRowCount() != this.getRowCount()) || (mat.getColumnCount() != this.getColumnCount())){
			throw new IllegalArgumentException("Attempt to add matrices with different dimensions");
		}
		
		Vector[] newRows = new Vector[this.getRowCount()];
		
		for(int i = 0; i < this.getRowCount(); i++) {
			newRows[i] = this.getRow(i).add(mat.getRow(i));
		}
		
		return new Matrix(newRows);
	}
	
	// Computes [aij - a'ij]
	public Matrix sub(Matrix mat) {
		if((mat.getRowCount() != this.getRowCount()) || (mat.getColumnCount() != this.getColumnCount())){
			throw new IllegalArgumentException("Attempt to subtract matrices with different dimensions");
		}
		
		Vector[] newRows = new Vector[this.getRowCount()];
		
		for(int i = 0; i < this.getRowCount(); i++) {
			newRows[i] = this.getRow(i).sub(mat.getRow(i));
		}
		
		return new Matrix(newRows);
	}
	
	// Multiply all entires with a constant d
	public Matrix mul(double d) {
		Vector[] newRows = new Vector[this.getRowCount()];
		
		for(int i = 0; i < this.getRowCount(); i++) {
			Vector row = new Vector(this.getColumnCount());
			for(int j = 0; j < this.getColumnCount(); j++) {
				row.setCoord(j, this.getRow(i).getCoord(j) * d);
			}
			
			newRows[i] = row;
		}
		
		return new Matrix(newRows);
	}
	
	// Matrix multiplication
	public Matrix mul(Matrix mat) {
		if((this.getColumnCount() != mat.getRowCount())){
			throw new IllegalArgumentException("Attempt to multiply matrices with different row- and column count");
		}
		
		Vector[] newRows = new Vector[this.getRowCount()];
		
		for(int i = 0; i < this.getRowCount(); i++) {
			Vector row = new Vector(mat.getColumnCount());
			for(int j = 0; j < mat.getColumnCount(); j++) {
				row.setCoord(j, this.getRow(i).dot(mat.getColumn(j)));
			}
			
			newRows[i] = row;
		}
		
		return new Matrix(newRows);
	}
	
	// Returns the transpose of this matrix
	public Matrix transpose() {
		Matrix transposeMatrix = new Matrix(this.getColumnCount(), this.getRowCount());
		
		for(int r = 0; r < this.getRowCount(); r++) {
			transposeMatrix.setColumn(r, this.getRow(r));
		}
		
		return transposeMatrix;
	}
	
	// Returns the cofactor matrix of this matrix
	public Matrix cofactor() {
		Matrix cofactors = new Matrix(this.getRowCount(), this.getColumnCount());
		for(int r = 0; r < this.getRowCount(); r++) {
			for(int c = 0; c < this.getColumnCount(); c++) {
				int sign = (int)Math.pow(-1, r+c);
				cofactors.setEntry(r, c, sign*this.minor(r, c).det());
			}
		}
		
		return cofactors;
	}
	
	// Returns the adjugate of this matrix
	public Matrix adjugate() {
		return this.cofactor().transpose();
	}
	
	// Returns the minor matrix given by removing the row and columns that contain aij
	public Matrix minor(int i, int j) {
		Matrix minor = new Matrix(this.getRowCount() - 1, this.getColumnCount() - 1);
		
		for(int r = 0; r < this.getRowCount(); r++) {
			if(r != i) {
				Vector row = new Vector(this.getColumnCount() - 1);
				
				for(int c = 0; c < this.getColumnCount(); c++) {
					if(c != j) {
						int coord = (c > j) ? (c - 1) : c;
						
						row.setCoord(coord, this.getEntry(r, c));
					}
				}
				
				int rowNum = (r > i) ? (r - 1) : r;
				minor.setRow(rowNum, row);
			}
		}
		
		return minor;
	}
	
	// Computes the determinant of this matrix
	public double det() {
		if(this.getRowCount() == 2 && this.getColumnCount() == 2) {
			return ((this.getRow(0).getCoord(0) * this.getRow(1).getCoord(1)) - (this.getRow(0).getCoord(1) * this.getRow(1).getCoord(0)));
		}
		
		double determinant = 0;
		
		for(int i = 0; i < this.getColumnCount(); i++) {
			Matrix minor = this.minor(0, i);
			double coefficient = this.getRow(0).getCoord(i) * minor.det();
			
			if(i % 2 == 0) {
				determinant += coefficient;
			}else {
				determinant -= coefficient;
			}
		}
		
		return determinant;
	}
	
	// Computes the inverse of this matrix by A*/|A|
	public Matrix inverse() {
		double det = this.det();
		
		// not invertible
		if(det == 0) {
			return null;
		}
		
		return this.adjugate().mul((1/det));
	}
	
	public String toString() {
		String strRep = "Matrix [" + this.getRowCount() + "x" + this.getColumnCount() + "]\n";
		
		for(int i = 0; i < this.getRowCount(); i++) {
			String rowRep = "[";
			for(int j = 0; j < (this.getColumnCount() - 1); j++) {
				rowRep += (this.getEntry(i, j) + ", ");
			}
			rowRep += this.getEntry(i, this.getColumnCount()-1);
			rowRep += "]";
			
			if(i < (this.getRowCount() - 1)) {
				rowRep += "\n";
			}
			
			strRep += rowRep;
		}
		
		return strRep;
	}
}
