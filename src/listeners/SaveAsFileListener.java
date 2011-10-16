package listeners;

import hci.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;

public class SaveAsFileListener implements ActionListener, ItemListener
{
	private ImagePanel panel;
	private JFrame frame;
	
	public SaveAsFileListener(ImagePanel panel, JFrame frame)
	{
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{		
	}

}
