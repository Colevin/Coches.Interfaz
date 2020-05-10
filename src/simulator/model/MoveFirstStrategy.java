package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> onlyFirst = new ArrayList<>();
		if(q.size()>0) {
			onlyFirst.add(q.get(0));
		}
		return onlyFirst;
	}
}
