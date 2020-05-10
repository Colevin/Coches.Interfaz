package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private List<Event> events;
	private String[] columns = {"Time", "Desc"};
	
	private static final long serialVersionUID = 1L;
	
	EventsTableModel(Controller ctrl){
		events = new ArrayList<>();
		ctrl.addObserver(this);
	}
	public int getRowCount() {
		return events.size();
	}
	public int getColumnCount() {
		return columns.length;
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = null;
		Event e = this.events.get(rowIndex);
		
		switch(columnIndex){
			case 0:
				o = e.getTime();
				break;
			case 1:
				o = e.toString();
				break;
			default: assert (false);
		}
		return o;
	}
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.events = events;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
