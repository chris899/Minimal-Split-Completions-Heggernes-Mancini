import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
	private List<Vertex> vertices = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();
	private List<Edge> fillEdges = new ArrayList<>();
	private Map<Edge, Boolean> edges2 = new HashMap<Edge, Boolean>();
	private Set<Pair<String, String>> fillEdges2 = new HashSet<Pair<String, String>>();
	private Map<String, Vertex> vertexIdMap;
	
	/*public static Graph random_simple(int numberOfVertices, double p) {
		Graph graph = new Graph();
		
		// the vertices will have ids 0, ..., N-1
		Map<String, Vertex> idToVertexMap = new HashMap<>();
		
		for (int i = 0; i < numberOfVertices; ++i) {
			String id = i;
			
			Vertex vertex = new Vertex();
			vertex.setId(id);
		}
		
		return graph;
	}*/
	public Graph() {
		
	}
	public static Graph from(Grafoi g, List<Pair<String,String>> fillEdgesInput) {
		Graph graph = new Graph();
		Map<String, Vertex> vertexIdMap = new HashMap<>();
		
		for (String id : g.vertexList()) {
			Vertex vertex = new Vertex();
			
			vertex.setId(id);
			vertex.setDegree(g.degreeOf(id));
			vertex.setNeighbors(new ArrayList<Vertex>());
			graph.vertices.add(vertex);
			vertexIdMap.put(id, vertex);
		}
		
		for (Pair<String,String> edgePair : g.edgeList()) {
			Edge edge = new Edge();
			
			edge.setFirstEndpoint(vertexIdMap.get(edgePair.getSource()));
			edge.setSecondEndpoint(vertexIdMap.get(edgePair.getTarget()));
			
			graph.edges.add(edge);
			graph.edges2.put(edge, true);
			graph.fillEdges2.add(edgePair);
			vertexIdMap.get(edgePair.getSource()).getNeighbors().add(vertexIdMap.get(edgePair.getTarget()));
			vertexIdMap.get(edgePair.getTarget()).getNeighbors().add(vertexIdMap.get(edgePair.getSource()));
			
		}
		
		for (Pair<String,String> edgePair : fillEdgesInput) {
			Edge edge = new Edge();
			edge.setFirstEndpoint(vertexIdMap.get(edgePair.getSource()));
			edge.setSecondEndpoint(vertexIdMap.get(edgePair.getTarget()));
			
			graph.fillEdges.add(edge);
			//graph.edges.add(edge);
			//graph.edges2.put(edge, true);
			//vertexIdMap.get(edgePair.getSource()).getNeighbors().add(vertexIdMap.get(edgePair.getTarget()));
			//vertexIdMap.get(edgePair.getSource()).setDegree(vertexIdMap.get(edgePair.getSource()).getDegree()+1);
			//vertexIdMap.get(edgePair.getTarget()).getNeighbors().add(vertexIdMap.get(edgePair.getSource()));	
			//vertexIdMap.get(edgePair.getTarget()).setDegree(vertexIdMap.get(edgePair.getTarget()).getDegree()+1);
		}
		
		graph.vertexIdMap = vertexIdMap;
		
		return graph;
	}
	
	public void deleteEdge(Edge edge) {
		edges2.remove(edge);
	}
	public List<Vertex> getVertices() {
		return vertices;
	}


	public Set<Pair<String, String>> getFillEdges2() {
		return fillEdges2;
	}
	
	public void setFillEdges2(Set<Pair<String, String>> fillEdges2) {
		this.fillEdges2 = fillEdges2;
	}
	
	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}
	

	public Map<Edge, Boolean> getEdges2() {
		return edges2;
	}

	public void setEdges2(Map<Edge, Boolean> edges2) {
		this.edges2 = edges2;
	}

	public List<Edge> getEdges() {
		return edges;
	}


	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}


	public List<Edge> getFillEdges() {
		return fillEdges;
	}


	public void setFillEdges(List<Edge> fillEdges) {
		this.fillEdges = fillEdges;
	}
	
	public Vertex getVertexForId(String id) {
		return vertexIdMap.get(id);
	}
}
