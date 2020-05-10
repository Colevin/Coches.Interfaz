package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;
	private List<Road> roads;
	private String[] columns = {"Id", "Length", "Weather", "Max.Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	RoadsTableModel(Controller ctrl){
		roads = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return roads.size();
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
		Road r = this.roads.get(rowIndex);
		
		switch(columnIndex){
			case 0:
				s = r.getId();
				break;
			case 1:
				s = r.roadLenght();
				break;
			case 2:
				s= r.getWeather();
				break;
			case 3:
				s= r.getMaxSpeed();
				break;
			case 4:
				s=r.getMaxSpeed();
				break;
			case 5:
				s=r.getTotalCO2();
				break;
			case 6:
				s=r.getContLimit();
				break;
			default: assert (false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.roads = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
