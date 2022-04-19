import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball implements Runnable {

	public static int WORLD_W, WORLD_H; // The ball container's width and height
	private int x, y; // Ball coordinates
	private int minX, maxX, minY, maxY; // Bounds of the ball's container
	private int size; // Ball size
	private int dx, dy; // Ball direction
	private Color colour; // Ball colour
	private boolean stopRequested; // Flag for stop requested
	private Random gen;

	public Ball() {
		// Container's bounds
		minX = 0;
		minY = 0;
		maxX = WORLD_W - 1;
		maxY = WORLD_H - 1;

		gen = new Random();
		colour = new Color(gen.nextFloat(), gen.nextFloat(), gen.nextFloat());
		x = gen.nextInt(maxX - minX + 1); // Randomises the spawn of the ball in the x axis
		y = gen.nextInt(maxY - minY + 1); // Randomises the spawn of the ball in the y axis
		size = gen.nextInt(50 - 15) + 15; // Random size with minimum 10 so that it doesn't spawn pixel sized balls
		dx = gen.nextInt(25 + 25) - 25; // Random direction for the x axis
		dy = gen.nextInt(25 + 25) - 25; // Random direction for the y axis
		if (dx == 0 && dy == 0) { // If the value of x and y are 0, hard code the direction
			dx = 2;
			dy = 1;
		}

	}

	private void move() {
		// Ball's minimum coordinates before collision with the container
		float radius = size / 2;
		float ballMinX = minX + radius;
		float ballMinY = minY + radius;
		float ballMaxX = maxX - radius;
		float ballMaxY = maxY - radius;

		x += dx;
		y += dy;

		// Check if it crosses the x-axis bounds
		if (x < ballMinX) { // The minimum X coordinate collision detection
			dx = -dx; // Bounce along the normal
			x = (int) ballMinX; // Re-position the ball at the x-axis
		} else if (x > ballMaxX) { // The maximum X coordinate collision detection
			dx = -dx;
			x = (int) ballMaxX;
		}

		// Check if it crosses the y-axis bounds
		if (y < ballMinY) { // The minimum Y coordinate collision detection
			dy = -dy; // Bounce along the normal
			y = (int) ballMinY; // Re-position the ball at the y-axis
		} else if (y > ballMaxY) { // The maximum Y coordinate collision detection
			dy = -dy;
			y = (int) ballMaxY;
		}

	}

	@Override
	public void run() {
		stopRequested = false;
		while (!stopRequested) {
			move();
			try {
				Thread.sleep(gen.nextInt(50 - 20) + 20); // Frame rate or speed of the ball
			} catch (InterruptedException e) {
			}
		}

	}

	public void requestStop() {
		stopRequested = true;
	}

	public void drawBall(Graphics g) {
		g.setColor(colour);
		g.fillOval(x - (size / 2), y - (size / 2), size, size); // Setting center of the circle as its collision hitbox
	}

}
