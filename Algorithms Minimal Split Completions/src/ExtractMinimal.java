

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;


public class ExtractMinimal {
	private Grafoi g;
	private  Grafoi h;
	private  Grafoi m;
	private  Grafoi tempo;
	private boolean minimal;
	private int j;
	private int sumLeft;
	private int sumRight;
	private List<Pair<String,String>> f = new ArrayList<Pair<String,String>>();
	private List<Pair<String,String>> f_ = new ArrayList<Pair<String,String>>();
	private HashMap<Integer, Boolean> degree = new HashMap<Integer, Boolean>();
	private DoubleDegreeList[] degreeArray;
	private DoubleDegreeList nonIncreasingDegreeList;
	private Node[] arrayPointers;
	private List<Integer>[] sortedVertexesbyDegree;
	private int minimumIndex;
	Graph graph;
	private long start2;
	private long end2;
	int deletededges = 0;
	public ExtractMinimal() {
		this.g = new Grafoi();
		this.h = new Grafoi();
		this.tempo = new Grafoi();
	}
	
	public void run() throws NumberFormatException, IOException {
		//input();
		testInput(5000, 0.05,0.7, 0.4);	
		if(!h.isSplit()) {
			JOptionPane.showMessageDialog(null, 
                    "The graph H is not split with the given fill edges. You have to give fillEdges that make H a split graph", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
		m  = h;
		f_ = f;
		//System.out.println("to g exei tosa edges: " + g.edgeList().size());
		//System.out.println("to h exei tosa edges: " + h.edgeList().size());
		List<Pair<String,String>> empty = new ArrayList<Pair<String,String>>();
		constructDegrees();
		minimal = false;
		constructDegreeArray(f_);
		while(minimal == false && minimumIndex < degreeArray.length) {
			if (minimumIndex < j) {	
				Node minimumVertexNode = degreeArray[minimumIndex].getNode();
				Vertex minimumVertex = minimumVertexNode.getVertex();
				Node minimumEdgeNode = degreeArray[minimumIndex].getNode().getFillEdgeList().getNode();
				Edge minimumEdge = minimumEdgeNode.getEdge2();
				Node copyMinimumVertexNode = minimumEdgeNode.getPointer().getPointerToVertexNode();

				
				// delete minimum fill edge f and its copy
				deleteMinimumFillEdgeAndItsCopy(minimumEdgeNode);
				
				deleteOrMoveVertexNodeAfterFillEdgeRemoval(minimumVertexNode, minimumVertexNode);

				deleteOrMoveVertexNodeAfterFillEdgeRemoval(copyMinimumVertexNode, minimumVertexNode);

				
				updateMinimumIndex();
				// find the new max clique size
				updateJ(minimumEdge);
			} else {
				minimal = true;
			}				
		}
		end2 = System.currentTimeMillis();
		System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
		//System.out.print("M Edges = {");
		//m.printEdgeList();
		//System.out.print("}");	
		System.out.println();
		/*for(Edge edge : graph.getEdges2().keySet()) {
			System.out.print(edge.getFirstEndpoint().getId() + "-" + edge.getSecondEndpoint().getId() + ", ");
		}*/
		System.out.println("to f' exei tosa edges: " + f_.size());
		//System.out.println("to g exei tosa edges: " + g.edgeList().size());
		System.out.println("to h exei tosa edges: " + h.edgeList().size());
		//System.out.println("to graph exei tosa edges: " + graph.getEdges2().size());
		int total =  h.edgeList().size()-f_.size();
		//System.out.println("total " + total);
		System.out.println("deleted edges " + deletededges);
		System.out.println("g edges: " + total);
		int left = f_.size() - deletededges;
		System.out.println("fill edges left " + left);



	}
	
	private void updateMinimumIndex() {
		int startScanIndex = minimumIndex - 1;
		
		while (startScanIndex < degreeArray.length && degreeArray[startScanIndex].isEmpty()) {
			startScanIndex++;
		}
		
		minimumIndex = startScanIndex;
	}
	
	private void deleteOrMoveVertexNodeAfterFillEdgeRemoval(Node vertexNode, Node minimumNode) {
		int remainingFillEdgesCount = vertexNode.getFillEdgeList().getSize();
		
		if (remainingFillEdgesCount == 0) {
			degreeArray[vertexNode.getDegreeArrayIndex()].removeNode(vertexNode);
		} else {
			degreeArray[vertexNode.getDegreeArrayIndex()].removeNode(vertexNode);
			degreeArray[vertexNode.getDegreeArrayIndex() - 1].addFront(vertexNode);
			vertexNode.setDegreeArrayIndex(vertexNode.getDegreeArrayIndex() - 1);
		}
	}

	private void deleteMinimumFillEdgeAndItsCopy(Node minimumEdgeNode) {
		deletededges++;
		deleteFillEdgeNode(minimumEdgeNode);
	}

	@SuppressWarnings({ })
	private void deleteFillEdgeNode(Node minimumEdgeNode) {	
		graph.deleteEdge(minimumEdgeNode.getEdge2());
		minimumEdgeNode.getPointerToVertexNode().removeFromFillEdgeList(minimumEdgeNode);
		
		Node copyFillEdge = minimumEdgeNode.getPointer();
		copyFillEdge.getPointerToVertexNode().removeFromFillEdgeList(copyFillEdge);
	}
	
	private void updateJ(Edge edge) {
		Vertex u = edge.getFirstEndpoint();
		Vertex v = edge.getSecondEndpoint();
		int d1 = edge.getFirstEndpoint().getDegree();
		int d2 = edge.getSecondEndpoint().getDegree();
		if(d1 == d2) {
			arrayPointers[d1].getPointerLast().setD(d1 - 1);
			arrayPointers[d1].getPointerLast().getPrev().setD(d1 - 1 );
			if((arrayPointers[d1].getPosition() == j && arrayPointers[d1].getPointerLast().getD() < j-1)) {
				updateSumRight(d1-1);
				updateSumLeft(d1+1);
				j--;
			}else {
				UpdateSums(d1);
				UpdateSums(d2);
			}
			if(arrayPointers[d1-1].getPointerLast() == null) {
				arrayPointers[d1-1].setPointerLast(arrayPointers[d1].getPointerLast());
				arrayPointers[d1-1].setPosition(arrayPointers[d1].getPosition());
			}
			if(arrayPointers[d1].getPointerLast().getPrev().getPrev() == null || arrayPointers[d1].getPointerLast().getPrev().getPrev().getD() != d1) {
				arrayPointers[d1].setPointerLast(null);
				arrayPointers[d1].setPosition(null);
			}else {
				arrayPointers[d1].setPosition(arrayPointers[d1].getPosition()-2);
				arrayPointers[d1].setPointerLast(arrayPointers[d1].getPointerLast().getPrev().getPrev());
			}
			
		}else {
			updateUpdate(d1);
			updateUpdate(d2);
		}
		u.setDegree(u.getDegree()-1);
		v.setDegree(v.getDegree()-1);
	}
	
	private void UpdateSums(int d) {
		if(arrayPointers[d].getPosition() > j) {
			sumRight--;
		}else if(arrayPointers[d].getPosition() <= j) {
			sumLeft--;
		}
		
	}

	private void updateUpdate(int i) {
		arrayPointers[i].getPointerLast().setD(i-1);
		if((arrayPointers[i].getPosition() == j && arrayPointers[i].getPointerLast().getD() < j - 1)) {
			updateSumRight(i-1);
			updateSumLeft(i);
			j--;
		}
		else {
			UpdateSums(i);
		}
		if(arrayPointers[i-1].getPointerLast() == null) { 
			arrayPointers[i-1].setPointerLast(arrayPointers[i].getPointerLast());
			arrayPointers[i-1].setPosition(arrayPointers[i].getPosition());
		}	
		if(arrayPointers[i].getPointerLast().getPrev() == null || arrayPointers[i].getPointerLast().getPrev().getD() != i) {
			arrayPointers[i].setPointerLast(null);
			arrayPointers[i].setPosition(null);
		}else if(arrayPointers[i].getPointerLast().getPrev().getD() == i){
			arrayPointers[i].setPosition(arrayPointers[i].getPosition() - 1);
			arrayPointers[i].setPointerLast(arrayPointers[i].getPointerLast().getPrev());
		}
		
		
	}
	
	private void updateSumRight(int d) {
		sumRight = sumRight + d;	
	}
	
	private void updateSumLeft(int d) {
		sumLeft = sumLeft - d;	
	}
	
	private boolean isSplit() {
		return (sumLeft == j * (j-1) + sumRight);
	}
	// vrika ta degrees kai ekana thn list L se nonincreasing order kathws kai to array me ta pointer
	@SuppressWarnings("unchecked")
	private void constructDegrees() {
		arrayPointers = new Node[m.vertexList().size()];
		sortedVertexesbyDegree = new ArrayList[m.vertexList().size()];
		List<Integer> degrees = new ArrayList<Integer>();
		nonIncreasingDegreeList = new DoubleDegreeList();
		int d;
		for(int i = 0; i<arrayPointers.length; i++) {
			arrayPointers[i] = new Node();
		}
		for (int i = 0; i < sortedVertexesbyDegree.length; ++i) {
			sortedVertexesbyDegree[i] = new ArrayList<Integer>();
		}
		int pointer = m.vertexList().size()-1;
		for(String s : m.vertexList()) {
			d = m.degreeOf(s);
			sortedVertexesbyDegree[d].add(d);
			if(pointer > d) {
				pointer = d;
			}
		}
		int i = 1;
		int x = 0;
		sumLeft = 0;
		sumRight = 0;
		while(pointer < sortedVertexesbyDegree.length) {
			degrees.add(0,pointer);
			nonIncreasingDegreeList.addFront(pointer);
			if(!degree.containsKey(pointer)) {
				degree.put(pointer, true);
				arrayPointers[pointer].setPointerLast(nonIncreasingDegreeList.getNode()); // pointerLast
				arrayPointers[pointer].setPosition(m.vertexList().size() - x);
			}
			x++;
			sortedVertexesbyDegree[pointer].remove(0);
			while(sortedVertexesbyDegree[pointer].isEmpty()) {
				pointer++;
				if(pointer>=sortedVertexesbyDegree.length) break;
			}
			
		}
		for(Integer di : degrees) {
			if(di < i - 1) {
				break;
			}else {
				i++;
			}
		}
		j = i - 1;
		i = i - 1;
		for(int k = 0; k<i; k++) {
			sumLeft = sumLeft + degrees.get(k);		
		}
		for(int k = i; k<degrees.size(); k++) {
			sumRight = sumRight + degrees.get(k);
		}
	}

	
	private void constructDegreeArray(List<Pair<String,String>> fillEdgesInput) {
		Graph graph = Graph.from(h, fillEdgesInput);
		
		// use a memory pool for fill edge nodes because they are much too many compared to vertex nodes
		NodeMemoryPool nodeMemoryPool = getNodeMemoryPool(2*graph.getFillEdges().size());
				
		start2 = System.currentTimeMillis();
		DoubleDegreeList [] degreeArray = new DoubleDegreeList[graph.getVertices().size()];
		
		for (int i = 0; i < degreeArray.length; ++i) {
			degreeArray[i] = new DoubleDegreeList();
		}

		// for each file edge
		List<Edge> fillEdges = graph.getFillEdges();
		int minimumIndex = graph.getVertices().size();
		
		
		
		for (Edge fillEdge : fillEdges) {
			Vertex u = fillEdge.getFirstEndpoint();
			Vertex v = fillEdge.getSecondEndpoint();
			Node uNode = addVertexinDegreeArray(degreeArray, u);
			Node vNode = addVertexinDegreeArray(degreeArray, v);
			
			Node uFillEdgeNode = addFillEdgeInVertexNode(uNode, fillEdge, nodeMemoryPool);
			Node vFillEdgeNode = addFillEdgeInVertexNode(vNode, fillEdge, nodeMemoryPool);
			
			linkFillEdges(uFillEdgeNode, vFillEdgeNode);
			
			minimumIndex = updateMinimumIndex(minimumIndex, u, v);
		}
		
		this.degreeArray = degreeArray;
		this.minimumIndex = minimumIndex;
		this.graph = graph;
	}
	
	private NodeMemoryPool getNodeMemoryPool(int i) {
		// TODO Auto-generated method stub
		NodeMemoryPool nodeMemoryPool = new NodeMemoryPool(i);
		return nodeMemoryPool;
	}

	private Node addVertexinDegreeArray(DoubleDegreeList[] degreeArray, Vertex vertex) {
		Node node = null;
		
		// check if already inserted
		if (vertex.getNode() != null) {
			return vertex.getNode();
		}
		
		node = degreeArray[vertex.getDegree()].addFront(vertex);
		vertex.setNode(node);
		node.setDegreeArrayIndex(vertex.getDegree());
		return node;
	}

	private Node addFillEdgeInVertexNode(Node node, Edge fillEdge, NodeMemoryPool nodeMemoryPool) {
		Node newNode = node.getFillEdgeList().addFront(fillEdge, nodeMemoryPool);
		newNode.setPointerToVertexNode(node);
		return newNode;
	}

	private void linkFillEdges(Node uFillEdgeNode, Node vFillEdgeNode) {
		uFillEdgeNode.setPointer(vFillEdgeNode);
		vFillEdgeNode.setPointer(uFillEdgeNode);
	}

	private int updateMinimumIndex(int minimumIndex, Vertex u, Vertex v) {
		return Math.min(minimumIndex, Math.min(u.getDegree(), v.getDegree()));
	}

	private void input() throws NumberFormatException, IOException{
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Chris\\Desktop\\ExtractMinimal Input.txt"))) {
		    String line;
		    int j = 0;
		    int vertices = Integer.parseInt(br.readLine());
		    for(int i = 1; i <= vertices; i++) {
		    	g.addVertex("" + i);
		    	h.addVertex("" + i);
		    }
		    int numEdges = Integer.parseInt(br.readLine());
		    h.initializeDegreeMap();
		    g.initializeDegreeMap();
		    String u;
		    String v;
		     while ((line = br.readLine()) != null) {
		    	 String[] s = line.split(",");
		    	 u = s[0];
				 v = s[1];
				 Pair<String,String> edge = new Pair<String, String>(u,v);
				 g.addEdge(edge);
				 h.addEdge(edge);
				 h.increaseDegreeOfVertex(u);
				 h.increaseDegreeOfVertex(v);
				 g.increaseDegreeOfVertex(u);
				 g.increaseDegreeOfVertex(v);
				 j++;
				 if(j == numEdges) break;
		     }
		    
		    while ((line = br.readLine()) != null) {
		    	String[] s = line.split(",");
		    	u = s[0];
		    	v = s[1];
		    	Pair<String,String> edge = new Pair<String, String>(u,v);
				f.add(edge);
				h.addEdge(edge);
				h.increaseDegreeOfVertex(u);
				h.increaseDegreeOfVertex(v);				
		    }
		}
	}
	
	private void testInput(int numberOfVertices, double p,double pp, double deleteEdges){
		List<String> K = new ArrayList<String>();
		List<String> I = new ArrayList<String>();
		    for(int i = 1; i <= numberOfVertices; i++) {
		    	if(StdRandom.bernoulli(p)) {
		    		I.add("" + i);
		    	}else {
		    		K.add("" + i);
		    	}
		    	h.addVertex("" + i);
		    	tempo.addVertex("" + i);
		    	g.addVertex("" + i);
		    }
		    System.out.println("K size =" + K.size());
		    h.initializeDegreeMap();
		    tempo.initializeDegreeMap();
		    g.initializeDegreeMap();
		    for(int i = 0; i<K.size() - 1; i++) {
				for(int j = i+1; j<K.size(); j++) {
					String u = K.get(i);
					String v = K.get(j);
					Pair<String,String> edge = new Pair<String, String>(u,v);
					h.addEdge(edge);
					tempo.addEdge(edge);
					h.increaseDegreeOfVertex(u);
					h.increaseDegreeOfVertex(v);
					tempo.increaseDegreeOfVertex(u);
					tempo.increaseDegreeOfVertex(v);
				
				}
		    }
		    for(int i = 0; i<I.size(); i++) {
				for(int j = i+1; j<K.size(); j++) {
					if(StdRandom.bernoulli(pp)) {
						String u = I.get(i);
						String v = K.get(j);
						Pair<String,String> edge = new Pair<String, String>(u,v);
						h.addEdge(edge);
						tempo.addEdge(edge);
						h.increaseDegreeOfVertex(u);
						h.increaseDegreeOfVertex(v);
						tempo.increaseDegreeOfVertex(u);
						tempo.increaseDegreeOfVertex(v);
					}
				}
		    }
		    List<Pair<String,String>> temp2 = new ArrayList<Pair<String,String>>();
			for(Pair<String, String> e : h.edgeList()) {
				if(StdRandom.bernoulli(deleteEdges)) {
					temp2.add(e);
					f.add(e);
					
				}
			}
	}
	
	private void printF() {
		for(Pair<String,String> e : f_) {
			System.out.print(e.getSource()+ "->" + e.getTarget() + ", ");
		}
		System.out.println();
	}

	
}
