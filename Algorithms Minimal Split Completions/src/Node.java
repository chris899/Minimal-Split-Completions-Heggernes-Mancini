

public class Node {
	private String data;
	private Vertex vertex;
	private Edge edge2;
	private Integer position;
	private Pair<String,String> edge;
	private DoubleDegreeList fillEdgeList;
	private Node pointer; // pointer to copy of fill edge
	private Node pointerLast;
	private Node pointerToVertexNode;
	private int degreeArrayIndex;
	private int d;
	Node prev;
	Node next;
	public Node(Node prev, String data, Node next) {
		this.prev = prev;
		this.data = data;
		this.next = next;
		fillEdgeList = new DoubleDegreeList();
	}
	
	public Node() {
	}
	public Node(Node prev, int data, Node next) {
		this.prev = prev;
		this.d = data;
		this.next = next;
		fillEdgeList = new DoubleDegreeList();
	}
	
	public Node(Node prev, Vertex data, Node next) {
		this.prev = prev;
		this.vertex = data;
		this.next = next;
		fillEdgeList = new DoubleDegreeList();
	}
	
	public Node(Node prev, Edge data, Node next) {
		this.prev = prev;
		this.edge2 = data;
		this.next = next;
	}
	
	public Pair<String, String> getEdge() {
		return edge;
	}

	public void setEdge(Pair<String, String> edge) {
		this.edge = edge;
	}

	public Node(Node prev, Pair<String,String> data, Node next) {
		this.prev = prev;
		this.edge = data;
		this.next = next;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public DoubleDegreeList getFillEdgeList() {
		return fillEdgeList;
	}

	public void setFillEdgeList(DoubleDegreeList fillEdgeList) {
		this.fillEdgeList = fillEdgeList;
	}
	
	public Node getPointer() {
		return pointer;
	}

	public void setPointer(Node pointer) {
		this.pointer = pointer;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public Edge getEdge2() {
		return edge2;
	}

	public void setEdge2(Edge edge2) {
		this.edge2 = edge2;
	}

	public Node getPointerLast() {
		return pointerLast;
	}

	public void setPointerLast(Node pointerLast) {
		this.pointerLast = pointerLast;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Node getPointerToVertexNode() {
		return pointerToVertexNode;
	}

	public void setPointerToVertexNode(Node pointerToVertexNode) {
		this.pointerToVertexNode = pointerToVertexNode;
	}
	
	public void removeFromFillEdgeList(Node node) {
		fillEdgeList.removeNode(node);
	}

	public int getDegreeArrayIndex() {
		return degreeArrayIndex;
	}

	public void setDegreeArrayIndex(int degreeArrayIndex) {
		this.degreeArrayIndex = degreeArrayIndex;
	}
	
	public void clearPointers() {
		next = null;
		prev = null;
	}
}