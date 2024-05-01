package question1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileSorter implements Runnable {
	private int limit;
	private File inputFile;
	private File outputFile;
	private double splitProgress;
	private double mergeProgress;
	private long inputLines;
	private List<File> tempSortedFiles;
	private MergeStage mergeStage;

	public FileSorter(int limit, File inputFile, File outputFile) {
		this.limit = limit;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.splitProgress = 0;
		this.mergeProgress = 0;
		this.tempSortedFiles = new ArrayList<>();
		this.inputLines = 0;
	}

	@Override
	public void run() {
		try {
			BufferedReader countbr = Files.newBufferedReader(inputFile.toPath());
			inputLines = countbr.lines().count();
			BufferedReader br = Files.newBufferedReader(inputFile.toPath());
			ArrayList<String> lines = new ArrayList<String>();
			String line = br.readLine();
			int count = 0;

			while (line != null) {
				lines.add(line);
				count++;
				splitProgress = (double) count / inputLines;
				if (lines.size() == limit) {
					tempSortedFiles.add(getSortedFile(lines.toArray()));
					lines = new ArrayList<String>();
				}
				line = br.readLine();
			}

			mergeStage = new MergeStage<>(tempSortedFiles);
			Iterator<String> sortedElements = mergeStage.getSortedElements();

			try (FileWriter fw = new FileWriter(outputFile, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw)) {
				while (sortedElements.hasNext()) {
					out.println(sortedElements.next());
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public double getMergeProgress() {
		return mergeStage.getMergeProgress() * 100;
	}

	public double getSplitProgress() {
		return splitProgress * 100;
	}

	private static File newTempFile(List<String> strings) throws IOException {
		File tempFile = File.createTempFile("sorted-", ".txt");
		Files.write(tempFile.toPath(), strings);
		tempFile.deleteOnExit();
		return tempFile;
	}

	private File getSortedFile(Object[] lines) {
		Arrays.sort(lines);
		String[] stringArray = Arrays.copyOf(lines, lines.length, String[].class);
		try {
			return newTempFile(Arrays.asList(stringArray));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		FileSorter f = new FileSorter(10, new File(
				"D:\\Github\\COMP610\\COMP610-Data-Structures-and-Algorithm\\assessments\\assessment 2\\cities.txt"),
				new File("citiesOutput.txt"));
		Thread thread = new Thread(f);
		thread.start();

	}
}
