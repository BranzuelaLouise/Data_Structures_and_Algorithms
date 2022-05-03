import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HotPlateGUI extends JPanel implements MouseMotionListener, MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private final Timer timer;
	private final int ELEMENT_COLUMNS = 20;
	private final int ELEMENT_ROWS = 20;
	private Color colour;
	private static final int DRAW_PANEL_HEIGHT = 600;
	private static final int DRAW_PANEL_WIDTH = 600;
	private static final int temp_MIN = 0;
	private static final int temp_MAX = 1000;
	private static final int temp_INIT = 500;
	private static final int heatConstant_MIN = 1;
	private static final int heatConstant_MAX = 100;
	private static final int heatConstant_INIT = 50;
	private double temp;
	private double heatConstant;
	private final DrawPanel drawPanel;
	private final Element[][] elements = new Element[ELEMENT_COLUMNS][ELEMENT_ROWS];

	public HotPlateGUI() {
		super(new BorderLayout());
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException
				| UnsupportedLookAndFeelException e) {
		}

		temp = temp_INIT;
		heatConstant = (double) heatConstant_INIT / 100;
		drawPanel = new DrawPanel();

		JPanel panel = new JPanel();
		JLabel tempSliderLabel = new JLabel("Temperature");
		JSlider tempSlider = new JSlider(temp_MIN, temp_MAX);
		tempSlider.setValue(temp_INIT);
		tempSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				temp = tempSlider.getValue();
			}
		});
		JPanel tempSliderPanel = new JPanel();
		tempSliderPanel.add(tempSliderLabel, BorderLayout.NORTH);
		tempSliderPanel.add(tempSlider, BorderLayout.SOUTH);

		JSlider heatSlider = new JSlider(heatConstant_MIN, heatConstant_MAX);
		JLabel heatSliderLabel = new JLabel("Heat Constant");
		heatSlider.setValue(heatConstant_INIT);
		heatSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				heatConstant = (double) heatSlider.getValue() / 100;
				drawPanel.intializeElements();
			}
		});
		JPanel heatSliderPanel = new JPanel();
		heatSliderPanel.add(heatSliderLabel, BorderLayout.NORTH);
		heatSliderPanel.add(heatSlider, BorderLayout.SOUTH);

		super.add(drawPanel, BorderLayout.NORTH);
		super.add(tempSliderPanel, BorderLayout.CENTER);
		super.add(heatSliderPanel, BorderLayout.SOUTH);

		addMouseListener(this);
		addMouseMotionListener(this);

		this.timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawPanel.revalidate();
				drawPanel.repaint();
			}
		});
	}

	private boolean isOnMap(int x, int y) {
		return x >= 0 && y >= 0 && x < ELEMENT_COLUMNS && y < ELEMENT_ROWS;
	}

	public class DrawPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public DrawPanel() {
			super.setPreferredSize(new Dimension(DRAW_PANEL_WIDTH, DRAW_PANEL_HEIGHT));
			super.setBackground(Color.red);
			intializeElements();
		}

		public void intializeElements() {
			for (int y = 0; y < ELEMENT_COLUMNS; y++) {
				for (int x = 0; x < ELEMENT_ROWS; x++) {
					elements[x][y] = new Element(0, heatConstant);
				}
			}

			for (int y = 0; y < ELEMENT_COLUMNS; y++) {
				for (int x = 0; x < ELEMENT_ROWS; x++) {
					List<Point> neighbors = this.getNeighborsOfPoint(x, y);
					for (Point neighbor : neighbors) {
						int neighborX = (int) neighbor.getX();
						int neighborY = (int) neighbor.getY();
						elements[x][y].addNeighbour(elements[neighborX][neighborY]);
					}
				}
			}
		}

		private List<Point> getNeighborsOfPoint(int x, int y) {
			List<Point> neighbors = new ArrayList<Point>();
			for (int xx = -1; xx <= 1; xx++) {
				for (int yy = -1; yy <= 1; yy++) {
					if (xx == 0 && yy == 0) {
						continue;
					}
					if (isOnMap(x + xx, y + yy)) {
						neighbors.add(new Point(x + xx, y + yy));
					}
				}
			}
			return neighbors;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int width = getWidth() / ELEMENT_ROWS;
			int height = getHeight() / ELEMENT_COLUMNS;

			for (int y = 0; y < ELEMENT_COLUMNS; y++) {
				for (int x = 0; x < ELEMENT_ROWS; x++) {
					int currentElementTemp = Math.abs((int) elements[x][y].getTemperature());

					if (currentElementTemp > 255) {
						currentElementTemp = 255;
					} else if (currentElementTemp < 0) {
						currentElementTemp = 0;
					}

					int red = currentElementTemp;
					int blue = 255 - red;
					g.setColor(new Color(red, 0, blue));
					g.fillRect(x * width + 1, y * height + 1, width - 1, height - 1);
				}
			}
		}
	}

	private Point getCurrentMouseGridPosition(MouseEvent e) {
		int width = drawPanel.getWidth() / ELEMENT_ROWS;
		int height = drawPanel.getHeight() / ELEMENT_COLUMNS;
		int x = (int) e.getX() / width;
		int y = (int) e.getY() / height;
		return new Point(x, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point mousePointer = getCurrentMouseGridPosition(e);
		if (isOnMap(mousePointer.x, mousePointer.y)) {
			Element element = elements[mousePointer.x][mousePointer.y];
			element.applyTempToElement(temp);
			Thread thread = new Thread(element);
			thread.start();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (int y = 0; y < ELEMENT_COLUMNS; y++) {
			for (int x = 0; x < ELEMENT_ROWS; x++) {
				elements[x][y].requestStop();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.timer.start();
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point mousePointer = getCurrentMouseGridPosition(e);
		if (isOnMap(mousePointer.x, mousePointer.y)) {
			Element element = elements[mousePointer.x][mousePointer.y];
			element.applyTempToElement(temp);
			Thread thread = new Thread(element);
			thread.start();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		JFrame window = new JFrame("Hotplate");
		window.setSize(DRAW_PANEL_WIDTH + 101, DRAW_PANEL_HEIGHT + 110);

		HotPlateGUI panel = new HotPlateGUI();
		window.add(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null); // pack frame
		window.setVisible(true); // show the frame
	}
}
