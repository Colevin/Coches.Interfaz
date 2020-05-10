package simulator.model;

public class NewJunctionEvent extends Event{
	private String id;
	private LightSwitchingStrategy lighStrat;
	private DequeuingStrategy deqStrat;
	private int x;
	private int y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(time);
			this.id = id;
			this._time = time;
			this.lighStrat = lsStrategy;
			this.deqStrat = dqStrategy;
			this.x = xCoor;
			this.y = yCoor;
			}

	@Override
	void execute(RoadMap map) {
		map.addJunction(new Junction(this.id, this.lighStrat, this.deqStrat, this.x, this.y));
	}
	public String toString() {
		return "New Junction '"+ id +"'";
	}
}
