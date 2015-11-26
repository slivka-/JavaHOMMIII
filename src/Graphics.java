import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;


public class Graphics {
	
	private ArrayList<Image> defaultTileBackgroundImage;
	private File defaultImageBackgroundDirectory = new File("assets\\terrain\\green");
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
}
