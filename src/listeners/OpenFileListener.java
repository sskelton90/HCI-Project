package listeners;

import hci.ImageLabeller;
import hci.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import utils.ImageFileFilter;

public class OpenFileListener implements ActionListener, ItemListener {

	private ImagePanel imagePanel;
	private JFrame frame;

	public OpenFileListener(ImagePanel imagePanel, JFrame frame)
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
		System.out.println("Open dat file");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new ImageFileFilter());
		int fileReturnValue = fileChooser.showOpenDialog(this.frame);
			
		if (fileReturnValue == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			imagePanel.setImage(file.getPath());
			
			ImageLabeller.saveAsFile.setEnabled(false);
			ImageLabeller.saveFile.setEnabled(false);
			ImageLabeller.savedOnce = false;
			ImageLabeller.savedAs = "";
		}
	}		
}