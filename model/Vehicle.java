package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import exception.contClassVehicleNullException;
import exception.itinerartVehicleShortException;
import exception.maxVehicleSpeedNegativeException;
import exception.speedVehicleNegException;
import exception.statusVehicleNullException;

public class Vehicle extends SimulatedObject {
	private List<Junction> itinerary;
	private int maxSpeed;
	private int speed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int totalContamination;
	private int totalDistance;
	private int lastJunctionVisited;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary)   {
		super(id);
		if (maxSpeed > 0)
			this.maxSpeed = maxSpeed;
		else
			throw new maxVehicleSpeedNegativeException("Max Speed should be bigger than 0");

		if (0 <= contClass && contClass <= 10)
			this.contClass = contClass;
		else
			throw new contClassVehicleNullException("Contamination has an invalid value");

		if (itinerary.size() > 1)
			this.itinerary = Collections.unmodifiableList(itinerary);

		else
			throw new itinerartVehicleShortException("Itinery is not longh enough");

		this.lastJunctionVisited = 0;
		this.status = VehicleStatus.PENDING;
		this.speed = 0;

	}

	public void setSpeed(int s)   {
		if (s < 0)
			throw new speedVehicleNegException("Max Speed should be bigger than 0");
		if (this.status == VehicleStatus.TRAVELING) {
			if (s <= maxSpeed) {
				this.speed = s;
			} else {
				this.speed = maxSpeed;
			}
		}

	}

	public void setContamination(int c) {
		if (c < 0 || c > 10) {
			throw new contClassVehicleNullException("The contamination class has an invalid range");
		} else {
			this.contClass = c;
		}
	}

	public void advance(int time) {
		int partialCont = 0, iniLoc = this.location;
		if (this.status == VehicleStatus.TRAVELING) {
			if (this.location + this.speed < this.road.roadLenght()) {
				this.location = this.location + this.speed;
			} else {
				this.location = this.road.roadLenght();
			}
			this.totalDistance += this.location - iniLoc;
			partialCont = this.contClass * (this.location - iniLoc);

			this.totalContamination += partialCont;

			this.road.addContamination(partialCont);
			if (this.location == this.road.roadLenght()) {
				this.speed = 0;
				this.lastJunctionVisited++;
				this.status = VehicleStatus.WAITING;
				this.getItinerary().get(lastJunctionVisited).enter(this);
			}
		}
	}

	public void moveToNextRoad() {
		if (this.status != VehicleStatus.WAITING && this.status != VehicleStatus.PENDING) {
			throw new statusVehicleNullException("The status of the vehicle is invalid" + this);
		}
		if (this.status != VehicleStatus.PENDING && this.lastJunctionVisited != 0) {
			this.road.exit(this);
		}
		if (this.lastJunctionVisited != itinerary.size() - 1) {
			this.location = 0;
			this.status = VehicleStatus.TRAVELING;
			this.road = this.itinerary.get(this.lastJunctionVisited)
					.roadTo(this.itinerary.get(this.lastJunctionVisited + 1));
			this.road.enter(this);
		} else if (this.lastJunctionVisited == itinerary.size() - 1) {
			this.status = VehicleStatus.ARRIVED;
		}
	}

	public JSONObject report() throws JSONException {
		JSONObject rep = new JSONObject();
		rep.put("id", this._id);
		rep.put("speed", this.speed);
		rep.put("distance", this.totalDistance);
		rep.put("co2", this.totalContamination);
		rep.put("class", this.contClass);
		if (this.status == VehicleStatus.TRAVELING || this.status == VehicleStatus.WAITING) {
			rep.put("road", this.road);
			rep.put("location", this.location);
		}
		rep.put("status", this.status);

		return rep;
	}

	public int getSpeed() {
		return this.speed;
	}
	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	public int getTotalCont() {
		return this.totalContamination;
	}
	public int getDistance() {
		return this.totalDistance;
	}
	public int getContClass() {
		return this.contClass;
	}

	public Road getRoad() {
		return this.road;
	}

	public List<Junction> getItinerary() {
		return this.itinerary;
	}

	public int getLocation() {
		return this.location;
	}
	public VehicleStatus getStatus() {
		return this.status;
	}
	
}