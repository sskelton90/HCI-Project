package hci;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ImageDropWindow extends JPanel implements DropTargetListener {

	DropTarget dropTarget;
	JLabel label;
	static DataFlavor urlFlavor, uriListFlavor, macPictStreamFlavor;
	
	static 
	{
		try
		{
			urlFlavor = new DataFlavor("application/x-java-url; class=java.net.URL");
			uriListFlavor = new DataFlavor("text/uri-list; class=java.lang.String");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public ImageDropWindow() 
	{
		super(new BorderLayout());
		label = new JLabel("Drop Here", SwingConstants.CENTER);
		label.setFont(getFont().deriveFont(Font.BOLD, 24.0f));
		add(label, BorderLayout.CENTER);
		
		dropTarget = new DropTarget(label, this);
	}
	
	public void showImageInNewFrame(ImageIcon icon)
	{
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(icon));
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		Transferable trans = dtde.getTransferable();
		
		boolean transferSuccessful = false;
		
		try
		{
			if (trans.isDataFlavorSupported(DataFlavor.imageFlavor))
			{
				System.out.println("Image Flavor");
				Image img = (Image) trans.getTransferData(DataFlavor.imageFlavor);
				showImageInNewFrame(new ImageIcon(img));
		
				transferSuccessful = true; 
			}
			else if (trans.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
			{
				System.out.println("Java File List Flavor");
				List list = (List) trans.getTransferData(DataFlavor.javaFileListFlavor);
				ListIterator it = list.listIterator();
				
				while (it.hasNext())
				{
					File f = (File) it.next();
					BufferedImage img = ImageIO.read(f);
					ImageIcon icon = new ImageIcon(img);
					showImageInNewFrame(icon);
				}
				
				transferSuccessful = true;
			}
			else if (trans.isDataFlavorSupported(uriListFlavor))
			{
				System.out.println("URI List Flavor");
				String uris = (String) trans.getTransferData(uriListFlavor);
				
				StringTokenizer tokenizer = new StringTokenizer(uris, "\r\n");
				while (tokenizer.hasMoreTokens())
				{
					String uri = tokenizer.nextToken();
					String newUri = uri.substring(5);
					System.out.println(newUri);
					File f = new File(newUri);
					if (f.exists())
					{
						BufferedImage img = ImageIO.read(f);
						ImageIcon icon = new ImageIcon(img);
						showImageInNewFrame(icon);
					}
				}
				
				transferSuccessful = true;
			}
			else if (trans.isDataFlavorSupported(urlFlavor))
			{
				System.out.println("URL Flavor");
				URL url = (URL) trans.getTransferData(urlFlavor);
				
				ImageIcon icon = new ImageIcon(url);
				showImageInNewFrame(icon);
				
				transferSuccessful = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dtde.dropComplete(transferSuccessful);
		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Image DropTarget Demo");
		ImageDropWindow demoPanel = new ImageDropWindow();
		
		frame.getContentPane().add(demoPanel);
		frame.pack();
		frame.setVisible(true);
	}
}

