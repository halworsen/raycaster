package maths;

import junit.framework.TestCase;

public class SphereTest extends TestCase {
	
	public void testIntersect()
	{
		Vector3D rayOrigin = new Vector3D(-7, 0, 0);
		Vector3D rayDir = new Vector3D(1, 0, 0);
		Ray ray = new Ray(rayOrigin, rayDir);
		
		Vector3D sphereOrigin = new Vector3D(0, 0, 0);
		double radius = 5;
		Sphere sphere = new Sphere(sphereOrigin, radius);
		
		Vector3D intersect = sphere.getIntersection(ray);
		assertTrue("expected (-5, 0, 0) but was " + intersect.toString(), intersect.equals(new Vector3D(-5, 0, 0)));
		
		rayOrigin = new Vector3D(-6, 1, 1);
		rayDir = new Vector3D(2, 0.2, 1);
		ray = new Ray(rayOrigin, rayDir);
		
		intersect = sphere.getIntersection(ray);
		
		// Found with the help of GeoGebra
		double xError = Math.abs(intersect.getX() - (-4.551517925669758));
		double yError = Math.abs(intersect.getY() - (1.144848207433024));
		double zError = Math.abs(intersect.getZ() - (1.724241037165121));
		
		// Allow an error of at most 10^-10
		assertTrue("x coordinate error too large (was " + xError + ")", xError < 1e-10);
		assertTrue("y coordinate error too large (was " + yError + ")", yError < 1e-10);
		assertTrue("z coordinate error too large (was " + zError + ")", zError < 1e-10);
	}
	
}
