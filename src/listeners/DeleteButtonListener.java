package listeners;

import hci.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class DeleteButtonListener implements ActionListener {

	private ImagePanel panel;

	public DeleteButtonListener(ImagePanel panel)
	{
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (ImagePanel.currentlySelectedPolygon != null)
		{
			int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete " + ImagePanel.currentlySelectedPolygon.getTag() + "?", "Are you sure?",
                    JOptionPane.YES_NO_OPTION);
			if (confirmed == JOptionPane.YES_OPTION)
			{
				panel.deletePolygon(ImagePanel.currentlySelectedPolygon);
				panel.needSaved = true;
			}
		}
	}

}
