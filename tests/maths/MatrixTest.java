package maths;

import junit.framework.TestCase;

public class MatrixTest extends TestCase {

	public void testMatrixEntries()
	{
		Vector vecA = new Vector(1, 2, 3);
		Vector vecB = new Vector(4, 5, 6);
		Vector vecC = new Vector(7, 8, 9);
		
		Matrix matrix = new Matrix(vecA, vecB, vecC);
		
		assertEquals(3, matrix.getRowCount());
		assertEquals(3, matrix.getColumnCount());
		
		int count = 1;
		for(int i = 0; i < matrix.getRowCount(); i++)
		{
			for(int j = 0; j < matrix.getColumnCount(); j++)
			{
				assertEquals((double)count++, matrix.getEntry(i, j));
			}
		}
		
		matrix.setEntry(2, 0, 56.0);
		assertEquals(56.0, matrix.getEntry(2, 0));
	}
	
	public void testMatrixDeterminant()
	{
		Vector vecA = new Vector(1, 2, 3);
		Vector vecB = new Vector(4, 5, 6);
		Vector vecC = new Vector(7, 8, 9);
		
		Matrix matrix = new Matrix(vecA, vecB, vecC);
		
		assertEquals(0.0, matrix.det());
		
		vecA = new Vector(1, 2, 3);
		vecB = new Vector(4, 1, 6);
		vecC = new Vector(7, 8, 1);
		
		matrix = new Matrix(vecA, vecB, vecC);
		
		assertEquals(104.0, matrix.det());
	}
	
	public void testMatrixInverse()
	{
		Vector vecA = new Vector(1, 2, 3);
		Vector vecB = new Vector(4, 5, 6);
		Vector vecC = new Vector(7, 8, 9);
		
		Matrix matrix = new Matrix(vecA, vecB, vecC);
		Matrix inverse = matrix.inverse();
		
		// Check if null is returned for non-invertible matrices
		assertNull(inverse);
		
		vecA = new Vector(1, 2, 3);
		vecB = new Vector(4, 1, 6);
		vecC = new Vector(7, 8, 1);
		
		matrix = new Matrix(vecA, vecB, vecC);
		inverse = matrix.inverse();
		
		// Multiplication from the left
		Matrix leftMul = inverse.mul(matrix);
		// Multiplication from the right
		Matrix rightMul = matrix.mul(inverse);
		
		Matrix identity = Matrix.IdentityMatrix3x3();
		
		// Check that it's an inverse from the left
		for(int i = 0; i < matrix.getRowCount(); i++)
		{
			for(int j = 0; j < matrix.getColumnCount(); j++)
			{
				double error = Math.abs(leftMul.getEntry(i,j) - identity.getEntry(i,j));
				assertTrue("inverse entry error for (" + i + ", " + j + ") too large (was " + error + ")", error < 1e-10);
			}
		}
		
		// Check that it's an inverse from the right
		for(int i = 0; i < matrix.getRowCount(); i++)
		{
			for(int j = 0; j < matrix.getColumnCount(); j++)
			{
				double error = Math.abs(rightMul.getEntry(i,j) - identity.getEntry(i,j));
				assertTrue("inverse entry error for (" + i + ", " + j + ") too large (was " + error + ")", error < 1e-10);
			}
		}
	}
	
	public void testMatrixMinor()
	{
		Vector vecA = new Vector(1, 2, 3);
		Vector vecB = new Vector(4, 5, 6);
		Vector vecC = new Vector(7, 8, 9);
		
		Matrix matrix = new Matrix(vecA, vecB, vecC);
		Matrix computedMinor = matrix.minor(0, 0);
		
		Vector minA = new Vector(5, 6);
		Vector minB = new Vector(8, 9);
		Matrix minorMatrix = new Matrix(minA, minB);
		
		
		for(int i = 0; i < minorMatrix.getRowCount(); i++)
		{
			for(int j = 0; j < minorMatrix.getColumnCount(); j++)
			{
				assertEquals(minorMatrix.getEntry(i, j), computedMinor.getEntry(i, j));
			}
		}
		
		minA = new Vector(1, 2);
		minB = new Vector(7, 8);
		minorMatrix = new Matrix(minA, minB);
		computedMinor = matrix.minor(1, 2);
		
		for(int i = 0; i < minorMatrix.getRowCount(); i++)
		{
			for(int j = 0; j < minorMatrix.getColumnCount(); j++)
			{
				assertEquals(minorMatrix.getEntry(i, j), computedMinor.getEntry(i, j));
			}
		}
		
	}
	
	public void testMatrixAdd()
	{
		Vector vecA = new Vector(1, 1, 1);
		Vector vecB = new Vector(1, 1, 1);
		Vector vecC = new Vector(1, 1, 1);
		
		Matrix matrixA = new Matrix(vecA, vecB, vecC);
		
		vecA = new Vector(1, 0, 1);
		vecB = new Vector(0, 1, 0);
		vecC = new Vector(1, 0, 1);
		
		Matrix matrixB = new Matrix(vecA, vecB, vecC);
		
		vecA = new Vector(2, 1, 2);
		vecB = new Vector(1, 2, 1);
		vecC = new Vector(2, 1, 2);
		
		Matrix correctResult = new Matrix(vecA, vecB, vecC);
		Matrix computedResult = matrixA.add(matrixB);
		
		for(int i = 0; i < correctResult.getRowCount(); i++)
		{
			for(int j = 0; j < correctResult.getColumnCount(); j++)
			{
				assertEquals(correctResult.getEntry(i, j), computedResult.getEntry(i, j));
			}
		}
	}
	
	public void testMatrixSub()
	{
		Vector vecA = new Vector(1, 1, 1);
		Vector vecB = new Vector(1, 1, 1);
		Vector vecC = new Vector(1, 1, 1);
		
		Matrix matrixA = new Matrix(vecA, vecB, vecC);
		
		vecA = new Vector(1, 0, 1);
		vecB = new Vector(0, 1, 0);
		vecC = new Vector(1, 0, 1);
		
		Matrix matrixB = new Matrix(vecA, vecB, vecC);
		
		vecA = new Vector(0, 1, 0);
		vecB = new Vector(1, 0, 1);
		vecC = new Vector(0, 1, 0);
		
		Matrix correctResult = new Matrix(vecA, vecB, vecC);
		Matrix computedResult = matrixA.sub(matrixB);
		
		for(int i = 0; i < correctResult.getRowCount(); i++)
		{
			for(int j = 0; j < correctResult.getColumnCount(); j++)
			{
				assertEquals(correctResult.getEntry(i, j), computedResult.getEntry(i, j));
			}
		}
	}
	
	public void testMatrixCofactor()
	{
		Vector vecA = new Vector(1, 2, 3);
		Vector vecB = new Vector(4, 5, 6);
		Vector vecC = new Vector(7, 8, 9);
		
		Matrix matrix = new Matrix(vecA, vecB, vecC);
		
		vecA = new Vector(-3, 6, -3);
		vecB = new Vector(6, -12, 6);
		vecC = new Vector(-3, 6, -3);
		
		Matrix correctMatrix = new Matrix(vecA, vecB, vecC);
		Matrix computedMatrix = matrix.cofactor();
		
		for(int i = 0; i < correctMatrix.getRowCount(); i++)
		{
			for(int j = 0; j < correctMatrix.getColumnCount(); j++)
			{
				assertEquals(correctMatrix.getEntry(i, j), computedMatrix.getEntry(i, j));
			}
		}
	}
	
}
