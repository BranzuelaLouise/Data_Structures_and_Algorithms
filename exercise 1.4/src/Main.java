import java.util.NoSuchElementException;

public class Main {

	public interface RandomObtainable<E> {
		public E getRandom() throws NoSuchElementException;

		public boolean removeRandom() throws UnsupportedOperationException;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
