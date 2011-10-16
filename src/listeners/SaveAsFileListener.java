package listeners;

import hci.ImagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class SaveAsFileListener implements ActionListener, ItemListener
{
	private ImagePanel panel;
	private JFrame frame;
	
	public SaveAsFileListener(ImagePanel panel, JFrame frame)
	{
		this.panel = panel;
		this.frame = frame;
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		int fileReturnValue = fileChooser.showSaveDialog(this.frame);
			
		if (fileReturnValue == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			System.out.println("User wants to save to " + file.getPath());
			
			String xml = this.panel.getPolygonsAsString();
			
			if (xml == null)
			{
				return;
			}
			
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file + ".tags")));
				bw.write(xml);
				bw.flush();
				bw.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

}
