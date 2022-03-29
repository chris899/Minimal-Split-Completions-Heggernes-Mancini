

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Grafoi{
	private List<String> vert = new ArrayList<String>();
	private List<Pair<String, String>> edges = new ArrayList<Pair<String, String>>();
	private int maxCliqueSize = -1;
	private HashMap<String, Integer> vertexDegrees = new HashMap<String, Integer>();
	private HashMap<String, List<String>> neighborsOfVertex = new HashMap<String, List<String>>();
	private HashMap<String, List<Pair<String, String>>> incidentEdgesOfVertex = new HashMap<String, List<Pair<String, String>>>();

	public Grafoi() {
		
	}
	

	public boolean containsEdge(Pair<String, String> e) {
		boolean b = false;
		if(edgeExists(e)) {
			b = true;
		}
		return b;	
	}
	private boolean edgeExists(Pair<String, String> e) {
		boolean b = false;
		for(Pair<String, String> edge : edges) {
			if((e.getSource().equals(edge.getSource()) || e.getSource().equals(edge.getTarget())) && (e.getTarget().equals(edge.getSource()) || e.getTarget().equals(edge.getTarget()))) {
				 b = true;
			}
		}
		return b;
	}
	public boolean containsEdge(String u, String v) {
		boolean b = false;
		for(Pair<String, String> e : edges) {
			if((e.getSource().equals(u) && e.getTarget().equals(v)) || (e.getSource().equals(v) && e.getTarget().equals(u))) {
				b = true;
			}
		}
		return b;
		
	}
	public void addVertex(String u) {
		vert.add(u);
	}
	
	public void removeVertex(String v) {
		vert.remove(v);
	}
	
	public void addEdge(Pair<String,String> e) {
		edges.add(e);
	}
	
	public void addEdge(String u, String v) {
		Pair<String,String> e = new Pair<String,String>(u,v);
		edges.add(e);
	}
	
	public void removeEdge(Pair<String, String> e) {
		edges.remove(e);
	}
	
	public List<String> vertexList() {
		return vert;
	}
	
	public List<Pair<String, String>> edgeList(){
		return edges;
	}
		
	public int degreeOf(String v) {
		return vertexDegrees.get(v);
	}
	
	@SuppressWarnings("unchecked")
	public boolean isSplit() {
		List<Integer> degrees = new ArrayList<Integer>();
		List<Integer>[] sortedVertexesbyDegree = new ArrayList[vert.size()];
		for (int i = 0; i < sortedVertexesbyDegree.length; ++i) {
			sortedVertexesbyDegree[i] = new ArrayList<Integer>();
		}
		int d;
		int pointer = vert.size()-1;
		for(String s : vert) {
			d = degreeOf(s);
			sortedVertexesbyDegree[d].add(0, d);
			if(pointer > d) {
				pointer = d;
			}
		}
		int i = 1;
		int sum1 = 0;
		int sum2 = 0;
	
		while(pointer < sortedVertexesbyDegree.length){
			degrees.add(0, sortedVertexesbyDegree[pointer].get(0));
			sortedVertexesbyDegree[pointer].remove(0);
			while(sortedVertexesbyDegree[pointer].isEmpty()) {
				pointer++;
				if(pointer == sortedVertexesbyDegree.length) break;
			}
		}
		for(Integer de : degrees) {
			if(de < i - 1) {
				break;
			}else {
				i++;
			}
		}
		i = i-1;
		for(int k = 0; k<i; k++) {
			sum1 = sum1 + degrees.get(k);	
		}
		for(int k = i; k<degrees.size(); k++) {
			sum2 = sum2 + degrees.get(k);
		}
		sum2 = sum2 + i*(i-1);
		if(sum1==sum2) {
			maxCliqueSize = i;
			return true;
		}else {
			return false;
		}
	}
	
	public List<String> getNeighborsListOf(String v){
		return neighborsOfVertex.get(v);
	}
	
	public void printEdgeList() {
		for(Pair<String,String> e : edges) {
			e.print();
		}
	}
	
	public int getMaxCliqueSize() {
		return maxCliqueSize;
	}
	public void setMaxCliqueSize(int maxCliqueSize) {
		this.maxCliqueSize = maxCliqueSize;
	}

	public void increaseDegreeOfVertex(String v) {
		vertexDegrees.put(v, vertexDegrees.get(v) + 1);
	}
	
	public void increaseNeighborsOfVertex(String u, String v) {
			neighborsOfVertex.get(u).add(v);		
			neighborsOfVertex.get(v).add(u);		
	}
	
	
	public void initializeNeigborsMap() {
		for(String s: vert) {
			List<String> n = new ArrayList<String>();
			neighborsOfVertex.put(s, n);
		}	
	}
	
	public void initializeIncidenceMap() {
		for(String s: vert) {
			List<Pair<String, String>> n = new ArrayList<Pair<String,String>>();
			incidentEdgesOfVertex.put(s, n);
		}	
	}
	
	
	public void increaseIncidenceMapOfVertex(String v, Pair<String, String> edge) {
		incidentEdgesOfVertex.get(v).add(edge);
	}


	public HashMap<String, List<Pair<String, String>>> getIncidentEdgesOfVertex() {
		return incidentEdgesOfVertex;
	}


	public void setIncidentEdgesOfVertex(HashMap<String, List<Pair<String, String>>> incidentEdgesOfVertex) {
		this.incidentEdgesOfVertex = incidentEdgesOfVertex;
	}


	public void initializeDegreeMap() {
		for(String s: vert) {
			vertexDegrees.put(s, 0);
		}	
	}
	
}
