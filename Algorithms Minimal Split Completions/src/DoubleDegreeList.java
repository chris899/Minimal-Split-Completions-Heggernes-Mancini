
public class DoubleDegreeList {
	private Node head; 	 
	private int  size;
	
	public DoubleDegreeList() {
	    this.head = null;
	    this.size = 0;
	}

	
	public void addFront(Node node) {
		node.clearPointers();
		
		if (head == null) {
			head = node;
		} else {
			head.prev = node;
			node.next = head;
			head = node;
		}
		size++;
	}
	
	public void addFront(int data) {
		if(head == null) {
			head = new Node(null, data, null);
		}else {
			Node newNode = new Node(null, data, head);
			head.prev = newNode;
			head = newNode;
		}
		size++;
	}
	
	public Node addFront(Vertex vertex) {		
		if(head == null) {
			head = new Node(null, vertex, null);
		}else {
			Node newNode = new Node(null, vertex, head);
			head.prev = newNode;
			head = newNode;
		}
		size++;
		
		return head;
	}
	
	public Node addFront(Edge fillEdge, NodeMemoryPool nodeMemoryPool) {
		Node newNode = nodeMemoryPool.newNode();
		newNode.prev = null;
		newNode.next = null;
		newNode.setEdge2(fillEdge);
		
		if(head == null) {
			head = newNode;
		}else {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		size++;
		
		return head;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
	public void removeNode(Node node) {
		if (node == head) {
			removeFront();
		} else {
			node.prev.next = node.next;
			if (node.next != null) {
				node.next.prev = node.prev;
			}
			size--;
		}
	}
	
	public void removeFront() {
		if(head == null) return;
		
		head = head.next;
		if (head != null) { // if the list had only 1 node
			head.prev = null;
		}
		size--;
	}
	public Boolean isEmpty() {
		return head == null;
	}
	
	public Node getNode() {
		return head;
	}
	
	
}
