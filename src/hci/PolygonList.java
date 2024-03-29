package hci;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shapes.Polygon;

public class PolygonList extends JPanel implements ListSelectionListener 
{
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel listModel;
	private JList list;
	
	public PolygonList()
	{
		super(new BorderLayout());
		
		listModel = new DefaultListModel();
		
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(5);
		list.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(list);
		
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addPolygon(Polygon polygon)
	{
		for (int i = 0; i < listModel.getSize(); i++)
		{
			if (polygon.equals(listModel.get(i)))
			{
				listModel.add(i, polygon);
				listModel.remove(i);
				return;
			}
			
		}
		this.listModel.addElement(polygon);
	}
	
	public void removePolygon(Polygon polygon)
	{
		this.listModel.removeElement(polygon);
	}
	
	public void replaceItems(ArrayList<Polygon> polygons)
	{
		this.listModel.clear();
		
		for (Polygon p : polygons){
			this.listModel.addElement(p);
		}
	}
	
	public void setSelected(Polygon polygon)
	{
		this.list.setSelectedValue(polygon, true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
//		int index = list.getSelectedIndex();
//		if (listModel.getSize() == 0)
//		{
//			ImageLabeller.editTag.setEnabled(false);
//			ImageLabeller.deletePolygon.setEnabled(false);
//			return;
//		}
//		
//		for (int i = 0; i < listModel.getSize(); i++)
//		{
//			Polygon polygon = (Polygon) this.listModel.get(i);
//			polygon.unselect();
//		}
//		
//		if (index != -1)
//		{
//			Polygon polygon = (Polygon) this.listModel.get(index);
//			polygon.select();
//			
//			ImagePanel.currentlySelectedPolygon = polygon;
//			
//			ImageLabeller.editTag.setEnabled(true);
//			ImageLabeller.deletePolygon.setEnabled(true);
//		}
	}

}
