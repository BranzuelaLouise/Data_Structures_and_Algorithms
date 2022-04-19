import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in); // Creating a new scanner object
		while (true) // Loop for eternity...
		{
			String input = scanner.next(); // Read the user's input as string
			Stack<Character> stack = new Stack<>(); // Creating a stack.
			int flag = 0;
			for (int i = 0; i < input.length(); i++) // Iterate through each character of the string.
			{
				char c = input.charAt(i); // Get the specified character from the user input

				switch (c) {
				case '(':
				case '{':
				case '[':
				case '<': // Fall through
					stack.push(c);
					break;
				default:
					switch (c) {
					case ')':
						if (stack.peek() == '(') // If the top of the stack is '(', then pop ')'
							stack.pop();
						else // If the pair doesn't match or doesn't have a pair, then raise a flag.
						{
							flag = 1;
							break;
						}
						break;
					case '}':
						if (stack.peek() == '{')
							stack.pop();
						else {
							flag = 1;
							break;
						}
						break;
					case ']':
						if (stack.peek() == '[')
							stack.pop();
						else {
							flag = 1;
							break;
						}
						break;
					case '>':
						if (stack.peek() == '<')
							stack.pop();
						else {
							flag = 1;
							break;
						}
						break;
					default:
						break;
					}
					break;
				}
			}
			if (flag == 1 || stack.empty() == false) // Checks if the flag is 1 or the stack isn't empty.
				System.out.println("Not evaluated successfully");
			else
				// If the flag hasn't been raised and the stack is empty, then print.
				System.out.println("Evaluated successfully");
		}

	}
}