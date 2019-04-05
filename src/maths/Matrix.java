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
	
	public static Matrix IdentityMatrix3x3() {
		Vector3D e1 = new Vector3D(1, 0, 0);
		Vector3D e2 = new Vector3D(0, 1, 0);
		Vector3D e3 = new Vector3D(0, 0, 1);
		
		return new Matrix(e1, e2, e3);
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
		return rows.length;
	}
	
	// Returns the amount of columns in the matrix
	public int getColumnCount() {
		return rows[0].dim();
	}
	
	// Returns the given row
	public Vector getRow(int row) {
		return rows[row];
	}
	
	// Returns the given column
	public Vector getColumn(int col) {
		Vector column = new Vector(getRowCount());
		
		for(int i = 0; i < getRowCount(); i++) {
			column.setCoord(i, getRow(i).getCoord(col));
		}
		
		return column;
	}
	
	// Returns aij
	public double getEntry(int i, int j) {
		return getRow(i).getCoord(j);
	}
	
	// Sets the entry aij
	public void setEntry(int i, int j, double e) {
		getRow(i).setCoord(j, e);
	}
	
	// Sets the specified row to the given vector
	public void setRow(int row, Vector vec) {
		if(vec.dim() != getColumnCount()) {
			throw new IllegalArgumentException("Attempt to change row to one with different length");
		}
		
		rows[row] = vec;
	}
	
	// Sets the specified column to the given vector
	public void setColumn(int col, Vector vec) {
		if(vec.dim() != getRowCount()) {
			throw new IllegalArgumentException("Attempt to change column to one with different length");
		}
		
		for(int i = 0; i < getRowCount(); i++) {
			getRow(i).setCoord(col, vec.getCoord(i));
		}
	}
	
	// Computes [aij + a'ij]
	public Matrix add(Matrix mat) {
		if((mat.getRowCount() != getRowCount()) || (mat.getColumnCount() != getColumnCount())){
			throw new IllegalArgumentException("Attempt to add matrices with different dimensions");
		}
		
		Vector[] newRows = new Vector[getRowCount()];
		
		for(int i = 0; i < getRowCount(); i++) {
			newRows[i] = getRow(i).add(mat.getRow(i));
		}
		
		return new Matrix(newRows);
	}
	
	// Computes [aij - a'ij]
	public Matrix sub(Matrix mat) {
		if((mat.getRowCount() != getRowCount()) || (mat.getColumnCount() != getColumnCount())){
			throw new IllegalArgumentException("Attempt to subtract matrices with different dimensions");
		}
		
		Vector[] newRows = new Vector[getRowCount()];
		
		for(int i = 0; i < getRowCount(); i++) {
			newRows[i] = getRow(i).sub(mat.getRow(i));
		}
		
		return new Matrix(newRows);
	}
	
	// Multiply all entires with a constant d
	public Matrix mul(double d) {
		Vector[] newRows = new Vector[getRowCount()];
		
		for(int i = 0; i < getRowCount(); i++) {
			Vector row = new Vector(getColumnCount());
			for(int j = 0; j < getColumnCount(); j++) {
				row.setCoord(j, getRow(i).getCoord(j) * d);
			}
			
			newRows[i] = row;
		}
		
		return new Matrix(newRows);
	}
	
	// Matrix multiplication
	public Matrix mul(Matrix mat) {
		if((getColumnCount() != mat.getRowCount())){
			throw new IllegalArgumentException("Attempt to multiply matrices with different row- and column count");
		}
		
		Vector[] newRows = new Vector[getRowCount()];
		
		for(int i = 0; i < getRowCount(); i++) {
			Vector row = new Vector(mat.getColumnCount());
			for(int j = 0; j < mat.getColumnCount(); j++) {
				row.setCoord(j, getRow(i).dot(mat.getColumn(j)));
			}
			
			newRows[i] = row;
		}
		
		return new Matrix(newRows);
	}
	
	// Returns the transpose of this matrix
	public Matrix transpose() {
		Matrix transposeMatrix = new Matrix(getColumnCount(), getRowCount());
		
		for(int r = 0; r < getRowCount(); r++) {
			transposeMatrix.setColumn(r, getRow(r));
		}
		
		return transposeMatrix;
	}
	
	// Returns the cofactor matrix of this matrix
	public Matrix cofactor() {
		Matrix cofactors = new Matrix(getRowCount(), getColumnCount());
		for(int r = 0; r < getRowCount(); r++) {
			for(int c = 0; c < getColumnCount(); c++) {
				int sign = (int)Math.pow(-1, r+c);
				cofactors.setEntry(r, c, sign*minor(r, c).det());
			}
		}
		
		return cofactors;
	}
	
	// Returns the adjugate of this matrix
	public Matrix adjugate() {
		return cofactor().transpose();
	}
	
	// Returns the minor matrix given by removing the row and columns that contain aij
	public Matrix minor(int i, int j) {
		Matrix minor = new Matrix(getRowCount() - 1, getColumnCount() - 1);
		
		for(int r = 0; r < getRowCount(); r++) {
			if(r != i) {
				Vector row = new Vector(getColumnCount() - 1);
				
				for(int c = 0; c < getColumnCount(); c++) {
					if(c != j) {
						int coord = (c > j) ? (c - 1) : c;
						
						row.setCoord(coord, getEntry(r, c));
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
		if(getRowCount() == 2 && getColumnCount() == 2) {
			return ((getRow(0).getCoord(0) * getRow(1).getCoord(1)) - (getRow(0).getCoord(1) * getRow(1).getCoord(0)));
		}
		
		double determinant = 0;
		
		for(int i = 0; i < getColumnCount(); i++) {
			Matrix minor = minor(0, i);
			double coefficient = getRow(0).getCoord(i) * minor.det();
			
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
		double det = det();
		
		// not invertible
		if(det == 0) {
			return null;
		}
		
		return adjugate().mul((1/det));
	}
	
	public String toString() {
		String strRep = "Matrix [" + getRowCount() + "x" + getColumnCount() + "]\n";
		
		for(int i = 0; i < getRowCount(); i++) {
			String rowRep = "[";
			for(int j = 0; j < (getColumnCount() - 1); j++) {
				rowRep += (getEntry(i, j) + ", ");
			}
			rowRep += getEntry(i, getColumnCount()-1);
			rowRep += "]";
			
			if(i < (getRowCount() - 1)) {
				rowRep += "\n";
			}
			
			strRep += rowRep;
		}
		
		return strRep;
	}
}
