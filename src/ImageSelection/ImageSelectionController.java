package ImageSelection;

public class ImageSelectionController {

	private ImageSelectionBox isb;
	private FolderImageBox fib;
	
	public ImageSelectionController(ImageSelectionBox isb, FolderImageBox fib) {
		this.isb = isb;
		this.fib = fib;
		initialize();
		
	}
	
	private void initialize() {
		fib.setController(this);		
		fib.initialize();
	}
	
	
	public void setSelectedDirectory(int index) {
		isb.setImageSet(index);
	}
}
