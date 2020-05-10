package simulator.factories;


import simulator.model.NewCityRoadEvent;
import simulator.model.NewRoadEvent;


public class NewCityRoadEventBuilder extends NewRoadEventBuilder {
	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}
	@Override
	protected NewRoadEvent createRoad() {
		return new NewCityRoadEvent(this.time, this.id, this.jsrc, this.dsrc, this.maxSpeed, this.co2limit, this.length, this.weather);
	}

}
