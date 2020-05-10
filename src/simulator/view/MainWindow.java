package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		//tables
		EventsTable eT = new EventsTable(_ctrl);
		eT.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eT);
		
		//JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		//eventsView.setPreferredSize(new Dimension(500, 200));
		//tablesPanel.add(eventsView);
		//VEHICLE TABLE_________________
		
		VehiclesTable vT = new VehiclesTable(_ctrl);
		vT.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vT);
		/*JPanel vehicleView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Events");
		vehicleView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehicleView);*/
		
		
		//ROAD TABLE_________________
		RoadsTable rT = new RoadsTable(_ctrl);
		rT.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(rT );
		
		/*
		JPanel roadView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Events");
		roadView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadView);*/
		
		//JUNCTION TABLE_________________
		JunctionsTable jT = new JunctionsTable(_ctrl);
		jT.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(jT);
		
		/*JPanel junctionView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Events");
		junctionView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionView);*/
		
	
		//MAP DEL PROFE 
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		//MAPBYROAD------------------
		
		JPanel mapView2 = createViewPanel(new MapByRoadComponent(_ctrl), "Map");
		mapView2.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView2);
		
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
//TODO add a framed border to p with title
		p.add(new JScrollPane(c));
		return p;
	}
}