package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int maxSpeed,  int co2Limit, int length, Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	Road createRoad(RoadMap map) {
		Junction srcJunction = map.getJunction(this.srcJun);
		Junction desJunction = map.getJunction(this.destJunc);
		this.src = srcJunction;
		this.des = desJunction;
		return new IntercityRoad(this.id, srcJunction, desJunction, this.maxSpeed, this.co2Limit, this.length,
				this.weather);
	}

}
