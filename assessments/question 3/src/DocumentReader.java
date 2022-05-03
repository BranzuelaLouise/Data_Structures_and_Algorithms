import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class DocumentReader {

	public static void main(String[] args) {

		String fileName = "text.txt";

		String[] words = new String[0];
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			words = stream.flatMap(line -> Arrays.stream(line.split(" "))).toArray(String[]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SelfOrganizingArrayList<String> myList = new SelfOrganizingArrayList<>(3);
		for (String d : words) {
			String data = d.toLowerCase();
			if (!myList.contains(data)) {
				myList.add(data);
			}
		}
		System.out.println(myList.toString());

	}
}
