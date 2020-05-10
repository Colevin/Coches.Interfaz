package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;
	private List<Junction> junctions;
	private String[] columns = {"Id", "Green", "Queues"};
	JunctionsTableModel(Controller ctrl){
		junctions = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return junctions.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}
	public String getColumnName(int column) {
		return columns[column];
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		Junction j = this.junctions.get(rowIndex);
		
		switch(columnIndex){
			case 0:
				s = j.getId();
				break;
			case 1:
				s = j.getGreenLightIndex();
				if(Integer.valueOf(s.toString()) == -1) {
					s = "NONE";
				}
				break;
			case 2:
				s= j.displayQueue();
				break;
			default: assert (false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.junctions = map.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
