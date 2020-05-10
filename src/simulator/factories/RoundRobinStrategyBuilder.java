package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data.has("timeslot")){
			int timeslot = (int) data.get("timeslot");
			return new RoundRobinStrategy(timeslot);
		}
		else {
			return new RoundRobinStrategy(1);
		}
	}
}
