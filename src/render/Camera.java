package render;

import maths.Vector;
import maths.Matrix;
import maths.Vector3D;

public class Camera {
	
	private Vector3D origin;
	// Transformation from the camera's basis to the standard basis
	private Matrix basisTransform;
	
	private int horizontalFOV;
	private int verticalFOV;
	
	public Camera(Vector3D origin, int hFOV, int vFOV) {
		this.origin = origin;
		
		this.horizontalFOV = hFOV;
		this.verticalFOV = vFOV;
		
		this.basisTransform = new Matrix(new Vector(1,0,0), new Vector(0,1,0), new Vector(0,0,1));
	}

	// Returns the camera origin
	public Vector3D getOrigin() {
		return this.origin;
	}
	
	// The camera direction is ALWAYS in the direction of the X unit in the camera's coordinate system
	public Vector3D getDir() {
		return new Vector3D(this.basisTransform.getColumn(0));
	}
	
	public Matrix getTransform() {
		return this.basisTransform;
	}

	public int getHFOV() {
		return this.horizontalFOV;
	}

	public int getVFOV() {
		return this.verticalFOV;
	}
	
	public void setOrigin(Vector3D newOrigin) {
		this.origin = newOrigin;
	}
	
	// Resets camera origin and basis/orientation
	public void reset() {
		this.origin = new Vector3D();
		this.basisTransform = new Matrix(new Vector(1,0,0), new Vector(0,1,0), new Vector(0,0,1));
	}

	// Translates the camera in the standard basis
	public void translateAbsolute(Vector3D translation) {
		this.origin = new Vector3D(this.origin.add(translation));
	}
	
	// Translates the camera locally
	public void translate(Vector3D translation) {
		translation = new Vector3D(translation.mul(this.basisTransform.inverse()));
		this.translateAbsolute(translation);
	}
	
	// Rotates the camera around the standard basis X axis
	public void rotateAbsoluteX(double theta) {
		Matrix rotationMatrix = Matrix.RotationMatrix3DX(theta);
		rotationMatrix = rotationMatrix.mul(this.basisTransform);
		
		this.basisTransform = rotationMatrix;
	}
	
	// Rotates the camera around the standard basis Y axis
	public void rotateAbsoluteY(double theta) {
		Matrix rotationMatrix = Matrix.RotationMatrix3DY(theta);
		rotationMatrix = rotationMatrix.mul(this.basisTransform);
		
		this.basisTransform = rotationMatrix;
	}
	
	// Rotates the camera around the standard basis Z axis
	public void rotateAbsoluteZ(double theta) {
		Matrix rotationMatrix = Matrix.RotationMatrix3DZ(theta);
		rotationMatrix = rotationMatrix.mul(this.basisTransform);
		
		this.basisTransform = rotationMatrix;
	}
	
	// Rotates the camera locally around the X axis
	// performs the rotation in the normal XYZ coordinate system then transforms to the camera's basis
	public void rotateX(double theta) {
		Matrix rotationMatrix = Matrix.RotationMatrix3DX(theta);
		// Rotate, then transform to the camera basis
		rotationMatrix = rotationMatrix.mul(this.basisTransform.inverse());
		
		this.basisTransform = rotationMatrix;
	}
	
	// Rotates the camera locally around the Y axis
	public void rotateY(double theta) {
		Matrix rotationMatrix = Matrix.RotationMatrix3DY(theta);
		rotationMatrix = rotationMatrix.mul(this.basisTransform.inverse());

		this.basisTransform = rotationMatrix;
	}
	
	// Rotates the camera locally around the Z axis
	public void rotateZ(double theta) {
		Matrix rotationMatrix = Matrix.RotationMatrix3DZ(theta);
		rotationMatrix = rotationMatrix.mul(this.basisTransform.inverse());

		this.basisTransform = rotationMatrix;
	}
}
