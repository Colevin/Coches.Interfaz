package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = (int) data.get("time");
		String id = (String) data.get("id");
		int maxSpeed = data.getInt("maxspeed");
		int class1 = (int) data.get("class");
		JSONArray itinerary = data.getJSONArray("itinerary");
		List<String> itinerario = new ArrayList<>();
		for(int i = 0; i < itinerary.length(); i++) {
			itinerario.add(itinerary.getString(i));
		}
		return new NewVehicleEvent(time, id, maxSpeed, class1, itinerario);
	}

}
