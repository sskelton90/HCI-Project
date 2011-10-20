package hci;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class HelpViewer extends JFrame
{

	public HelpViewer()
	{
		this.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);
		
		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(800, 600));
		
        JEditorPane textArea = new JEditorPane();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
 
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);

		String textToSet = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader("textfiles/HelpFile"));
			String str;
			while ((str = in.readLine()) != null) 
			{
				textToSet += (str + "\n");
			}
			in.close();
		} catch (IOException e) {}
		
		textArea.setText(textToSet);
	}
}
