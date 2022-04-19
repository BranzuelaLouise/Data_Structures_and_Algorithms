import java.awt.Graphics;

public class Element implements Runnable {

	public Element() {

	}

	private void move() {

	}

	@Override
	public void run() {

	}

	public void drawRectangle(Graphics g) {
		g.setColor(colour);
		g.fillRect(x - (size / 2), y - (size / 2), size, size); // Setting center of the circle as its collision hitbox
	}

}
