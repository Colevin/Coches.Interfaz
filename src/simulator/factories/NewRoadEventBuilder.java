package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewRoadEvent;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event>{
	
	//a ver: 1� sabes el tipo de road_ city o inter, 
	//2� llamo a la funcion de crear evento correspondiente al tipo del road
	//3� luego llamo en la llamada a newcity road paso de los string a el tipo de cosas que necesito, aqui es Junction y el vehicle era tipo road
	//4� creo un atributo privado asi se de que tipo es el buildr que voy a usar city o inter y llamo a createRoad que no necesita el tipo
	
	NewRoadEventBuilder(String type) {
		super(type);
	}
	
	protected int time;
	protected String id;
	protected String jsrc;
	protected String dsrc;
	protected int length ;
	protected int co2limit;
	protected int maxSpeed ;
	protected Weather weather ;
	
	protected abstract NewRoadEvent createRoad();

	@Override
	protected Event createTheInstance(JSONObject data) {

		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.jsrc = data.getString("src");
		this.dsrc = data.getString("dest");
		this.length = data.getInt("length");
		this.co2limit = data.getInt("co2limit");
		this.maxSpeed = data.getInt("maxspeed");
		this.weather = Weather.valueOf(data.getString("weather"));
		
		
		return this.createRoad();
	}
}
