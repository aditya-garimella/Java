
public class ExecuteLinkedList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.printLinkedList();
		list.insertLast(9);
		list.insertLast(8);
		list.insertLast(7);
		list.insertLast(6);
		list.insertLast(5);
		list.insertLast(4);
		list.insertLast(3);
		//list.insert(11, 7);
		//list.insert(12, 9);
		list.printLinkedList();
	}

}
