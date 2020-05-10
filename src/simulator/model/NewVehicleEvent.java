package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	private List<String> itineraryStr;
	private int maxSpeed;
	private int contClass;
	private String id;
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itineraryStr) {
			super(time);
			this.id= id;
			this.itineraryStr = itineraryStr;
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			}

	@Override
	void execute(RoadMap map) {
		List<Junction> itinerary = new ArrayList<>();
		for(String jId: itineraryStr) {
			if(map.getJunction(jId)!= null) {
				itinerary.add(map.getJunction(jId));
			}
		}
		Vehicle v = new Vehicle(this.id, this.maxSpeed, this.contClass, itinerary);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
	public String toString() {
		return "New Vehicle '"+ id +"'";
	}
}
