package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{
	private static final long serialVersionUID = 1L;
	
	private JLabel time;
	private JLabel eventAdd;
	
	StatusBar(Controller ctrl){
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		this.time = new JLabel("Time: ");
		this.add(time);
		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.eventAdd = new JLabel("Event Added");
		this.add(eventAdd);
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.time.setText("Time: "+ time);
		this.eventAdd.setText("Welcolme!");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.time.setText("Time: "+ time);
		this.eventAdd.setText("Event Added  ( " + e + " )");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.time.setText("Time: "+ time);
		this.eventAdd.setText("Welcolme!");	
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.time.setText("Time: "+ time);
		this.eventAdd.setText("Welcolme!");	
	}

	@Override
	public void onError(String err) {
		this.time.setText("Time: "+ time);
		this.eventAdd.setText("Welcolme!");	
	}

}
