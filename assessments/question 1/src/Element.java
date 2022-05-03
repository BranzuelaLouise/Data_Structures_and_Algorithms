import java.util.ArrayList;
import java.util.List;

public class Element implements Runnable {
	private List<Element> neighbours = new ArrayList<Element>();
	private double currentTemp;
	private double heatConstant;
	private boolean stopRequested;

	public Element(double startTemp, double heatConstant) {
		currentTemp = startTemp;
		this.heatConstant = heatConstant;
		stopRequested = false;
	}

	public void start() {
		int totalNeighbourTemperature = 0;
		for (Element neighbor : neighbours) {
			totalNeighbourTemperature += neighbor.getTemperature();
		}
		currentTemp += ((totalNeighbourTemperature / neighbours.size()) - currentTemp) * heatConstant;
	}

	public double getTemperature() {
		return currentTemp;
	}

	public void requestStop() {
		stopRequested = true;
	}

	@Override
	public void run() {
		stopRequested = false;
		while (currentTemp > 0) {
			try {
				for (Element neighbour : neighbours) {
					neighbour.start();
				}
				Thread.sleep(100);
				System.out.println("Element Temp: " + currentTemp);
			} catch (InterruptedException e) {
			}
		}
	}

	public void addNeighbour(Element element) {
		neighbours.add(element);
	}

	public void applyTempToElement(double appliedTemp) {
		currentTemp += (appliedTemp - currentTemp) * heatConstant;
	}

	public static void main(String[] args) {
		Element element1 = new Element(100, 0.5);
		Element element2 = new Element(0, 0.5);

		Thread thread = new Thread(element1);
		Thread thread2 = new Thread(element2);
		thread.start();
		thread2.start();

	}
}
