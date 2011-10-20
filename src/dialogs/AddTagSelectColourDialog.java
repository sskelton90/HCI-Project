package dialogs;

import hci.ImageLabeller;
import hci.ImagePanel;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shapes.Polygon;

public class AddTagSelectColourDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Color selectedColour = Color.GREEN;
	private final JTextField textField = new JTextField(10);
	private final Polygon parentPolygon;
	private final JLabel currentColourText = new JLabel("0x" + Integer.toHexString(Color.GREEN.getRGB()));
	private final ImagePanel parentPanel;
	private boolean edit;

	public AddTagSelectColourDialog(Polygon parentPolygon, ImagePanel parentPanel) 
	{
		initUI();
		this.parentPolygon = parentPolygon;
		this.parentPanel = parentPanel;
		
		if (parentPolygon.getTag() != null)
		{
			this.edit = true;
			this.textField.setText(parentPolygon.getTag());
		}
		
		if (parentPolygon.getColour() != Color.GREEN)
		{
			this.currentColourText.setText("0x" + Integer.toHexString(parentPolygon.getColour().getRGB()));
			this.currentColourText.setForeground(parentPolygon.getColour());
			this.selectedColour = parentPolygon.getColour();
		}
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
		
		this.setLocationRelativeTo(null);
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
		
		this.currentColourText.setForeground(selectedColour);
		
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
					currentColourText.setForeground(selectedColour);
					currentColourText.setText("0x" + Integer.toHexString(selectedColour.getRGB()));
				}
			}
		});
		
		innerPanel.add(label);
		innerPanel.add(currentColourText);
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
            	if (textField.getText().isEmpty())
            	{
            		JOptionPane.showMessageDialog(AddTagSelectColourDialog.this, "Tags must have a title.\nPlease name your tag.", "Forgotten to name your tag?", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	
                parentPolygon.setColour(selectedColour);
                parentPolygon.setTag(textField.getText());
                ImageLabeller.polygonList.addPolygon(parentPolygon);
                dispose();
            }
        });
		
		
		JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
            	if (!edit)
            	{
            		parentPanel.deletePolygon(parentPolygon);
            		parentPolygon.setHidden();
            		parentPanel.resetShape();
            		parentPanel.repaint();
            	}
                dispose();
            }
        });
        
        innerPanel.add(save);
        innerPanel.add(cancel);
        
        return innerPanel;
	}
	
}
