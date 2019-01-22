package render;

import java.awt.Color;

import maths.Matrix;
import maths.Ray;
import maths.Vector3D;
import maths.GeoShape;

import render.Scene;
import render.Light;

public class RayCaster {
	// Camera to cast from
	Camera cam;
	
	// How many pixels to compute in the X direction
	int pixelsX;
	// How many pixels to compute in the Y direction
	int pixelsY;
	
	public RayCaster(Camera cam, int pixelsX, int pixelsY) {
		this.cam = cam;
		this.pixelsX = pixelsX;
		this.pixelsY = pixelsY;
	}
	
	// Renders the given scene
	public int[][] getColors(Scene scene) {
		int colorArray[][] = new int[this.pixelsX][this.pixelsY];
		
		// no lights, nothing's gonna be visible anyways
		if(scene.getLightCount() == 0) {
			return colorArray;
		}
		
		double[][] distances = new double[this.pixelsX][this.pixelsY];
		
		double angleStepHorizontal = (double)this.cam.getHFOV() / (double)this.pixelsX;
		double angleStepVertical = (double)this.cam.getVFOV() / (double)this.pixelsY;
	
		Matrix transform = this.cam.getTransform().inverse();
		
		// Cast a ray for each pixel. For each ray, compute its intersection with all scene objects
		// We compute colors on the closest intersection points

		for(int y = 0; y < this.pixelsY; y++) {
			double angV = Math.toRadians(-cam.getVFOV()/2 + (angleStepVertical * y));
			
			for(int x = 0; x < this.pixelsX; x++) {
				double angH = Math.toRadians(-cam.getHFOV()/2 + (angleStepHorizontal * x));
				
				Color c;
				
				// Perform the rotations in the standard basis, then transform to the camera basis
				Vector3D renderDir = new Vector3D(1, 0, 0);
				
				Matrix rotationMatrix = Matrix.RotationMatrix3DY(angV);
				rotationMatrix = rotationMatrix.mul(Matrix.RotationMatrix3DZ(angH));
				rotationMatrix = rotationMatrix.mul(transform);
				
				renderDir = new Vector3D(renderDir.mul(rotationMatrix));
				
				Ray ray = new Ray(cam.getOrigin(), (Vector3D)renderDir);

				for(GeoShape renderObject : scene.getShapes()) {

					Vector3D intersect = renderObject.getIntersection(ray);
					
					if(intersect == null) {
						continue;
					}
					
					double distToCam = intersect.sub(this.cam.getOrigin()).length();
					
					// Only render the shapes closest to the camera
					if((distToCam < distances[x][y]) || (distances[x][y] == 0)) {
						distances[x][y] = distToCam;

						float totalLight = 0;
						// Compute color for the point
						for(Light lightSrc : scene.getLights()) {
							totalLight += (float)lightSrc.computeColorIntensity(intersect);
						}
						
						c = new Color(totalLight, totalLight, totalLight);
						colorArray[x][y] = c.getRGB();
					}

				}
			}
		}
			
		return colorArray;
	}
	
}
