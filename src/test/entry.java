package test;

import maths.Vector;
import maths.Vector3D;
import maths.Ray;
import maths.Plane;
import maths.Matrix;

public class entry {
	public static void main(String[] args) {
		/*
		Vector vecB = new Vector(0, 1.0);
		Vector vecC = new Vector(0, -2);
		
		Matrix matrix = new Matrix(vecA, vecB, vecC);
		System.out.println(matrix);
		
		Vector vecD = new Vector(1.0, 3, 0);
		Vector vecE = new Vector(0, 1.0, 0);
		Matrix matrix2 = new Matrix(vecD, vecE);
		System.out.println(matrix2);
		System.out.println(matrix.mul(matrix2));
		
		Vector testVec = new Vector(1, 0, 0);
		Matrix testRotMat = Matrix.RotationMatrix3D(-Math.PI/20, -Math.PI/4);
		
		System.out.println(testVec.mul(testRotMat));
		*/
		
		Vector vecA = new Vector(1, 0, 0);
		Vector vecB = new Vector(0, 1, 0);
		Vector vecC = new Vector(0, 0, 1);
		
		Matrix testMat = new Matrix(vecA, vecB, vecC);
		System.out.println(testMat);
		System.out.println("inverse below");
		
		Matrix inv = testMat.inverse();
		System.out.println(inv);
		
		System.out.println(testMat.mul(inv));
		System.out.println(inv.mul(testMat));
	}
}
