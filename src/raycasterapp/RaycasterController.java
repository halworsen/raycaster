package raycasterapp;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import data.DataHandleSuccessType;
import data.DataManager;

import maths.Plane;
import maths.Sphere;
import maths.Vector3D;

import render.Camera;
import render.Light;
import render.RayCaster;
import render.RenderScene;

public class RaycasterController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem newSceneItem;

    @FXML
    private MenuItem openItem;

    @FXML
    private MenuItem saveItem;

    @FXML
    private MenuItem saveAsItem;

    @FXML
    private MenuItem quitItem;

    @FXML
    private MenuItem clearSceneItem;

    @FXML
    private MenuItem resetCamItem;
    
    @FXML
    private Canvas renderCanvas;

    @FXML
    private Button translatePX;

    @FXML
    private Button translateNX;

    @FXML
    private Button translatePY;

    @FXML
    private Button translateNY;

    @FXML
    private Button translatePZ;

    @FXML
    private Button translateNZ;

    @FXML
    private Button rotatePY;

    @FXML
    private Button rotateNY;

    @FXML
    private Button rotatePP;

    @FXML
    private Button rotateNP;

    @FXML
    private Button rotatePR;

    @FXML
    private Button rotateNR;

    @FXML
    private TextField addLightX;

    @FXML
    private TextField addLightY;

    @FXML
    private TextField addLightZ;

    @FXML
    private TextField addLightRadius;

    @FXML
    private TextField addLightIntensity;

    @FXML
    private Button addLightButton;

    @FXML
    private TextField addSphereX;

    @FXML
    private TextField addSphereY;

    @FXML
    private TextField addSphereZ;

    @FXML
    private TextField addSphereRadius;

    @FXML
    private Button addSphereButton;

    @FXML
    private TextField addPlaneX;

    @FXML
    private TextField addPlaneY;

    @FXML
    private TextField addPlaneZ;

    @FXML
    private TextField addPlaneNX;

    @FXML
    private TextField addPlaneNY;

    @FXML
    private TextField addPlaneNZ;

    @FXML
    private Button addPlaneButton;
    private Stage stage;
    private DataManager dataManager;

	private Camera cam;
	private RenderScene renderScene;
	private RayCaster renderer;
	
	private int canvasWidth;
	private int canvasHeight;
	
    private boolean unsavedChanges = false;
	
    @FXML
    void onQuitClicked(ActionEvent event) {
    	if(unsavedChanges) {
    		Alert alert = new Alert(AlertType.CONFIRMATION, "You have unsaved changes. Are you sure you want to quit?", ButtonType.YES, ButtonType.NO);
    		alert.showAndWait();
    		
    		if(alert.getResult() == ButtonType.NO) {
    			return;
    		}
    	}
    	
    	Platform.exit();
    }
	
    @FXML
    void clearScene(ActionEvent event) {
    	renderScene.clear();
    	
    	unsavedChanges = true;
		updateTitle();
    	updateCanvas();
    }
    
    @FXML
    void resetCamera(ActionEvent event) {
    	cam.reset();
    	cam.setOrigin(new Vector3D(-10, 0, 0));
    	
    	updateCanvas();
    }
    
    @FXML
    void createNewScene(ActionEvent event) {
    	if(unsavedChanges) {
    		Alert alert = new Alert(AlertType.CONFIRMATION, "You have unsaved changes. Are you sure you want to create a new scene?", ButtonType.YES, ButtonType.NO);
    		alert.showAndWait();
    		
    		if(alert.getResult() == ButtonType.NO) {
    			return;
    		}
    	}
    	
    	renderScene.clear();
    	dataManager.resetCurrentFile();
    	unsavedChanges = false;
    	updateTitle();
    	
    	updateCanvas();
    }

    @FXML
    void openSavedScene(ActionEvent event) {
    	if(unsavedChanges) {
    		Alert alert = new Alert(AlertType.CONFIRMATION, "You have unsaved changes. Are you sure you want to open another scene?", ButtonType.YES, ButtonType.NO);
    		alert.showAndWait();
    		
    		if(alert.getResult() == ButtonType.NO) {
    			return;
    		}
    	}
    	
    	DataHandleSuccessType success = dataManager.load();
    	
    	if(success == DataHandleSuccessType.OK) {
    		unsavedChanges = false;
    		updateTitle();
    	} else if(success == DataHandleSuccessType.FAIL) {
    		Alert alert = new Alert(AlertType.ERROR, "An error occured while opening the selected scene", ButtonType.OK);
    		alert.showAndWait();
    	}
    	
    	updateCanvas();
    }
    
    @FXML
    void saveCurrentScene(ActionEvent event) {
    	DataHandleSuccessType success = dataManager.save();
    	
    	if(success == DataHandleSuccessType.OK) {
    		unsavedChanges = false;
    		updateTitle();
    	} else if(success == DataHandleSuccessType.FAIL) {
    		Alert alert = new Alert(AlertType.ERROR, "An error occured while saving the scene", ButtonType.OK);
    		alert.showAndWait();
    	}
    }

    @FXML
    void saveCurrentSceneAs(ActionEvent event) {
    	DataHandleSuccessType success = dataManager.saveAs();
    	
    	if(success == DataHandleSuccessType.OK) {
    		unsavedChanges = false;
    		updateTitle();
    	} else if(success == DataHandleSuccessType.FAIL) {
    		Alert alert = new Alert(AlertType.ERROR, "An error occured while saving the scene", ButtonType.OK);
    		alert.showAndWait();
    	}
    }
	
    @FXML
    // -X
    void translateNXPress() {
    	this.cam.translate(new Vector3D(-0.5, 0, 0));
    	
    	updateCanvas();
    }

    @FXML
    // -Y
    // Note: Y controls are inverted
    void translateNYPress() {
    	this.cam.translate(new Vector3D(0, 0.5, 0));
    	
    	updateCanvas();
    }

    @FXML
    // -Z
    void translateNZPress() {
    	this.cam.translate(new Vector3D(0, 0, -0.5));
    	
    	updateCanvas();
    }

    @FXML
    // +X
    void translatePXPress() {
    	this.cam.translate(new Vector3D(0.5, 0, 0));
    	
    	updateCanvas();
    }

    @FXML
    // +Y
    void translatePYPress() {
    	this.cam.translate(new Vector3D(0, -0.5, 0));
    	
    	updateCanvas();
    }

    @FXML
    // +Z
    void translatePZPress() {
    	this.cam.translate(new Vector3D(0, 0, 0.5));
    	
    	updateCanvas();
    }
    
    @FXML
    // -Pitch
    void rotateNPPress() {
    	this.cam.rotateY(-Math.PI/40);
    	
    	updateCanvas();
    }

    @FXML
    // -Roll
    void rotateNRPress() {
    	this.cam.rotateX(-Math.PI/40);
    	
    	updateCanvas();
    }

    @FXML
    // -Yaw
    // Note: Yaw controls are inverted. Just felt more right that +yaw brings you right (no pun intended)
    void rotateNYPress() {
    	this.cam.rotateZ(Math.PI/40);
    	
    	updateCanvas();
    }

    @FXML
    // +Pitch
    void rotatePPPress() {
    	this.cam.rotateY(Math.PI/40);
    	
    	updateCanvas();
    }

    @FXML
    // +Roll
    void rotatePRPress() {
    	this.cam.rotateX(Math.PI/40);
    	
    	updateCanvas();
    }

    @FXML
    // +Yaw
    void rotatePYPress() {
    	this.cam.rotateZ(-Math.PI/40);
    	
    	updateCanvas();
    }
    
    @FXML
    // Add a light to the scene
    void addLightPress(ActionEvent event) {
    	double x = (double) addLightX.getTextFormatter().getValue();
    	double y = (double) addLightY.getTextFormatter().getValue();
    	double z = (double) addLightZ.getTextFormatter().getValue();
    	
    	double radius = (double) addLightRadius.getTextFormatter().getValue();
    	double intensity = (double) addLightIntensity.getTextFormatter().getValue();
    	
    	Light newLight = new Light(new Vector3D(x,y,z), radius, intensity);
    	renderScene.addLight(newLight);
    	
    	unsavedChanges = true;
		updateTitle();
    	updateCanvas();
    }

    @FXML
    // Add a plane to the scene
    void addPlanePress(ActionEvent event) {
        double x = (double) addPlaneX.getTextFormatter().getValue();
        double y = (double) addPlaneY.getTextFormatter().getValue();
        double z = (double) addPlaneZ.getTextFormatter().getValue();

        double nx = (double) addPlaneNX.getTextFormatter().getValue();
        double ny = (double) addPlaneNY.getTextFormatter().getValue();
        double nz = (double) addPlaneNZ.getTextFormatter().getValue();
        
        Plane newPlane = new Plane(new Vector3D(nx,ny,nz), new Vector3D(x,y,z));
        renderScene.addGeoShape(newPlane);
        
        unsavedChanges = true;
		updateTitle();
        updateCanvas();
    }

    @FXML
    // Add a sphere to the scene
    void addSpherePress(ActionEvent event) {
        double x = (double) addSphereX.getTextFormatter().getValue();
        double y = (double) addSphereY.getTextFormatter().getValue();
        double z = (double) addSphereZ.getTextFormatter().getValue();
        
        double radius = (double) addSphereRadius.getTextFormatter().getValue();
        
        Sphere newSphere = new Sphere(new Vector3D(x,y,z), radius);
        renderScene.addGeoShape(newSphere);
        
        unsavedChanges = true;
		updateTitle();
        updateCanvas();
    }
    
    void setStage(Stage stage) {
    	if(this.stage != null) { return; }
    	
    	this.stage = stage;
    	this.dataManager = new DataManager(stage, renderScene);
    }
    
    @FXML
    void initialize() {
        assert renderCanvas != null : "fx:id=\"renderCanvas\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert translatePX != null : "fx:id=\"translatePX\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert translateNX != null : "fx:id=\"translateNX\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert translatePY != null : "fx:id=\"translatePY\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert translateNY != null : "fx:id=\"translateNY\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert translatePZ != null : "fx:id=\"translatePZ\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert translateNZ != null : "fx:id=\"translateNZ\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert rotatePY != null : "fx:id=\"rotatePY\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert rotateNY != null : "fx:id=\"rotateNY\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addLightX != null : "fx:id=\"addLightX\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addLightY != null : "fx:id=\"addLightY\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addLightZ != null : "fx:id=\"addLightZ\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addLightButton != null : "fx:id=\"addLightButton\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addSphereX != null : "fx:id=\"addSphereX\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addSphereY != null : "fx:id=\"addSphereY\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addSphereZ != null : "fx:id=\"addSphereZ\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addSphereButton != null : "fx:id=\"addSphereButton\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addPlaneX != null : "fx:id=\"addPlaneX\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addPlaneY != null : "fx:id=\"addPlaneY\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addPlaneZ != null : "fx:id=\"addPlaneZ\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        assert addPlaneButton != null : "fx:id=\"addPlaneButton\" was not injected: check your FXML file 'RaycasterApp.fxml'.";
        
        // Make a filter that can determine if the text entry input is a valid number
        Pattern validInputPattern = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        UnaryOperator<TextFormatter.Change> filter = c -> {
        	String text = c.getControlNewText();
        	if(validInputPattern.matcher(text).matches()) {
        		return c;
        	}else {
        		return null;
        	}
        };
        
        // Same as above for positive numbers only
        Pattern validInputPatternPositive = Pattern.compile("(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        UnaryOperator<TextFormatter.Change> filterPositive = c -> {
        	String text = c.getControlNewText();
        	if(validInputPatternPositive.matcher(text).matches()) {
        		return c;
        	}else {
        		return null;
        	}
        };
        
        // Make a converter that converts text entries into double values
        StringConverter<Double> converter = new StringConverter<Double>() {
            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0;
                }else {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        
        // Make text formatters and apply them to all the text fields
        addLightX.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addLightY.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addLightZ.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addLightRadius.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filterPositive));
        addLightIntensity.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filterPositive));
        addSphereX.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addSphereY.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addSphereZ.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addSphereRadius.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filterPositive));
        addPlaneX.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addPlaneY.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addPlaneZ.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addPlaneNX.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addPlaneNY.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        addPlaneNZ.setTextFormatter(new TextFormatter<Double>(converter, 0.0, filter));
        
		this.canvasWidth = (int)renderCanvas.getWidth();
		this.canvasHeight = (int)renderCanvas.getHeight();
        
        initCanvas();
    }
    
    // Sets up the rendering scene, camera and renderer
    private void initCanvas() {
		this.renderScene = new RenderScene();
		this.cam = new Camera(new Vector3D(-10, 0, 0), 60, 60);
		this.renderer = new RayCaster(cam, canvasWidth, canvasHeight);
		
		updateCanvas();
    }
    
    // Updates the canvas by rendering the scene
    private void updateCanvas() {
		// render the scene
		Color[][] colors = renderer.getColors(this.renderScene);

		PixelWriter writer = renderCanvas.getGraphicsContext2D().getPixelWriter();
		for(int x = 0; x < canvasWidth; x++) {
			for(int y = 0; y < canvasHeight; y++) {
				if(colors[x][y] == null) {
					colors[x][y] = Color.color(0, 0, 0);
				}
				
				writer.setColor(x, y, colors[x][y]);
			}
		}
    }
    
    public void updateTitle() {
    	String sceneName = dataManager.getCurrentSceneName();
    	if(sceneName.isEmpty()) {
    		sceneName = "New Scene";
    	}
    	
		stage.setTitle("Raycaster - " + (unsavedChanges ? "*" : "") + sceneName);
    }
}
