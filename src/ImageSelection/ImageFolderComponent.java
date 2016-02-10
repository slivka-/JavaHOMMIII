package ImageSelection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ImageFolderComponent extends JPanel{

	public ImageFolderComponent(FolderImageBox fib, ImageSelectionBox isb) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//FolderImage
		add(fib.getPane(), BorderLayout.NORTH);
		//ImageSelectionBox
		add(isb.getPane(), BorderLayout.CENTER);

		Button unitButton = new Button("ODDZIAL");
		unitButton.setVisible(true);
		unitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				isb.setSelctedToUnit();
			}
		});
		add(unitButton, BorderLayout.SOUTH);

	}
	
	
}
