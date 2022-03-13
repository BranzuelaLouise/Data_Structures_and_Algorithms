
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author sehall
 */
public class BallGui extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final Timer timer;
	private final JButton addButton, stopButton, add100, removeBall;
	private final DrawPanel drawPanel;
	private final List<Ball> balls;

	public BallGui() {
		super(new BorderLayout());
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException
				| UnsupportedLookAndFeelException e) {
		}

		balls = new ArrayList<>();
		drawPanel = new DrawPanel();
		addButton = new JButton("Add ball");
		stopButton = new JButton("Stop ");
		add100 = new JButton("Add 100");
		removeBall = new JButton("Remove ball");
		addButton.addActionListener(this);
		stopButton.addActionListener(this);
		add100.addActionListener(this);
		removeBall.addActionListener(this);

		JPanel panel = new JPanel();
		panel.add(addButton);
		panel.add(stopButton);
		panel.add(add100);
		panel.add(removeBall);

		super.add(panel, BorderLayout.SOUTH);
		super.add(drawPanel, BorderLayout.CENTER);

		timer = new Timer(20, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == addButton) {
			Ball ball = new Ball();
			Thread thread = new Thread(ball);
			thread.start();
			balls.add(ball);
		}
		if (source == stopButton) {
			for (Ball ball : balls) {
				ball.requestStop();
			}
		}
		if (source == timer) {
			drawPanel.repaint();
		}
		if (source == add100) {
			for (int i = 0; i < 100; i++) {
				Ball ball = new Ball();
				Thread thread = new Thread(ball);
				thread.start();
				balls.add(ball);
			}

		}
		if (source == removeBall) {
			if (balls.size() > 0) { // Check if there's a ball in list
				balls.get(0).requestStop();
				balls.remove(0);
			}
		}
	}

	public class DrawPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public DrawPanel() {
			super.setPreferredSize(new Dimension(600, 600));
			super.setBackground(Color.WHITE);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Ball.WORLD_W = getWidth();
			Ball.WORLD_H = getHeight();

			for (Ball ball : balls) {
				ball.drawBall(g);
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("THE BALL BOUNCER");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BallGui());
		frame.pack();
		frame.setLocationRelativeTo(null); // pack frame
		frame.setVisible(true); // show the frame

	}
}
