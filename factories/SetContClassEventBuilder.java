package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = (int) data.get("time");
		JSONArray info = data.getJSONArray("info");
		Pair<String, Integer> pair;
		List<Pair<String,Integer>> roadCont = new ArrayList<>();
		JSONObject pairJSON;
		for(int i = 0; i < info.length(); i++) {
			pairJSON = info.getJSONObject(i);
			String road = pairJSON.getString("vehicle");
			int cont = (int) pairJSON.get("class");
			pair = new Pair<>(road, cont);
			roadCont.add(pair);
		}
		return new NewSetContClassEvent(time, roadCont);
	}
}
