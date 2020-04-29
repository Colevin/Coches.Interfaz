package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	private  List<Pair<String,Weather>> ws;
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws == null) {
			throw new IllegalArgumentException();
		}
		else this.ws = ws;
		}

	@Override
	void execute(RoadMap map) {
	if(!ws.isEmpty()) { 
		for(Pair<String, Weather> p: ws) {
			map.getRoad(p.getFirst()).setWeather(p.getSecond());
		}
	}
		else throw new IllegalArgumentException("The contamination class is empty");
	}
	public String toString() {
		return "New Weather '"+ ws +"'";
	}
}
