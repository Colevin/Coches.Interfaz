package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements  DequeuingStrategy {

	List<Vehicle> alList = new ArrayList<>();
	public List<Vehicle> dequeue(List<Vehicle> q) {
		for(int i = 0;i < q.size();i++) {
			alList.add(q.get(i));
		}
		return alList;
	}
	
}
