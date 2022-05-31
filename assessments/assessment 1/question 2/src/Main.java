public class Main {

	public static void main(String args[]) {
		LinkRetainRemoveSet<Integer> set = new LinkRetainRemoveSet<>();

		set.add(5);
		set.add(4);
		set.add(2);
		set.add(1);
		set.add(3);
		set.add(7);
		set.add(6);

		System.out.println("Set = " + set);

		LinkRetainRemoveSet<Integer> retainSet = set.retain(2, 6);
		System.out.println("retain (2, 6)");

		System.out.println("Set = " + set);

		System.out.println("Returned Set = " + retainSet);

		set.add(1);
		set.add(6);
		set.add(7);

		System.out.println("Set = " + set);

		retainSet = set.remove(4, null);
		System.out.println("Remove(4, null)");
		System.out.println("Set = " + set);

		System.out.println("Returned Set = " + retainSet);
	}
}
