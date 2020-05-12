package simulator.model;

import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exception.dqStratJunNullException;
import exception.lsStratJunctionNullException;
import exception.srcDesJunNotSameException;
import exception.xJunNegException;
import exception.yJunNegException;

public class Junction extends SimulatedObject {
	private List<Road> listIncRoad; // Lista de carreteras que llegan a la juncion
	private Map<Junction, Road> mapOutgoing; // Tiene 2 valores, la juncion J est� conectada con la carretera C. Para
												// saber qu� carretera llega a qu� juncion
	private List<List<Vehicle>> listQue; // lista de listas de vehiculos de cada carretera
	private int greenIndex; // indice de la carretera de llegada que esta a verde
	private int lastSwTime; // tiempo que hace que se cambio el semaforo a verde
	private LightSwitchingStrategy lighStrat;
	private DequeuingStrategy deqStrat;
	private int x;
	private int y;
	private Map<Road, List<Vehicle>> roadMap;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor)
			throws IllegalArgumentException {
		super(id);
		this.mapOutgoing = new HashMap<>();
		this.listIncRoad = new ArrayList<>();
		this.listQue = new LinkedList<>();
		this.roadMap = new HashMap<>();

		if (lsStrategy == null)
			throw new lsStratJunctionNullException("The lsStrategty has null value");
		else {
			this.lighStrat = lsStrategy;
		}
		if (dqStrategy == null)
			throw new dqStratJunNullException("The deqStrategty has null value");
		else {
			this.deqStrat = dqStrategy;
		}
		if (x < 0)
			throw new xJunNegException("The x has neg value");
		else {
			this.x = xCoor;
		}
		if (y < 0)
			throw new yJunNegException("The y has neg value");
		else {
			this.y = yCoor;
		}
		this.greenIndex = -1;
		this.lastSwTime = 0;
	}

	void addIncommingRoad(Road r) {
		if (r.getSrc() == this) {
			throw new srcDesJunNotSameException("The junction of arrival and destination are not the same");
		} else {
			listIncRoad.add(r);
			List<Vehicle> que = new LinkedList<>();
			listQue.add(que);
			roadMap.put(r, que);
		}

	}

	void addOutGoingRoad(Road r) {
		if (r.getDest() == this) {
			throw new srcDesJunNotSameException("The junction of arrival and destination not the same");
		}
		/*
		 * if(r.getDestJunc().listIncRoad.size() != 0) { throw new
		 * IllegalArgumentException("The junction of arrival is not empty"); }
		 */
		else {
			mapOutgoing.put(r.getDest(), r);
		}

	}

	void enter(Vehicle v) {
		roadMap.get(v.getRoad()).add(v);
	}

	Road roadTo(Junction j) {
		return this.mapOutgoing.get(j);
	}

	@Override
	void advance(int time) {
		int newGreen = -1;

		if (this.greenIndex != -1 && !this.listQue.isEmpty() && !this.listQue.get(greenIndex).isEmpty()) {
			List<Vehicle> nextGreen = this.deqStrat.dequeue(this.listQue.get(greenIndex));
			// llama a la carretera que tiene ahora el verde para q sea esa la que avance
			for (int i = 0; i < nextGreen.size();i++) {
				nextGreen.get(i).moveToNextRoad();
				// loss coches se mueven a la siguiente carretera
				this.listQue.get(greenIndex).remove(i);
			}
		}
		newGreen = this.lighStrat.chooseNextGreen(this.listIncRoad, this.listQue, this.greenIndex, this.lastSwTime,time);
	
		if (this.greenIndex != newGreen) {
			this.greenIndex = newGreen;
			this.lastSwTime = time;
		}
	}

	@Override
	public JSONObject report() throws JSONException {
		JSONObject data = new JSONObject();
		JSONArray roadsArray = new JSONArray();

		if (greenIndex == -1) {
			data.put("green", "none");
		} else {
			data.put("green", listIncRoad.get(greenIndex).getId());
		}
		for (Road r : listIncRoad) {
			JSONObject road = new JSONObject();
			JSONArray vehicles = new JSONArray();
			road.put("road", r.getId());
			for (Vehicle v : roadMap.get(r)) { // cambiar nombre de road list a road map
				vehicles.put(v.getId());
			}
			road.put("vehicles", vehicles);
			roadsArray.put(road);
		}
		data.put("queues", roadsArray);
		data.put("id", this._id);
		return data;

//		StringBuilder sb = new StringBuilder();
//		String id = "\"id\" :  \" " + this._id + "\",";
//		if(this.listIncRoad.get(greenIndex) == null) {
//			String green = "\"green\" : none ,"; 
//		}
//		else {
//		String green = "\"green\" :   " + this.listIncRoad.get(greenIndex) + ","; 
//		}
//		String queues = "\"queues\" : [";
//		String qid;
//		
//		String ve = "\"vehicles\" :  \" " + this.vehicles + ",";

//		return new JSONObject( sb.append(id).append(sp).append(wea).append(co2).append(ve).toString());
	}

	public boolean hasOutgoing() {
		return this.mapOutgoing.size() > 0;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public String displayQueue() {
		String output = " ";
		for(Road r :this.listIncRoad) {
			int counter = 0;
			output += r.getId() + ": [ " ;
			List<Vehicle> roadQue = roadMap.get(r);
			for(Vehicle v : roadQue) {
				if(counter < roadQue.size()-1) {
					output += v.getId() + ",";
					counter++;
				}
			}	
				output += "] "; 
		}
		return output;
	}
	
	public int getGreenLightIndex() {
		return this.greenIndex;
	}

	public List<Road> getInRoads() {
		return this.listIncRoad;
	}
	public Map<Road, List<Vehicle>> getJunctionMap(){
		return this.roadMap;
	}
	public List<Vehicle> whatQueue(Road r){
		return this.roadMap.get(r);
	}
}
