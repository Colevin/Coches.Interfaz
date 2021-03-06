package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	private Controller _ctrl;
	private boolean _stopped;

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
		this._ctrl = ctrl;
		_stopped = true;
		initGui();
		ctrl.addObserver(this);
	}

	private void initGui() {
		createToolbarComp();
	}

	private void createToolbarComp() {
		this.toolBar = new JToolBar();
		this.add(toolBar, BorderLayout.PAGE_START);

		// Archivos _________________________________________________
		this.loadButton = new JButton();
		loadButton.setActionCommand("load");
		loadButton.setToolTipText("Load the file");
		loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileSelector = new JFileChooser();	
				fileSelector.setFileFilter(new FileNameExtensionFilter("Text file in JSON format", "txt", "json"));
				fileSelector.setCurrentDirectory(new File ("resources/examples"));
				int choice = fileSelector.showOpenDialog(loadButton);
				if (choice == JFileChooser.APPROVE_OPTION) {
					_ctrl.reset();
					try {
						_ctrl.loadEvents(new FileInputStream(fileSelector.getSelectedFile()));
					} catch(JSONException je) {
						JOptionPane.showMessageDialog(toolBar, je.getMessage(), "Error in JSON", JOptionPane.ERROR_MESSAGE);
					} catch (FileNotFoundException ex) {
						JOptionPane.showMessageDialog(toolBar, ex.getMessage(), "Error the file does not exist", JOptionPane.ERROR_MESSAGE);
					}
				}
				// Excepcioones
			}
		});
		//CO2 CLASS BUTTON ________________________________________
		this.co2ClassButton = new JButton();
		co2ClassButton.setActionCommand("change cont class");
		co2ClassButton.setToolTipText("change to different contamination class");
		co2ClassButton.setIcon(new ImageIcon("resources/icons/co2class.png"));
		co2ClassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame co2dialog = new JFrame();
				_stopped = true;
				turnUpButtons();
				ChangeCO2ClassDialog c = new ChangeCO2ClassDialog(co2dialog, _ctrl);
			}
		});
		//WEATHER BUTTON ________________________________________
		this.weatherButton = new JButton();
		weatherButton.setActionCommand("change weather of the road");
		weatherButton.setToolTipText("change to different weather");
		weatherButton.setIcon(new ImageIcon("resources/icons/weather.png"));
		weatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame window = new JFrame();
				_stopped = true;
				turnUpButtons();
				ChangeWeatherDialog w = new ChangeWeatherDialog(window, _ctrl);
			}
		});
		//run BUTTON ____________________________________________
		this.runButton = new JButton();
		runButton.setActionCommand("play");
		runButton.setToolTipText("Start the simulation");
		runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shutDownButtons();
				_stopped = false;
			try {
				int steps = Integer.parseInt(ticksSpinner.getValue().toString());
					run_sim(steps);	
			}
				catch(NumberFormatException nfe) {
					turnUpButtons();
					_stopped = true;
				}	
			}
		});

		//STOP BUTTON    ________________________________________________
		this.stopButton = new JButton();
		stopButton.setActionCommand("stop");
		stopButton.setToolTipText("Stop the simulation");
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_stopped = true;
				
			}
		});
		//EXIT BUTTON ________________________________________
		this.exitButton = new JButton();
		exitButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		exitButton.setActionCommand("exit");
		exitButton.setToolTipText("This will close the simulation");
		exitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
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
		//TICKS SPINNER BUTTON ________________________________________
		this.ticksLabel = new JLabel("Ticks: ");
		SpinnerNumberModel stepsSpinnerModel = new SpinnerNumberModel(10, 0, 1000000, 10);
		this.ticksSpinner = new JSpinner(stepsSpinnerModel);
		this.ticksSpinner.setPreferredSize(new Dimension(65, 30)); // ESTA POR VER
		this.ticksSpinner.setMaximumSize(new Dimension(65, 30));
		//TOOLBAR BUTTON ________________________________________
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

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1); 
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.toolBar, e.getMessage(), "Error in Simulator", JOptionPane.ERROR_MESSAGE);
				turnUpButtons();
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			turnUpButtons();
			_stopped = true;
		}
	}

	private void enableToolBar(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void stop() {
		_stopped = true;
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
