package hci;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpViewer extends JFrame 
{

	public HelpViewer()
	{
		this.setVisible(true);

		Dimension panelSize = new Dimension(400, 300);
		this.setSize(panelSize);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
		
		this.setLocationRelativeTo(null);
		
		
		JTextArea textArea = new JTextArea();
		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		textArea.setEditable(false);
		this.add(textArea);
	}
}
