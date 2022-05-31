import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class FileQueue {
    private PriorityQueue queue;
    private FileSorter current;

    public FileQueue() {
        List<FileSorter> list = new ArrayList<>();
        queue = new PriorityQueue(list);
    }

    public void enqueueFile (FileSorter f) {
        queue.add(f);
    }

    public void startNext () {
        Object o = queue.peek();
        if (o != null) {
            FileSorter f = (FileSorter) queue.poll();
            Thread thread = new Thread(f);
            thread.start();
            current = f;
        }
    }

    public int getQueueSize () {
        return queue.size();
    }

    public FileSorter getCurrentProcessing() {
        return current;
    }
}
