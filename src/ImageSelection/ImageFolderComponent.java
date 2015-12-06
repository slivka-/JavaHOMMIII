package ImageSelection;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ImageFolderComponent extends JPanel{

	public ImageFolderComponent(FolderImageBoxContainer fibc, ImageSelectionBoxContainer isbc) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//FolderImageBox
		add(fibc.getPane(), BorderLayout.NORTH);
		//ImageSelectionBox
		add(isbc.getPane(), BorderLayout.CENTER);
	}
	
	
}
