package GraphicsProcessing;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


public class Graphics {
	
	private ArrayList<Image> defaultTileBackgroundImage;
	private File defaultImageBackgroundDirectory = new File("assets\\terrain\\green");
	private File defaultImageDirectory = new File("assets");
	private ArrayList<ArrayList<Image>> listOfImages = new ArrayList<ArrayList<Image>>();
	private Random r = new Random();
	
	public Graphics() {
		readAllDefaultBackgroundImages();
	}
	
	private void readAllDefaultBackgroundImages() {
		defaultTileBackgroundImage = new ArrayList<Image>();
		File[] files = defaultImageBackgroundDirectory.listFiles();
		for (int i = 0; i < files.length; ++i) {
			if (files[i].isFile()) {
				Image image = null;
				try {
					image = ImageIO.read(files[i]);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
				defaultTileBackgroundImage.add(image);
			}
		}
	}

	public Image getRandomTileDefaultBackgroundImage() {
		return defaultTileBackgroundImage.get(r.nextInt(defaultTileBackgroundImage.size()));
	}
	
	public ArrayList<Image> loadAllDefaultImagesFromDirectory(File path) {
		ArrayList<Image> imgFiles = new ArrayList<Image>();
		for (File file : path.listFiles()) {
			if (file.getName().toLowerCase().endsWith(".png")) {
				Image img = null;
				try {
					img = ImageIO.read(file);
				}
				catch (Exception e) {
					//TODO: handling
				}
				if (img != null) {
					imgFiles.add(img);
				}
			}
		}
		return imgFiles;		
	}
	
	public void loadAllDefaultImages() {
		for (File path : defaultImageDirectory.listFiles()) {
			listOfImages.add(loadAllDefaultImagesFromDirectory(path));
		}
	}
	
	public ArrayList<String> getAllDirectoriesName() {
		ArrayList<String> directories = new ArrayList<String>();
		for (File dir : defaultImageDirectory.listFiles()) {
			if (dir.isDirectory()) {
				directories.add(dir.getName());
			}
		}
		return directories;
	}
	
	public ArrayList<ArrayList<Image>> getListOfImages() {
		return listOfImages;
	}
}
