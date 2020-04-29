package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
        int time = (int) data.get("time");
		JSONArray info = data.getJSONArray("info");
		Pair<String, Weather> pair;
		List<Pair<String,Weather>> roadWeather = new ArrayList<>();
		JSONObject pairJSON;
		for(int i = 0; i < info.length(); i++) {
			pairJSON = info.getJSONObject(i);
			String road = (String) pairJSON.get("road");
			Weather weather = Weather.valueOf(pairJSON.getString("weather"));
			pair = new Pair<>(road, weather);
			roadWeather.add(pair);
		}
		return new SetWeatherEvent(time, roadWeather);
	}

}
