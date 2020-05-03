package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	private RoadMap roadMap;
	private List<Event> listEvent;
	private int time;
	private List<TrafficSimObserver> trafficSimList ; 
//ONERROR
	
	public TrafficSimulator() {
		this.time = 0;
		this.listEvent = new SortedArrayList<>();
		this.roadMap = new RoadMap();
		this.trafficSimList = new ArrayList<>();
	}

	public void addEvent(Event e) {
		if (e != null) {
			this.listEvent.add(e);
			for(TrafficSimObserver tf: trafficSimList) {
				tf.onEventAdded(roadMap, listEvent, e, time);;
			}
		}
	}

	public void advance() {
		this.time++;
		for(TrafficSimObserver tf: trafficSimList) {
			tf.onAdvanceStart(roadMap, listEvent, time);
		}
		while(listEvent.size()>0 && (listEvent.get(0).getTime() == this.time)) {
			listEvent.remove(0).execute(roadMap);
		}
		for (Junction j : roadMap.getJunctions()) {
			j.advance(time);
		}
		for (Road r : roadMap.getRoads()) {
			r.advance(time);
		}
		for(TrafficSimObserver tf: trafficSimList) {
			tf.onAdvanceEnd(roadMap, listEvent, time);
		}
	}

	public void reset() {
		roadMap.reset();
		listEvent.clear();
		this.time = 0;
		for(TrafficSimObserver tf: trafficSimList) {
			tf.onReset(roadMap, listEvent, time);
		}
	}

	public JSONObject report() {
		JSONObject data = new JSONObject();
		data.put("time", this.time);
		data.put("state", this.roadMap.report());
		return data;
	}

	public void addObserver(TrafficSimObserver o) {
		if(!this.trafficSimList.contains(o)) {
			this.trafficSimList.add(o);
			o.onRegister(roadMap, listEvent, time);
		}
	}

	public void removeObserver(TrafficSimObserver o) {
		this.trafficSimList.remove(o);
	}
	public List<Vehicle> getVehicles() {
		return roadMap.getVehicles();
	}
	public List<Road> getRoads(){
		return roadMap.getRoads();
	}
	public int getTicks() {
		return this.time;
	}
}
