package listeners;

import hci.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dialogs.AddTagSelectColourDialog;

public class EditButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ImagePanel.currentlySelectedPolygon != null)
		{
			AddTagSelectColourDialog dialog = new AddTagSelectColourDialog(ImagePanel.currentlySelectedPolygon);
			dialog.setVisible(true);
		}
	}

}
