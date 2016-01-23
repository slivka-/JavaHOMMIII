package GraphicsProcessing;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;


public class Graphics {
	
	private ArrayList<Image> defaultTileBackgroundImage;
	private File defaultImageBackgroundDirectory = new File("assets\\terrain\\green");
	private File defaultImageDirectory = new File("assets");
	private Random r = new Random();
	// catalog: fileName, imgFile
	private HashMap<String, HashMap<String, BufferedImage>> listImages = new HashMap<String, HashMap<String, BufferedImage>>();
	
	public Graphics() {
		readAllDefaultBackgroundImages();
		loadAllDefaultImages();
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


	public void loadAllDefaultImages() {
		for (File path : defaultImageDirectory.listFiles()) {
			listImages.put(path.getName(), loadAllDefaultImagesFromDirectory(path));
		}
	}

	public HashMap<String, BufferedImage> loadAllDefaultImagesFromDirectory(File path) {
		HashMap<String, BufferedImage> imgFiles = new HashMap<String, BufferedImage>();
		for (File file : path.listFiles()) {
			//System.out.println("File name: " + file.getName());
			if (file.getName().toLowerCase().endsWith(".png")) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(file);
				}
				catch (Exception e) {
					//TODO: handling
				}
				if (img != null) {
					imgFiles.put(file.getName(), img);
				}
			}
		}
		return imgFiles;
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

	public HashMap<String, HashMap<String, BufferedImage>> getListImages() {
		return listImages;
	}
}
