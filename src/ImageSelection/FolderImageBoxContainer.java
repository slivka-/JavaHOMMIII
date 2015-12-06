package ImageSelection;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FolderImageBoxContainer extends JPanel{

	private FolderImageBox fib;
	private JScrollPane pane;
	
	public FolderImageBoxContainer(FolderImageBox fib) {
		this.fib = fib;
		pane = new JScrollPane(fib.getList());
		initialize();

	}
	
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(220, 200));
	}
	
	public JScrollPane getPane() {
		return pane;
	}
}