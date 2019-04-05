package data;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;
import maths.GeoShape;
import maths.Plane;
import maths.Sphere;
import maths.Vector3D;
import render.Light;
import render.RenderScene;

public class DataManagerTest extends TestCase
{
	public void testSaveScene()
	{
		RenderScene rScene = new RenderScene();
		
		// Add a light
		Light light = new Light(new Vector3D(-1.5, 2, 1), 3, 1.5);
		rScene.addLight(light);
		
		// Add a plane to the scene
		Plane plane = new Plane(new Vector3D(1, 0, 0), new Vector3D(0, 0, 0));
		rScene.addGeoShape(plane);
		
		// Add a sphere to the scene
		Sphere sphere = new Sphere(new Vector3D(0, 0, 0), 1.5);
		rScene.addGeoShape(sphere);
		
		DataManager manager = new DataManager(rScene);
		File file = new File("resources/samplescene.rscn");
		String path = file.getAbsolutePath();
		
		assertTrue("Data manager should return true when the save succeeds", manager.saveToFile(path));
	}

	public void testLoadScene()
	{
		RenderScene rScene = new RenderScene();
		
		// Add a sphere to the scene
		Sphere sphere = new Sphere(new Vector3D(0, 0, 0), 5);
		rScene.addGeoShape(sphere);
		
		DataManager manager = new DataManager(rScene);
		File file = new File("resources/samplescene.rscn");
		String path = file.getAbsolutePath();
		
		assertTrue("Data manager should return true when loading valid saved render scenes", manager.loadFromFile(path));
		assertFalse("Data manager should return false when the load fails", manager.loadFromFile("bogusfilethatdoesntexist"));
		
		// Check if the sphere was removed from the scene
		ArrayList<GeoShape> shapes = rScene.getShapes();
		
		for (GeoShape shape : shapes)
		{
			if (shape instanceof Sphere)
			{
				assertNotSame(sphere, (Sphere)shape);
			}
		}
	}
	
}
