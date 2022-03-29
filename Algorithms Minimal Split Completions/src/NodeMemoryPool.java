public class NodeMemoryPool {
	private Node [] nodes = null;
	private int nextAvailableNode = 0;
	
	public NodeMemoryPool(int numNodes) {
		if (numNodes <= 0) { 
			throw new IllegalArgumentException("numNodes <= 0");
		}
		
		nodes = new Node[numNodes];
		
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = new Node();
		}
		nextAvailableNode = 0;
	}
	
	public Node newNode() {
		if (nextAvailableNode <= nodes.length - 1) {
			return nodes[nextAvailableNode++];
		}
		return new Node();
	}
}
