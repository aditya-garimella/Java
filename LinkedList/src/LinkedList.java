public class LinkedList{

public Node head;

//Insert at first position
public void insertFirst(int data){
	Node toBeInserted = new Node(data);
		this.head = toBeInserted;
		toBeInserted.setNext(this.head);
		this.head = toBeInserted;
	
}

//Insert at last position
public void insertLast(int data){
	Node toBeInserted = new Node(data);
	if(head == null){
		this.head = toBeInserted;
	}else{
		Node current = head;
		while( current.getNext() != null ){
			current = current.getNext();
		}
		current.setNext(toBeInserted);
	}
}

//Insert at nth position
public void insert(int data, int position){
	Node toBeInserted = new Node(data);
	Node current = head;
	if( position < 0 || position > this.size()){
		System.out.println("Position shouldn't be greater then size: "+this.size());
		return;
	}
	if( position == 0){
		toBeInserted.setNext(head);
		head = toBeInserted;
		return;
	}
	for(int i=0; i < position-1; i++){
		current = current.getNext();
	}
	toBeInserted.setNext(current.getNext());
	current.setNext(toBeInserted);
}

//Delete first item of list
public void deleteFirst(){
	if( head == null ){
		System.out.println("This is an empty list");
	}else{
		head = head.getNext();
	}
}

//Delete last item of list
public void deleteLast(){
	if( head == null ){
		System.out.println("This is an empty list");
	}else if( head.getNext() == null){
		head = null;
	}else{
		Node current = head;
		while( current.getNext().getNext() != null ){
			current = current.getNext();
		}
		current.setNext(current.getNext().getNext());
	}
	
}

//Delete item at nth position
public void delete(int position){
	if( position < 0 || position >= this.size()){
		System.out.println("The position should be greater than or equla to 0 and less than : "+this.size() );
	}else{
		Node current = head;
		for( int i=0; i < position; i++){
			current = current.getNext();
		}
		current.setNext(current.getNext().getNext());
	}
}

//Find if an element is present
public boolean find( int data){
	if( this.head == null){
		return false;
	}else{
		Node current =  head;
		while( current != null ){
			if( current.getData() == data ){
				return true;
			}
		}
	}
	return false;
}

//Get size of the linked list
public int size(){
	int size = 0;
	Node current = head;
	while( current != null){
		size++;
		current = current.getNext();
	}
	return size;
}

//Print all the elements of the linked list
public void printLinkedList(){
	Node current = head;
	if( head == null){
		System.out.println("Empty List");
		return;
	}
	while(current != null){
		System.out.print(current.getData() + ", ");
		current = current.getNext();
	}
}


















}