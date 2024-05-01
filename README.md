# Overview
These are the classwork I did for my Data Structures and Algorithms class. All of the code is written in Java.

# Exercises
### 1.1
Exercise 1.1 (Drawing UML Class Diagrams) Suppose a library program
is being designed that will use classes Book, Journal, Magazine, Library,
BookIndex, Customer, Author, Librarian as well as interfaces Loanable and
RestrictedLoanable. Draw a UML class diagram which indicates some of the
relationships between the classes and interfaces

### 1.2
Exercise 1.2 (Analyzing Selection Sort Algorithm) The selection sort algorithm works by 
first finding the smallest value in a list and swapping the first value with it, then 
finding the second smallest value and swapping the second value with it, continuing 
until all the values are in order. Implement this algorithm, then determine its growth 
function, and hence the order of the algorithm.

### 1.3
Exercise 1.3 (Bouncing Balls) Design a program which displays several balls
bouncing in a box, where the movement of each ball is controlled by its own
thread (you might like to make a Ball class implementing Runnable and use
Thread.sleep(delay) in its run method to control the speed of the ball). Try
to include buttons for increasing and decreasing the number of balls that are
drawn.

# Assessment 1
### Question 1 (Hot Plate)
Create an Element class representing parts of a hotplate, each running in its thread and 
adjusting its temperature based on neighboring elements. The class has methods to add 
neighbors, start its thread, and apply temperature changes. A main/test class creates 
two neighbor Element objects with preset temperatures, running their threads to reach similar 
temperatures over time. Additionally, a HotplateGUI is designed with a central panel displaying a 
2D grid of elements, where mouse events trigger temperature adjustments based on a JSlider gauge. 
A Timer animates temperature fluctuations, visually represented by color changes from Blue to Red, 
showcasing synchronized thread interactions and interactive temperature control in the GUI.

### Question 2 (LinkedRRSet)
Extend the LinkedSet class creating a subclass called LinkRetainRemoveSet<E
extends Comparable<E>>. It overrides the add method of LinkedSet to put elements in a
natural order (i.e. using the elements compareTo method) with no duplicates. It also has
methods retain and remove for dealing with ranges of elements. Each method accepts two
parameters of type E, start and end, which specify a range of elements to retain or remove
from the set (determined by the compareTo method of the elements). If either parameter is
null the range is considered open-ended. The retain method should retain just those elements
of the set that are between start (inclusive) and end (exclusive), and return the elements that
have been removed as a set. The remove method does the opposite, retaining the elements
that are outside the specified range, and returning the elements removed as a set. The
asymptotic complexity of both retain and remove methods can be done in O(n) time. Throw
suitable exceptions if start and/or end are not in the set or any other invalid input. Include a
driver main method to test the class with many variations. 

### Question 3 (Self Organizing List)
Create a SelfOrganizingArrayList class that implements the List interface and maintains a self-organizing 
list using an underlying array, where frequently accessed items move towards the front based on an access counter. 
The class includes suitable implementations of Iterator and ListIterator, focusing on read-only navigation. 
It prohibits user code from inserting elements at specific indices. The class also handles exceptions for incorrect usage. 
Additionally, a DocumentReader class with a main method is created to instantiate the self-organizing list for word frequency 
analysis from a text file. It ensures uniqueness of strings, recognizes case variations, and prints the list with word occurrences, 
showcasing list functionality like removes and iteration.

### Question 4 (Bracket Evaluator)
Design and write code for an algorithm that uses a stack data structure to efficiently evaluate
whether opening and closing bracket and brace pairs match up inside any given string in O(n)
time, where n is the length of the string. All other content in the string can be ignored and
may use any existing stack implementation. 

# Assessment 2
### Question 1 (File Sorting)
Create a FileSorter class that sorts lines of a large text file, where the file cannot fit entirely in memory. The class constructor 
takes an integer limit for the number of lines held in memory, and input and output text File objects. The algorithm performs 
a split stage, reading lines from the input file, quicksorting them in memory within the limit, and outputting to a temporary file. 
This split stage repeats until all lines are processed. Then, a merge stage merges temporary files using a merge sort algorithm until 
a single sorted output file is created. FileSorter can run as a separate thread to keep the GUI responsive, with methods to notify progress. 
Additionally, create a FileSorterGUI class with buttons to queue and execute tasks, along with progress bars for task monitoring.

### Question 2 (Trie Data Structures)
Implement a Trie data structure, which is a tree-based data structure used for efficiently storing and searching string elements, 
such as words. Each node in the Trie represents a character, and nodes are linked to form paths representing words. Searching for a word in the Trie involves 
traversing the tree from the root based on the characters in the target word. Adding a word involves traversing and adding nodes as necessary, while ensuring 
duplicate words are not added. The Trie is also used in auto-complete algorithms by traversing and obtaining word suggestions based on prefixes. Removing elements 
from the Trie involves cleaning up redundant nodes while maintaining the structure's efficiency. Removing a word may trigger the removal of unused nodes along 
the path, recursively up the tree.

### Question 3 (Hashset With Chaining)
Create a HashSetWithChaining class that implements a set using an underlying hash table with chaining to resolve collisions. 
The class is built from scratch, including a custom hash table and Node class, without encapsulating existing Java data structures. 
The set maintains a default load factor of 75% but can also accept custom load factors and initial capacities during construction. 
Additionally, a test class with a main method is created to thoroughly test the operations of the HashSetWithChaining class, including 
modifications to the toString method to display the entire contents of the hash table with chained elements, as needed.







