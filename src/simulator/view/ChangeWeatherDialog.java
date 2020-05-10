	package simulator.view;

	import java.awt.BorderLayout;
	import java.awt.Dimension;
	import java.awt.FlowLayout;
	import java.awt.Frame;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JDialog;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.JSpinner;
	import javax.swing.JTextArea;
	import javax.swing.SpinnerNumberModel;

	import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
	import simulator.model.Road;
	import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
	import simulator.model.Weather;


public class ChangeWeatherDialog extends JDialog{

		private static final long serialVersionUID = 1L;
		protected int status;
		private Controller controller;
		
		public ChangeWeatherDialog (Frame parent, Controller controller) {
			super(parent, false);
			this.controller = controller;
			initGUI();
		}
		
		private JTextArea text;
		private JLabel roads;
		private JLabel weather;
		private JLabel ticks;
		private JSpinner tickSpin;
		private String idRoad;
		private Weather idWeather;
		private int whatTime;
		private Event newEvent;
		
		
		private JComboBox<String> listRoads;
		
		private JComboBox<String> listWeather;
		private String weatherList[] = {"SUNNY" , "CLOUDY", "RAINY", "WINDY", "STORM"};
		
		private void initGUI() {
			String roads[] = new String[this.controller.getRoads().size()];
			int i = 0;
			for(Road r: this.controller.getRoads()) {
				roads[i] = r.getId();
				i++;
			}
			
			setTitle("Change Road Weather");
			JPanel mainPanel = new JPanel(new BorderLayout());

			
			//Texto
			this.text = new JTextArea(5, 30);
			this.text.setEditable(false);
			this.text.setText("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now");
			//ChangeCO2ClassDialog.add(this.text, BorderLayout.CENTER);
			
			
			//Selectors part
			
			this.roads = new JLabel("Road: ");
			this.listRoads = new JComboBox<String>(roads); 
			listRoads.setSelectedIndex(0);
			listRoads.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					idRoad = listRoads.getSelectedItem().toString();
				}	
			});
			
			
			this.weather = new JLabel("Weather: ");
			this.listWeather = new JComboBox<String>(weatherList); 
			listWeather.setSelectedIndex(0);
			listWeather.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					idWeather = Weather.valueOf(listRoads.getSelectedItem().toString());	
				}	
			});
			
			
			//Ticks
			this.ticks = new JLabel("Ticks: ");
			this.tickSpin = new JSpinner();
	
			//Buttons Panel
			
			JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

			//Cancel Button
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					status = 0;
					ChangeWeatherDialog.this.setVisible(false);
				}
			});
			buttonsPanel.add(cancelButton);
			//OK button
			JButton OKButton = new JButton("OK");
			OKButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<Pair<String, Weather>> ws = new ArrayList<>();
					Pair<String, Weather> theEvent = new Pair<>(idRoad, idWeather);
					ws.add(theEvent);
					status = 1;
					whatTime = controller.getTicks() + Integer.parseInt(tickSpin.getValue().toString());
					controller.addEvent(new SetWeatherEvent(whatTime, ws));
					ChangeWeatherDialog.this.setVisible(false);
				}
			});
			buttonsPanel.add(OKButton);

			mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
			setContentPane(mainPanel);
			setMinimumSize(new Dimension(100, 100));
			setVisible(false);
		}

}
