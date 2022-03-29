import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private String id;
	private int degree;
	private Node node;
	private boolean deleted;
	private List<Vertex> neighbors;
	
	public Vertex() {
		this.neighbors = new ArrayList<Vertex>();
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Vertex> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<Vertex> neighbors) {
		this.neighbors = neighbors;
	}
	
	
	
}
