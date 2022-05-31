import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FileUtil<T extends Comparable<T>> implements Comparable<FileUtil<T>> {
    private final Iterator<String> lines;
    private String line;

    public FileUtil(File file) {
        try {
            BufferedReader reader = Files.newBufferedReader(file.toPath());
            this.lines = reader.lines().collect(Collectors.toList()).listIterator();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public int compareTo(FileUtil<T> otherLine) {
        String thisLine = getNextLine();
        String anotherLine = otherLine.getNextLine();

        if (thisLine == null) return anotherLine == null ? 0 : -1;
        if (anotherLine == null) return 1;
        return thisLine.compareTo(anotherLine);
    }

    public String getLine() {
        String tmp = getNextLine();

        if (tmp != null) {
            line = null;
            return tmp;
        }

        throw new NoSuchElementException();
    }

    public boolean isEmpty() {
        return getNextLine() == null;
    }

    private String getNextLine() {
        if (line != null) return line;
        if (!lines.hasNext()) return null;
        return line = lines.next();
    }
}