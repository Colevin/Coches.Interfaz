package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{
	private List<Pair<String,Integer>> cs;
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		if(cs == null) {
			throw new IllegalArgumentException();
			}
		else {
			this.cs = cs;
			}
		}
	@Override
	void execute(RoadMap map) {
		if(!cs.isEmpty()) { 
		for(Pair<String, Integer> p: cs) {
			map.getVehicle(p.getFirst()).setContamination(p.getSecond());
		}
	}
		else throw new IllegalArgumentException("The contamination class is empty");
	}
	public String toString() {
		for(Pair<String,Integer> pair: cs) {
		return "New ContClass '"+ pair.getFirst() + pair.getSecond() + "'";
		}
	}
	
}
