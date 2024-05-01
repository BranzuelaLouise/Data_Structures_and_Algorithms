package question1;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class MergeStage<T extends Comparable<T>> {
	private final PriorityQueue<FileUtil<T>> files;
	private double mergeProgress;

	public MergeStage(List<File> files) {
		List<FileUtil<T>> list = new ArrayList<>();
		for (File file : files) {
			FileUtil<T> fileUtil = new FileUtil<>(file);
			if (!fileUtil.isEmpty()) {
				list.add(fileUtil);
				mergeProgress = (double) list.size() / files.size();
			}
		}
		this.files = new PriorityQueue<>(list);
	}

	public double getMergeProgress() {
		return mergeProgress;
	}

	public Iterator<String> getSortedElements() {
		return new Iterator<String>() {
			@Override
			public boolean hasNext() {
				return !files.isEmpty();
			}

			@Override
			public String next() {
				if (!hasNext())
					throw new NoSuchElementException();
				FileUtil<T> head = files.poll();
				String next = head.getLine();
				if (!head.isEmpty())
					files.add(head);
				return next;
			}
		};
	}
}