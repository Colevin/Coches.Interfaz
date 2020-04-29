package simulator.model;

public class IntercityRoad extends Road {
	IntercityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		this.cuMaxSpeed = maxSpeed;
	}

	void reduceTotalContamination() {
		int x = 0;
		switch (this.weather) {
		case SUNNY:
			x = 2;
			break;
		case CLOUDY:
			x = 3;
			break;
		case RAINY:
			x = 10;
			break;
		case WINDY:
			x = 15;
			break;
		case STORM:
			x = 20;
			break;
		}
		this.totalCont = (int) ((100.0 - x) / 100.0 * this.totalCont);
	}

	void updateSpeedLimit() {
		if (this.totalCont > this.contLimit) {
			this.cuMaxSpeed = (int) (this.maxSpeed * 0.5);
		} else {
			this.cuMaxSpeed = this.maxSpeed;
		}

	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int vehicleSpeed = this.cuMaxSpeed;
		if (this.weather == Weather.STORM) {
			vehicleSpeed = (int) (this.cuMaxSpeed * 0.8);
		}
		return vehicleSpeed;
	}

}
