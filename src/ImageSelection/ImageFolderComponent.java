package ImageSelection;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ImageFolderComponent extends JPanel{

	public ImageFolderComponent(FolderImageBox fib, ImageSelectionBoxContainer isbc) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//FolderImage
		add(fib.getPane(), BorderLayout.NORTH);
		//ImageSelectionBox
		add(isbc.getPane(), BorderLayout.CENTER);
	}
	
	
}
