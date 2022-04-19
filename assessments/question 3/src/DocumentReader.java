import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

public class DocumentReader {

	public static void main(String[] args) {

		String fileName = "text.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}

		SelfOrganizingArrayList<Integer> myList = new SelfOrganizingArrayList<Integer>(3);
		myList.add(4);
		myList.add(3);
		myList.add(2);

		Iterator<Integer> myIterator = myList.iterator();

		System.out.println(myList.size());
		myList.contains(4);
		System.out.println(myList.toString());

	}
}
