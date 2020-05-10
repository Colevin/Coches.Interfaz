package simulator.factories;

import org.json.JSONObject;

public interface Factory<T> {
	T createInstance(JSONObject info);
}
