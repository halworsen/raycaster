package maths;

import junit.framework.TestCase;

public class VectorTest extends TestCase
{
	
	
	public void testVectorCoordinates()
	{
		Vector vector = new Vector(1, 2, 3, 4, 5);
		
		// Check that getCoord returns the correct value for every coordinate
		for(int i = 0; i < vector.dim(); i++)
		{
			assertEquals(vector.getCoord(i), (double)i+1);
		}
	}
	
	public void testSetVectorCoords()
	{
		Vector vector = new Vector(1, 2, 3, 4, 5);
		assertEquals(vector.getCoord(2), 3.0);
		
		vector.setCoord(2, 9);
		assertEquals(vector.getCoord(2), 9.0);
	}
	
	public void testVectorLength()
	{
		Vector vector = new Vector(1, 1);
		assertEquals(vector.length(), Math.sqrt(2));
		
		vector = new Vector(3, 7, 1);
		assertEquals(vector.length(), Math.sqrt(Math.pow(3, 2) + Math.pow(7, 2) + 1));
	}
	
	public void testVectorAdd()
	{
		Vector vectorA = new Vector(7, 1);
		Vector vectorB = new Vector(1, 5);
		
		// Check that the new vector has coordinates (8,6)
		assertEquals(8.0, vectorA.add(vectorB).getCoord(0));
		assertEquals(6.0, vectorA.add(vectorB).getCoord(1));
		
		// Check that its dimension is correct
		assertEquals(2, vectorA.add(vectorB).dim());
	}
	
	public void testVectorSub()
	{
		Vector vectorA = new Vector(7, 1);
		Vector vectorB = new Vector(1, 5);
		
		// Check that the new vector has coordinates (6,-4)
		assertEquals(6.0, vectorA.sub(vectorB).getCoord(0));
		assertEquals(-4.0, vectorA.sub(vectorB).getCoord(1));
		
		// Check that its dimension is correct
		assertEquals(2, vectorA.sub(vectorB).dim());
	}
	
	public void testVectorNegation()
	{
		Vector vector = new Vector(7, 1, 1, 5);
		assertTrue(vector.neg().equals(new Vector(-7, -1, -1, -5)));
	}
	
	public void testVectorDotProduct()
	{
		Vector vectorA = new Vector(1, 9, 3, 2, 8, 5);
		Vector vectorB = new Vector(6, -1, -8, 2);
		
		boolean caught = false;
		// Check if an exception is thrown when we try to calculate the dot product between vectors of different dimensions (the operation is undefined)
		try
		{
			vectorA.dot(vectorB);
		}
		catch (IllegalArgumentException e)
		{
			caught = true;
		}
		
		if (!caught)
		{
			fail("Vector didn't throw an exception for undefined dot product");
		}
		
		// Check if the dot product is correct
		vectorB = new Vector(6, -1, -8, 2, 1, 3);
		assertEquals(vectorA.dot(vectorB), 0.0);
		
		// Check if it works when the dot product is not 0
		vectorA = new Vector(1, 2, 3);
		vectorB = new Vector(1, 1, 1);
		assertEquals(vectorA.dot(vectorB), 6.0);
		
		// Check if it works when the dot product is negative
		vectorA = new Vector(1, 0, 0);
		vectorB = new Vector(-1, 0, 0);
		assertEquals(vectorA.dot(vectorB), -1.0);
	}
	
	public void testVector3DCrossProduct()
	{
		Vector3D v3dA = new Vector3D(1, 0, 0);
		Vector3D v3dB = new Vector3D(0, 1, 0);
		
		// Check if cross products work
		Vector3D cross = v3dA.cross(v3dB);
		assertTrue(cross.equals(new Vector3D(0, 0, 1)));
		
		v3dA = new Vector3D(1, 2, 0);
		v3dB = new Vector3D(0, 1, 1);
		cross = v3dA.cross(v3dB);
		assertTrue(cross.equals(new Vector3D(2, -1, 1)));
	}
}
