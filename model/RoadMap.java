package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {

	private List<Junction> ListJunctions;
	private List<Road> ListRoads;
	private List<Vehicle> ListVehicles;
	private Map<String, Junction> JunctionsMap;
	private Map<String, Road> RoadsMap; // map w junction Id and the road itself
	private Map<String, Vehicle> VehiclesMap; // map w vehicle Id and the vehicle

	RoadMap() {
		this.ListJunctions = new ArrayList<>();
		this.ListRoads = new ArrayList<>();
		this.ListVehicles = new ArrayList<>();
		this.JunctionsMap = new HashMap<>();
		this.RoadsMap = new HashMap<>();
		this.VehiclesMap = new HashMap<>();
	}

	protected void addJunction(Junction j) {
		if (ListJunctions.contains(j)) {
			throw new IllegalArgumentException("Tienen el mismo ID");
		} else {
			ListJunctions.add(j);
			JunctionsMap.put(j._id, j);
		}

	}

	protected void addRoad(Road r) {
		if (ListRoads.contains(r)) {
			throw new IllegalArgumentException("The road does exist in the map");
		} else {
			if (RoadsMap.containsValue(r)) {
				throw new IllegalArgumentException("It alredy exists ");
			}
		}
		ListRoads.add(r);
		RoadsMap.put(r._id, r);
	}

	protected void addVehicle(Vehicle v) {
		int itinerarySize = v.getItinerary().size();
		for (Junction j : v.getItinerary()) {
			if (!j.getId().equals(v.getItinerary().get(itinerarySize - 1).getId())) {
				if (!j.hasOutgoing()) {
					throw new IllegalArgumentException("The Junction doesnt have any road to get to the next road");
				}
			}
		}
		if (ListVehicles.contains(v)) {
			throw new IllegalArgumentException("The Vehicle alredy exists in the list");
		} else {
			ListVehicles.add(v);
			VehiclesMap.put(v._id, v);
		}
	}

	public Junction getJunction(String id) {
		Junction j = null;
		if (JunctionsMap.containsKey(id)) {
			j = JunctionsMap.get(id);
		}
		return j;
	}

	public Road getRoad(String id) {
		Road r = null;
		if (RoadsMap.containsKey(id)) {
			r = RoadsMap.get(id);
		}
		return r;
	}

	public Vehicle getVehicle(String id) {
		Vehicle v = null;
		if (VehiclesMap.containsKey(id)) {
			v = VehiclesMap.get(id);
		}
		return v;
	}

	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(ListJunctions);
	}

	public List<Road> getRoads() {
		return Collections.unmodifiableList(ListRoads);
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(ListVehicles);
	}
	public void setContaminationClass(int co2Class, String idVehicle) {
		this.VehiclesMap.get(idVehicle).setContamination(co2Class);
	}

	void reset() {
		ListJunctions.removeAll(ListJunctions);
		JunctionsMap.clear();
		ListRoads.removeAll(ListRoads);
		RoadsMap.clear();
		ListVehicles.removeAll(ListVehicles);
		VehiclesMap.clear();
	}

	public JSONObject report() {
		JSONArray junctionsArray = new JSONArray();
		JSONArray roadsArray = new JSONArray();
		JSONArray vehiclesArray = new JSONArray();
		JSONObject data = new JSONObject();
		int i = 0;
		for (Road r : this.ListRoads) {
			roadsArray.put(i, r.report());
			i++;
		}
		i = 0;
		for (Vehicle v : this.ListVehicles) {
			vehiclesArray.put(i, v.report());
			i++;
		}
		 i = 0;
		for (Junction j : this.ListJunctions) {
			junctionsArray.put(i, j.report());
			i++;
		
		}
		data.put("roads", roadsArray);
		data.put("vehicles", vehiclesArray);
		data.put("junctions", junctionsArray);

		return data;
	}
	
}
