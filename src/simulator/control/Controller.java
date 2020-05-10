package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
import simulator.model.Vehicle;

public class Controller {
	private TrafficSimulator ts;
	private Factory<Event> fe;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
		if(sim == null) {
			throw new IllegalArgumentException("the simulator is null");
		}
		else if (eventsFactory == null)  {
			throw new IllegalArgumentException("the eventFactory is null");
		}
		else{
			this.ts = sim;
			this.fe = eventsFactory;
		}
	}
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));	
		if(!jo.has("events")) {
			throw new IllegalArgumentException("The input file does not have event key");
		}
		else {
			JSONArray arrayEv = jo.getJSONArray("events");
			for(int i= 0; i < arrayEv.length(); i++) {
			ts.addEvent(fe.createInstance(arrayEv.getJSONObject(i)));
			}
		}	
	}
	public void run(int n, OutputStream out) {
		JSONObject report = new JSONObject();
		JSONArray status = new JSONArray();
		PrintStream p = new PrintStream(out);
		//primero guardo la info del report en las n posicones del arrat 
		//luego meto todo el array en el JSON con la info status
		for(int i = 0; i < n; i++) {
			ts.advance();
			status.put(i, ts.report());
		}
		report.put("states", status);
		
		p.println(report.toString(4));
		
	}
	public void run(int n) {
		//implementar
		for(int i=0;i<n;i++){
			ts.advance();
		}
	}
	public void reset() {
		ts.reset();
	}
	public List<Vehicle> getVehicles() {
		return ts.getVehicles();
	}
	public List<Road> getRoads(){
		return ts.getRoads();
	}
	public int getTicks() {
		return ts.getTicks();
	}
	//___________________________________________
	public void addObserver(TrafficSimObserver o) {
		ts.addObserver(o);
	}
	public void removeObserver(TrafficSimObserver o) {
		ts.removeObserver(o);
	}
	public void addEvent(Event e) {
		ts.addEvent(e);
	}
}
