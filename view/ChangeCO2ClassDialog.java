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
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	protected int status;
	private Controller controller;
	
	public ChangeCO2ClassDialog(Frame parent, Controller controller) {
		super(parent, false);
		this.controller = controller;
		initGUI();
	}
	
	private JTextArea text;
	private JLabel Vehicles;
	private JLabel CO2;
	private JLabel ticks;
	private JSpinner tickSpin;
	private String idVehicle;
	private int newContClass;
	private int whatTime;
	private Event newEvent;
	
	private JComboBox<String> listVehicles;
	
	private JComboBox<Integer> listCO;
	private Integer Co2Pos[] = {0,1,2,3,4,5,6,7,8,9,10};
	
	private void initGUI() {
		String vehiclesRoad[] = new String[this.controller.getVehicles().size()];
		int i = 0;
		for(Vehicle v: this.controller.getVehicles()) {
			vehiclesRoad[i] = v.getId();
			i++;
		}
		
		setTitle("Change C02 Class");
		JPanel mainPanel = new JPanel(new BorderLayout());

		
		//Texto
		this.text = new JTextArea(5, 30);
		this.text.setEditable(false);
		this.text.setText("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now");
		//ChangeCO2ClassDialog.add(this.text, BorderLayout.CENTER);
		
		
		//Selectors part
		
		this.Vehicles = new JLabel("Vehicles: ");
		this.listVehicles = new JComboBox<String>(vehiclesRoad); 
		listVehicles.setSelectedIndex(0);
		listVehicles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idVehicle = listVehicles.getSelectedItem().toString();
			}	
		});
		
		
		this.CO2 = new JLabel("CO2 Class: ");
		this.listCO = new JComboBox<Integer>(Co2Pos); 
		listCO.setSelectedIndex(0);
		listCO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newContClass = Integer.valueOf(listCO.getSelectedItem().toString());	
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
				ChangeCO2ClassDialog.this.setVisible(false);	
			}
		});
		//OK button
		buttonsPanel.add(cancelButton);
		JButton OKButton = new JButton("OK");
		OKButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<Pair<String, Integer>> ws = new ArrayList<>();
					Pair<String, Integer> theEvent = new Pair<>(idVehicle, newContClass);
					ws.add(theEvent);
					status = 1;
					whatTime = controller.getTicks() + Integer.parseInt(tickSpin.getValue().toString());
					controller.addEvent(new NewSetContClassEvent(whatTime, ws));
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			});
		buttonsPanel.add(OKButton);

		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		setContentPane(mainPanel);
		setMinimumSize(new Dimension(100, 100));
		setVisible(false);
	}

}
