package maths;

import junit.framework.TestCase;

public class PlaneTest extends TestCase {
	
	public void testContains()
	{
		Vector3D planeOrigin = new Vector3D(0, 0, 0);
		Vector3D planeDir = new Vector3D(-1, 0, 0);
		Plane plane = new Plane(planeDir, planeOrigin);
		
		assertTrue(plane.contains(new Vector3D(0, 0, 5)));
		assertTrue(plane.contains(new Vector3D(0, 3874, 1734)));
		
		assertFalse(plane.contains(new Vector3D(-2, 0, 0)));
		assertFalse(plane.contains(new Vector3D(0.001, 1, 1)));
	}
	
	public void testPlaneIntersectRightWay()
	{
		Vector3D rayOrigin = new Vector3D(-1, 0, 1);
		Vector3D rayDir = new Vector3D(1, 0, 0);
		Ray ray = new Ray(rayOrigin, rayDir);
		
		Vector3D planeOrigin = new Vector3D(0, 0, 0);
		Vector3D planeDir = new Vector3D(-1, 0, 0);
		Plane plane = new Plane(planeDir, planeOrigin);
		
		// Check that the ray intersects the plane at the right position
		Vector3D intersect = plane.getIntersection(ray);
		assertTrue(intersect.equals(new Vector3D(0, 0, 1)));
	}
	
	public void testPlaneIntersectPositiveOnly()
	{
		Vector3D rayOrigin = new Vector3D(-1, 0, 1);
		Vector3D rayDir = new Vector3D(1, 0, 0);
		Ray ray = new Ray(rayOrigin, rayDir);
		
		Vector3D planeOrigin = new Vector3D(0, 0, 0);
		Vector3D planeDir = new Vector3D(-1, 0, 0);
		Plane plane = new Plane(planeDir, planeOrigin);
		
		// Check that, if we ask for "positive" intersections only, and then try to find the intersection between the plane and a ray pointed away from the plane, we get null
		rayDir = new Vector3D(-1, 0, 0);
		ray = new Ray(rayOrigin, rayDir);
		
		assertNull(plane.getIntersectionPositive(ray));
	}
	
}
