package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{
	private static final long serialVersionUID = 1L;
	private List<Vehicle> vehicles;
	private String[] columns = {"Id", "Location", "Itinerary", "Co2 Class", "Max.Speed", "Speed", "Total CO2", "Distance"};
	VehiclesTableModel(Controller ctrl){
		vehicles = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return vehicles.size();
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
		Vehicle v = this.vehicles.get(rowIndex);
		
		switch(columnIndex){
			case 0:
				s = v.getId();
				break;
			case 1:
				s = v.getLocation();
				break;
			case 2:
				s= v.getItinerary();
				break;
			case 3:
				s= v.getContClass();
				break;
			case 4:
				s=v.getMaxSpeed();
				break;
			case 5:
				s=v.getSpeed();
				break;
			case 6:
				s=v.getTotalCont();
				break;
			case 7:
				s=v.getDistance();
				break;
			
			default: assert (false);
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.vehicles = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.vehicles = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.vehicles = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.vehicles = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.vehicles = map.getVehicles();
		fireTableStructureChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
