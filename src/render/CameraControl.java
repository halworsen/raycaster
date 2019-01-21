package render;

import maths.Matrix;
import maths.Vector;
import maths.Vector3D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import render.Camera;
import render.MainWindow;

public class CameraControl implements KeyListener{
	private Camera cam;
	private MainWindow drawer;
	private JFrame window;
	
	public CameraControl(Camera cam, MainWindow drawer, JFrame window) {
		this.cam = cam;
		this.drawer = drawer;
		this.window = window;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Vector camPos = (Vector)this.cam.getOrigin();
		Vector camDir = (Vector)this.cam.getDir();
		
		int keyCode = e.getKeyCode();
		
		// move camera forward (w)
		if(keyCode == 87) {
			this.cam.translate(new Vector3D(0.5, 0, 0));
		}
		
		// move camera backward (s)
		if(keyCode == 83) {
			this.cam.translate(new Vector3D(-0.5, 0, 0));
		}
		
		// move camera left (a)
		if(keyCode == 65) {
			this.cam.translate(new Vector3D(0, 0.5, 0));
		}
		
		// move camera right (d)
		if(keyCode == 68) {
			this.cam.translate(new Vector3D(0, -0.5, 0));
		}
		
		// move camera up (space)
		if(keyCode == 32) {
			this.cam.translate(new Vector3D(0, 0, 0.5));
		}
		
		// move camera down (lalt)
		if(keyCode == 18) {
			this.cam.translate(new Vector3D(0, 0, -0.5));
		}
		
		if(!camPos.equals((cam.getOrigin()))){
			drawer.paint();
			window.repaint();
			return;
		}
		
		// yaw left
		if(keyCode == 37) {
			this.cam.rotateAbsoluteZ(Math.PI/40);

		}
		
		// yaw right
		if(keyCode == 39) {
			this.cam.rotateAbsoluteZ(-Math.PI/40);

		}

		// pitch up
		if(keyCode == 38) {
			this.cam.rotateAbsoluteY(Math.PI/40);
		}
		
		// pitch down
		if(keyCode == 40) {
			this.cam.rotateAbsoluteY(-Math.PI/40);
		}
		
		drawer.paint();
		window.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}
}
