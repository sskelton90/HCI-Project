package dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shapes.Polygon;

public class AddTagSelectColourDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Color selectedColour = null;
	private final JTextField textField = new JTextField(10);
	private final Polygon parent;
	private final JLabel currentColour = new JLabel("0x" + Integer.toHexString(Color.GREEN.getRGB()));
	

	public AddTagSelectColourDialog(Polygon parent) 
	{
		initUI();
		this.parent = parent;
	}
	
	public void initUI()
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		this.add(createTextField());
		this.add(createColorChooser());
		this.add(createButtons());
        
		this.setMaximumSize(new Dimension(300, 200));
		this.setPreferredSize(new Dimension(300, 200));
		this.setMinimumSize(new Dimension(300, 200));
		this.setResizable(false);
	}
	
	public JComponent createTextField()
	{
		JPanel innerPanel = new JPanel();
		JLabel label = new JLabel("Tag:");
		
		textField.setMaximumSize(new Dimension(300, 10));
		
		innerPanel.add(label);
		innerPanel.add(textField);
		
		return innerPanel;
	}
	
	public JComponent createColorChooser()
	{
		JPanel innerPanel = new JPanel();
		
		JLabel label = new JLabel("Colour:");
		
		
		JButton colourButton = new JButton("Select Colour...");
		colourButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedColour = JColorChooser.showDialog(
	                     AddTagSelectColourDialog.this,
	                     "Choose Background Color",
	                     Color.GREEN);
				
				if (selectedColour != null)
				{
					currentColour.setForeground(selectedColour);
					currentColour.setText("0x" + Integer.toHexString(selectedColour.getRGB()));
				}
			}
		});
		
		innerPanel.add(label);
		innerPanel.add(currentColour);
		innerPanel.add(colourButton);
		
		return innerPanel;
	}
	
	public JComponent createButtons()
	{
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                parent.setColour(selectedColour);
                parent.setTag(textField.getText());
                parent.drawPolygon(parent.getGraphics());
                dispose();
            }
        });
		
		
		JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        
        innerPanel.add(save);
        innerPanel.add(cancel);
        
        return innerPanel;
	}
	
}
