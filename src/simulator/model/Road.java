package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exception.contRoadNegException;
import exception.weatherRoadNullException;

public abstract class Road extends SimulatedObject {
	protected Junction srcJunc;
	protected Junction destJunc;	
	protected int length;
	protected int maxSpeed;
	protected int contLimit;
	protected int cuMaxSpeed;
	protected Weather weather;
	protected int totalCont;
	protected List <Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
		this.vehicles = new ArrayList<>(); 
		//preguntar a Samir si así mejor
		this.srcJunc.addOutGoingRoad(this);
		this.destJunc.addIncommingRoad(this);
	}
	abstract void reduceTotalContamination(); //
	abstract void updateSpeedLimit(); //
	abstract int calculateVehicleSpeed(Vehicle v); //
	
	void enter(Vehicle v) {
		vehicles.add(v);
	}
	void exit(Vehicle v) {
		vehicles.remove(v);
	}
	public void setWeather(Weather w) {
		if(w != null) {
			this.weather = w;
		}
		else {
			throw new weatherRoadNullException("Weather is null");
		}
	}
	void addContamination(int c){
		if(c>= 0) {
			this.totalCont += c;
		}
		else {
			throw new contRoadNegException("No negative co2 values are possible");
		}
	}
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
 		for (Vehicle v: vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
 		}
			vehicles.sort((v1, v2)->{
				if(v1.getLocation() == v2.getLocation()) {
					return 0;
				}
				else if(v1.getLocation() > v2.getLocation()){
					return 1;
				}
				return 0;
			});
 		
	}
	public JSONObject report() throws JSONException {
			JSONObject rep = new JSONObject();
			JSONArray vehiclesArray = new JSONArray();
			rep.put("speedlimit", this.cuMaxSpeed);
			rep.put("co2", this.totalCont);
			rep.put("weather", this.weather);
			for(Vehicle v: this.vehicles) {
				vehiclesArray.put(v.getId());
			}
			rep.put("vehicles", vehiclesArray);
			rep.put("id", this._id);
			return rep;
	}
	public Junction getDest() {
		return this.destJunc;
	}
	public int roadLenght() {
		return this.length;
	}
	public Weather getWeather() {
		return this.weather;
	}
	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	public Junction getSrc() {
		return this.srcJunc;
	}
	public double getTotalCO2() {
		return this.totalCont;
	}
	public double getContLimit() {
		return this.contLimit;
	}
	public double getLength() {
		return this.length;
	}
	public List<Vehicle> getListVehicleRoad(){
		return this.vehicles;
	}
}
