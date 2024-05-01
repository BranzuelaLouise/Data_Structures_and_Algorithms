package question1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FileSorterGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final Timer timer;
	private final JProgressBar mergeProgress;

	public FileSorterGUI() {
		super(new BorderLayout());
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException
				| UnsupportedLookAndFeelException e) {
		}

		JPanel panel = new JPanel();

		super.add(panel, BorderLayout.SOUTH);
		super.setPreferredSize(new Dimension(600, 600));

		timer = new Timer(20, this);
		this.mergeProgress = new JProgressBar();
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mergeProgress.setValue((int) FileSorter.getSplitProgress());
		System.out.println(FileSorter.getSplitProgress());
		if (FileSorter.getSplitProgress() == 100.0) {
			mergeProgress.setValue((int) FileSorter.getMergeProgress());
			System.out.println(FileSorter.getMergeProgress());
		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("File Sorter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new FileSorterGUI());
		frame.pack();
		frame.setLocationRelativeTo(null); // pack frame
		frame.setVisible(true); // show the frame
		FileSorter f = new FileSorter(10, new File(
				"D:\\Github\\COMP610\\COMP610-Data-Structures-and-Algorithm\\assessments\\assessment 2\\cities.txt"),
				new File("citiesOutput.txt"));
		Thread thread = new Thread(f);
		thread.start();

	}
}
