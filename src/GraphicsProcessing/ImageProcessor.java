package GraphicsProcessing;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageProcessor {

	public static BufferedImage[] divideImage(Image img, int cellWidth, int cellHeight) {
		int rows = img.getHeight(null) / cellHeight;
		int cols = img.getWidth(null) / cellWidth;
		
		int chunks = rows * cols;
		
		BufferedImage divImage = (BufferedImage)img;
		
		BufferedImage imgs[] = new BufferedImage[chunks];
		int chunkCount = 0;
		
		for (int x = 0; x < rows; ++x) {
			for (int y = 0; y < cols; ++y) {
				imgs[chunkCount] = new BufferedImage(cellWidth, cellHeight, divImage.getType());
				Graphics2D gr = imgs[chunkCount++].createGraphics();
				gr.drawImage(divImage, 0, 0, cellWidth,  cellHeight,
						y*cellWidth,  x*cellHeight, (y+1)*cellWidth,  (x+1)*cellHeight, null);
				gr.dispose();
			}
		}
		return imgs;
	}
}
