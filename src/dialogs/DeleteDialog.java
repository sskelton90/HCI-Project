package dialogs;

import javax.swing.JDialog;

import shapes.Polygon;

public class DeleteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Polygon selectedPolygon;
	
	public DeleteDialog(Polygon selectedPolygon)
	{
		initUI();
		this.selectedPolygon = selectedPolygon;
	}
	
	private void initUI()
	{
		
	}

}
