import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball implements Runnable {

	public static int WORLD_W, WORLD_H; // Box width and height
	private int x, y; // Ball coordinates
	private int minX, maxX, minY, maxY; // Bounds of the ball's box
	private int size; // Ball size
	private int dx, dy; // Ball speed
	private Color colour; // Ball colour
	private boolean stopRequested; // Flag for stop requested
	private Random gen;

	public Ball() {
		x = WORLD_W / 2;
		y = WORLD_H / 2;
		// Container's bounds
		minX = 0;
		minY = 0;
		maxX = WORLD_W - 1;
		maxY = WORLD_H - 1;

		gen = new Random();
		colour = new Color(gen.nextFloat(), gen.nextFloat(), gen.nextFloat());
		size = 20;
		Random xDirection = new Random();
		Random yDirection = new Random();
		dx = xDirection.nextInt(10 + 10) - 10; // Random direction for the x axis
		dy = yDirection.nextInt(5 + 5) - 5; // Random direction for the y axis
		if (dx == 0 && dy == 0) { // If the value of x and y are 0, hard code the direction
			dx = 2;
			dy = 1;
		}

	}

	private void move() {
		// Ball's allowed bounding coordinates before collision
		float radius = size / 2;
		float ballMinX = minX + radius; // Ball's minimum x coordinate before collision
		float ballMinY = minY + radius; // Ball's minimum y coordinate before collision
		float ballMaxX = maxX - radius; // Ball's maximum x coordinate before collision
		float ballMaxY = maxY - radius; // Ball's maximum y coordinate before collision

		x += dx;
		y += dy;

		// Check if it crosses the x-axis bounds
		if (x < ballMinX) { // The minimum X coordinate collision detection
			dx = -dx; // Bounce in the opposite x direction
			x = (int) ballMinX; // Re-position the ball at the x-axis
		} else if (x > ballMaxX) { // The maximum X coordinate collision detection
			dx = -dx;
			x = (int) ballMaxX;
		}

		// Check if it crosses the y-axis bounds
		if (y < ballMinY) { // The minimum Y coordinate collision detection
			dy = -dy; // Bounce in the opposite y direction
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
				Random speed = new Random();
				Thread.sleep(speed.nextInt(100)); // Randomises the speed of the ball
			} catch (InterruptedException e) {
			}
		}

	}

	public void requestStop() {
		stopRequested = true;
	}

	public void drawBall(Graphics g) {
		g.setColor(colour);
		g.fillOval(x - (size / 2), y - (size / 2), size, size); // Using the center of the circle as a coordinate
	}

}
