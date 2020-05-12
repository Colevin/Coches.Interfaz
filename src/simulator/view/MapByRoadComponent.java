package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _BLUE_LIGHT_COLOR = Color.BLUE;
	Color _BG_COLOR = Color.WHITE;
	private RoadMap _map;
	private Image _car;
	// el clima
	private Image sunny;
	private Image storm;
	private Image cloud;
	private Image rain;
	private Image wind;
	// las caritas
	private Image cont0;
	private Image cont1;
	private Image cont2;
	private Image cont3;
	private Image cont4;
	private Image cont5;

	MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
		setPreferredSize(new Dimension(300, 200));
	}

	private void initGUI() {
		_car = loadImage("car.png");
		sunny = loadImage("sun.png");
		storm = loadImage("storm.png");
		cloud = loadImage("cloud.png");
		rain = loadImage("rain.png");
		wind = loadImage("wind.png");
		cont0 = loadImage("cont_0.png");
		cont1 = loadImage("cont_1.png");
		cont2 = loadImage("cont_2.png");
		cont3 = loadImage("cont_3.png");
		cont4 = loadImage("cont_4.png");
		cont5 = loadImage("cont_5.png");
	}

	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics2D g) {
		drawRoads(g);
	}

	private void drawRoads(Graphics2D g) {
		int i = 0;
		for (Road r : _map.getRoads()) {

			int x1 = 50;
			int y = (i + 1) * 50;
			int x2 = getWidth() - 100;
			int B = r.roadLenght();
			g.setColor(Color.BLACK);
			g.drawString(r.getId(), x1 - 20, y);
			g.setColor(Color.BLACK);
			g.drawLine(x1, y, x2, y);
			// llamada a dibujar la junction
			drawJunctions(g, r, x1, y, x2);
			// llamada a dibujar los coches
			drawVehicles(g, r, x1, y, x2, B);
			// calcular el clima
			switch (r.getWeather()) {
			case SUNNY:
				g.drawImage(sunny, x2 + 20, y - 18, 32, 32, this);
				break;

			case STORM:
				g.drawImage(storm, x2 + 20, y - 18, 32, 32, this);
				break;

			case RAINY:
				g.drawImage(rain, x2 + 20, y - 18, 32, 32, this);
				break;

			case CLOUDY:
				g.drawImage(cloud, x2 + 20, y - 18, 32, 32, this);
				break;
			case WINDY:
				g.drawImage(wind, x2 + 20, y - 18, 32, 32, this);
				break;
			}
			// calcular la contaminacioon
			int C = (int) Math
					.floor(Math.min((double) r.getTotalCO2() / (1.0 + (double) r.getContLimit()), 1.0) / 0.19);
			switch (C) {
			case 0:
				g.drawImage(cont0, x2 + 60, y - 18, 32, 32, this);
				break;

			case 1:
				g.drawImage(cont1, x2 + 60, y - 18, 32, 32, this);
				break;
			case 2:
				g.drawImage(cont2, x2 + 60, y - 18, 32, 32, this);
				break;
			case 3:
				g.drawImage(cont3, x2 + 60, y - 18, 32, 32, this);
				break;
			case 4:
				g.drawImage(cont4, x2 + 60, y - 18, 32, 32, this);
				break;
			case 5:
				g.drawImage(cont5, x2 + 60, y - 18, 32, 32, this);
				break;
			}
			i++;
		}

	}

	private void drawJunctions(Graphics2D g, Road r, int x1, int y, int x2) {
		Color _JUNCTION_COLOR;

		// draw a circle with center at (x,y) with radius _JRADIUS
		g.setColor(_BLUE_LIGHT_COLOR);
		g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		// draw the circle at the end of the road
		if (r.getDest().getGreenLightIndex() == 1) {
			_JUNCTION_COLOR = _GREEN_LIGHT_COLOR;
		} else {
			_JUNCTION_COLOR = _RED_LIGHT_COLOR;
		}
		g.setColor(Color.RED);
		g.drawString(r.getSrc().getId(), x1 - _JRADIUS / 2, y - 10);
		g.drawString(r.getDest().getId(), x2 - _JRADIUS / 2, y - 10);

		g.setColor(_JUNCTION_COLOR);
		g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

		// draw the junction's identifier at (x,y).

	}

	private void drawVehicles(Graphics2D g, Road r, int x1, int y, int x2, int B) {
		for (Vehicle v : r.getListVehicleRoad()) {
				if (v.getStatus() != VehicleStatus.ARRIVED) {
					int x = 0;
					x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) B));

					g.drawImage(_car, x, y -10, 16, 16, this);
					g.drawString(v.getId(), x, y - 6);
				}
			}
		}

	private void updatePrefferedSize() {
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

}
