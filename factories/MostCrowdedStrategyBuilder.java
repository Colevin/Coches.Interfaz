package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{
	
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		JSONObject d = (JSONObject) data.get("data");
		if(d.has("timeslot")){
			int timeslot = (int) d.get("timeslot");
			return new MostCrowdedStrategy(timeslot);
		}
		else {
			return new MostCrowdedStrategy(1);
		}
	}
}
