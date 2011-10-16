package listeners;

import hci.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class DeleteButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ImagePanel.currentlySelectedPolygon != null)
		{
			int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete " + ImagePanel.currentlySelectedPolygon.getTag() + "?", "Are you sure?",
                    JOptionPane.YES_NO_OPTION);
			if (confirmed == JOptionPane.YES_OPTION)
			{
				//delete polygon
			}
		}
	}

}
