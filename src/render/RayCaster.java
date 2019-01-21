package render;

import java.awt.Color;

import maths.Matrix;
import maths.Ray;
import maths.Vector3D;
import maths.GeoShape;

public class RayCaster {
	// Camera to cast from
	Camera cam;
	// Interseciton points that have this distance or greater from the camera are colored full black
	int greatestDist;
	
	// How many pixels to compute in the X direction
	int pixelsX;
	// How many pixels to compute in the Y direction
	int pixelsY;
	
	public RayCaster(Camera cam, int greatestDist, int pixelsX, int pixelsY) {
		super();
		this.cam = cam;
		this.greatestDist = greatestDist;
		this.pixelsX = pixelsX;
		this.pixelsY = pixelsY;
	}
	
	public int[][] getColors(GeoShape renderObject) {
		int colorArray[][] = new int[this.pixelsX][this.pixelsY];
		
		double angleStepHorizontal = (double)this.cam.getHFOV() / (double)this.pixelsX;
		double angleStepVertical = (double)this.cam.getVFOV() / (double)this.pixelsY;
	
		Matrix transform = this.cam.getTransform().inverse();
		
		// find the place the camera is pointing directly at first
		Ray camRay = new Ray(cam.getOrigin(), cam.getDir());
		Vector3D camIntersect = renderObject.getIntersection(camRay);
		
		if(camIntersect == null) {
			// default to considering the length to the camera
			camIntersect = cam.getOrigin();
		}
		
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
				Vector3D intersect = renderObject.getIntersection(ray);
				
				if(intersect == null) {
					c = new Color(0, 0, 0);
				}else {
					Vector3D toIntersect = new Vector3D(intersect.sub(camIntersect));
					
					float color = (float)(1 - Math.min(toIntersect.length(), greatestDist) / greatestDist);
					c = new Color(color, color, color);
				}
				
				if(angV == 0 && angH == 0) {
					c = new Color(1, 0, 0);
				}
				
				colorArray[x][y] = c.getRGB();
			}
		}
		
		return colorArray;
	}
	
}
