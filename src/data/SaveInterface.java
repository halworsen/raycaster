package data;

import render.RenderScene;

public interface SaveInterface {
	public boolean saveToFile(String filePath);
	public boolean loadFromFile(String filePath);
}
