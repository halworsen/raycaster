package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import maths.GeoShape;
import maths.Plane;
import maths.Sphere;
import render.Light;
import render.RenderScene;

public class DataManager implements SaveInterface {
	
	private Stage stage;
	private RenderScene currentRenderScene;
	
	private String currentScenePath = "";
	private String currentSceneName = "";
	
	public DataManager(RenderScene scene) {
		currentRenderScene = scene;
	}
	
	public void setupStage() {
		Platform.runLater(() -> {
			stage = new Stage();
			stage.initModality(Modality.NONE);
			stage.initStyle(StageStyle.UTILITY);
		});
	}
	
	public void setCurrentScene(RenderScene scene) {
		currentRenderScene = scene;
	}
	
	public String getCurrentSceneName() {
		return currentSceneName;
	}
	
	public boolean loadFromFile(String path) {
		try {
			Scanner file = new Scanner(new FileReader(path));
			
			// Alt lagres på en linje, så hvis den ikke har en neste linje til å begynne med er fila tom
			if(!file.hasNextLine()) {
				file.close();
				return false;
			}
			
			Gson gson = new Gson();
			
			String sceneGson = file.nextLine();
			
			Map<String, String> categories;
			ArrayList<Light> lights;
			ArrayList<Sphere> spheres;
			ArrayList<Plane> planes;
			try {
				// Hent ut all lagret data fra JSON-strengen
				categories = gson.fromJson(
						sceneGson,
						new TypeToken<Map<String, String>>() {}.getType()
				);
				
				lights = gson.fromJson(
					categories.get("lights"),
					new TypeToken<ArrayList<Light>>() {}.getType()
				);
				
				spheres = gson.fromJson(
					categories.get("spheres"),
					new TypeToken<ArrayList<Sphere>>() {}.getType()
				);
				
				planes = gson.fromJson(
					categories.get("planes"),
					new TypeToken<ArrayList<Plane>>() {}.getType()
				);
			} catch (JsonSyntaxException e) {
				// Noe som ikke stemmer med filinnholdet
				file.close();
				
				return false;
			}
			
			// Legg til alle objektene til scenen
			currentRenderScene.clear();
			
			// Lys
			for(Light light : lights) {
				currentRenderScene.addLight(light);
			}
			
			// Sfærer
			for(Sphere sphere : spheres) {
				currentRenderScene.addGeoShape(sphere);
			}
			
			// Plan
			for(Plane plane : planes) {
				currentRenderScene.addGeoShape(plane);
			}
			
			file.close();
			
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	public boolean saveToFile(String path) {
		try {
			PrintWriter file = new PrintWriter(path);
			
			Gson gson = new Gson();
			
			/* 
			 * Lagre scenen i "kategoriene" lys, sfærer og plan.
			 * Hver kategori inneholder ny JSON som vi vet hva vil skal dekode til
			 */
			Map<String, String> categories = new HashMap<>();
			// Lagre JSON-representasjonen av listen av lysene i scenen
			categories.put("lights", gson.toJson(currentRenderScene.getLights()));
			
			ArrayList<GeoShape> shapes = currentRenderScene.getShapes();
			
			ArrayList<Sphere> spheres = new ArrayList<>();
			ArrayList<Plane> planes = new ArrayList<>();
			
			for(GeoShape shape : shapes) {
				if(shape instanceof maths.Sphere) {
					Sphere sphere = (Sphere)shape;
					spheres.add(sphere);
				}
				
				if(shape instanceof maths.Plane) {
					Plane plane = (Plane)shape;
					planes.add(plane);
				}
			}

			// Lagre JSON-representasjonene av listene av sfærene og planene i scenen
			categories.put("spheres", gson.toJson(spheres));
			categories.put("planes", gson.toJson(planes));
			
			String sceneGson = gson.toJson(categories);
			file.println(sceneGson);
			
			file.close();
			
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	private void setCurrentFile(File file) {
		currentScenePath = file.getAbsolutePath();
		currentSceneName = file.getName();
	}
	
	public void resetCurrentFile() {
		currentScenePath = "";
		currentSceneName = "";
	}
	
	public DataHandleSuccessType load() {
		if(stage == null)
		{
			throw new IllegalStateException("load was called but the stage was never set up!");
		}
		
		FileChooser fileDialog = new FileChooser();
		fileDialog.setTitle("Open scene");
		fileDialog.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Raycaster Scene", "*.rscn"),
				new FileChooser.ExtensionFilter("All files", "*.*")
		);
		File chosenFile = fileDialog.showOpenDialog(stage);
		
		// Ingen fil ble valgt (avbryt)
		if(chosenFile == null) {
			return DataHandleSuccessType.CANCELLED;
		}
		
		boolean success = loadFromFile(chosenFile.getAbsolutePath());
		if(success) {
			setCurrentFile(chosenFile);
			return DataHandleSuccessType.OK;
		}
		
		return DataHandleSuccessType.FAIL;
	}
	
	public DataHandleSuccessType save() {
		if(currentScenePath.isEmpty()) {
			return saveAs();
		}
		
		boolean success = saveToFile(currentScenePath);
		return success ? DataHandleSuccessType.OK : DataHandleSuccessType.FAIL;
	}
	
	public DataHandleSuccessType saveAs() {
		if(stage == null)
		{
			throw new IllegalStateException("saveAs was called but the stage was never set up!");
		}
		
		FileChooser fileDialog = new FileChooser();
		fileDialog.setTitle("Save scene");
		fileDialog.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Raycaster Scene", "*.rscn"),
				new FileChooser.ExtensionFilter("All files", "*.*")
		);
		File chosenFile = fileDialog.showSaveDialog(stage);
		
		// Avbryt-knapp
		if(chosenFile == null) {
			return DataHandleSuccessType.CANCELLED;
		}
		
		boolean success = saveToFile(chosenFile.getAbsolutePath());
		if(success) {
			setCurrentFile(chosenFile);
			return DataHandleSuccessType.OK;
		}
		
		return DataHandleSuccessType.FAIL;
	}
}
