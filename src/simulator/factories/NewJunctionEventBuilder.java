package simulator.factories;



import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory){
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}
	protected Event createTheInstance(JSONObject data) {
		int time =  data.getInt("time");
		String id = data.getString("id");
		JSONArray coor = data.getJSONArray("coor");
		int x = coor.getInt(0);
		int y = coor.getInt(1);
		JSONObject ls_Strategy = data.getJSONObject("ls_strategy");
		LightSwitchingStrategy ls = lssFactory.createInstance(ls_Strategy);
		JSONObject dq_Strategy = data.getJSONObject("dq_strategy");
		DequeuingStrategy dq = dqsFactory.createInstance(dq_Strategy);
		
 		return new NewJunctionEvent(time, id, ls, dq, x, y);
	}

}
