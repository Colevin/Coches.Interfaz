package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	private Controller ctrl;
	private boolean stop;

	// Componentes
	private JToolBar toolBar;
	private JButton loadButton;
	private JButton co2ClassButton;
	private JButton weatherButton;
	private JButton runButton;
	private JButton stopButton;
	private JLabel ticksLabel;
	private JSpinner ticksSpinner;
	
	private JButton exitButton;

	ControlPanel(Controller ctrl) {
		super(new BorderLayout());
		this.ctrl = ctrl;
		stop = true;
		initGui();
		ctrl.addObserver(this);
	}

	private void initGui() {
		createToolbarComp();
	}

	private void createToolbarComp() {
		this.toolBar = new JToolBar();
		this.add(toolBar, BorderLayout.PAGE_START);

		// Archivos
		this.loadButton = new JButton();
		loadButton.setActionCommand("load");
		loadButton.setToolTipText("Load the file");
		loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileSelector = new JFileChooser();
				int choice = fileSelector.showOpenDialog(loadButton);
				if (choice == JFileChooser.APPROVE_OPTION) {
					ctrl.reset();
				}
				// Excepcioones
			}
		});
		
		
		this.co2ClassButton = new JButton();
		co2ClassButton.setActionCommand("change cont class");
		co2ClassButton.setToolTipText("change to different contamination class");
		co2ClassButton.setIcon(new ImageIcon("resources/icons/co2class.png"));
		co2ClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame window = new JFrame();

			}
		});
		this.weatherButton = new JButton();
		weatherButton.setActionCommand("change weather of the road");
		weatherButton.setToolTipText("change to different weather");
		weatherButton.setIcon(new ImageIcon("resources/icons/weather.png"));
		weatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame window = new JFrame();

			}
		});
		//RUN BUTTON 
		
		
		
		
		//______________________________
		this.stopButton = new JButton();
		stopButton.setActionCommand("stop");
		stopButton.setToolTipText("Stop the simulation");
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop = true;
			}
		});

		this.exitButton = new JButton();
		exitButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		exitButton.setActionCommand("exit");
		exitButton.setToolTipText("This will close the simulation");
		exitButton.setIcon(new ImageIcon("resources/icon/exit.png"));
		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Object[] choice = { "yes", "no", "cancel" };
				int op = JOptionPane.showOptionDialog(exitButton, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choice, choice[1]);
				if (op == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		this.ticksLabel = new JLabel("Ticks: ");
		SpinnerNumberModel stepsSpinnerModel = new SpinnerNumberModel(0,0,1000000,500);
		this.ticksSpinner = new JSpinner(stepsSpinnerModel);
		this.ticksSpinner.setPreferredSize(new Dimension(65,30)); //ESTA POR VER
		this.ticksSpinner.setMaximumSize(new Dimension(65,30)); 
		
		
		toolBar.add(this.loadButton);
		toolBar.addSeparator();
		toolBar.add(this.co2ClassButton);
		toolBar.addSeparator();
		toolBar.add(this.weatherButton);
		toolBar.addSeparator();
		toolBar.add(this.runButton);
		toolBar.add(this.stopButton);
		toolBar.addSeparator();
		toolBar.add(this.ticksLabel);
		toolBar.add(this.ticksSpinner);
		toolBar.addSeparator();
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(this.exitButton);
	}

	private void shutDownButtons() {
		this.stopButton.setEnabled(true);
		this.loadButton.setEnabled(false);
		this.runButton.setEnabled(false);
		this.co2ClassButton.setEnabled(false);
		this.weatherButton.setEnabled(false);
	}

	private void turnUpButtons() {
		this.stopButton.setEnabled(true);
		this.loadButton.setEnabled(true);
		this.runButton.setEnabled(true);
		this.co2ClassButton.setEnabled(true);
		this.weatherButton.setEnabled(true);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
