package simulator.factories;

import simulator.model.NewInterCityRoadEvent;
import simulator.model.NewRoadEvent;


public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder{
	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}
	protected NewRoadEvent createRoad() {
		return new NewInterCityRoadEvent(this.time, this.id, this.jsrc, this.dsrc, this.maxSpeed, this.co2limit, this.length, this.weather);
	}

}
