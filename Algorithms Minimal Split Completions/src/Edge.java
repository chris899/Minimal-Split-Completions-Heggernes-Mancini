
public class Edge {
	private Vertex firstEndpoint;
	private Vertex secondEndpoint;
	
	public Edge() {
		
	}
	public Edge(Vertex firstEndpoint, Vertex secondEndpoint) {
		super();
		this.firstEndpoint = firstEndpoint;
		this.secondEndpoint = secondEndpoint;
	}
	
	public Vertex getFirstEndpoint() {
		return firstEndpoint;
	}
	public void setFirstEndpoint(Vertex firstEndpoint) {
		this.firstEndpoint = firstEndpoint;
	}
	public Vertex getSecondEndpoint() {
		return secondEndpoint;
	}
	public void setSecondEndpoint(Vertex secondEndpoint) {
		this.secondEndpoint = secondEndpoint;
	}
}
