package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit,int length,  Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		this.cuMaxSpeed = maxSpeed;
	}
	void reduceTotalContamination() {
		int x = 0;
		switch (this.weather) {
			case WINDY:
			case STORM:
				x = 10;
				break;
			default: x = 2;
				break;
		}
		this.totalCont  = this.totalCont - x;
		if(this.totalCont < 0) {
			this.totalCont = 0;
		}
	}
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return ((int)(((11.0-v.getContClass())/11.0)*this.maxSpeed));
	}
}
