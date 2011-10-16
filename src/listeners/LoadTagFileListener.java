package listeners;

import hci.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import utils.TagFileFilter;

public class LoadTagFileListener implements ActionListener, ItemListener {

	private ImagePanel imagePanel;
	private JFrame frame;

	public LoadTagFileListener(ImagePanel imagePanel, JFrame frame)
	{
		this.imagePanel = imagePanel;
		this.frame = frame;
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new TagFileFilter());
		int fileReturnValue = fileChooser.showOpenDialog(this.frame);

		if (fileReturnValue == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			this.imagePanel.loadPolygonsFromFile(file);			
			System.out.println("File " + file.getPath() + " has been selected.");
		}
	}		
}