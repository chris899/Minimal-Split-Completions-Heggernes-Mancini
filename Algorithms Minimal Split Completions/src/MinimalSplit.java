

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MinimalSplit {
	private Grafoi g;
	private Grafoi h;
	private Graph g1;
	private  ArrayList<Vertex> K;
	private  ArrayList<Vertex> I;
	private ArrayList<Pair<String,String>> f =  new ArrayList<Pair<String,String>>();
	private DoubleDegreeList[] sortedVertexesbyDegree;
	private int pointer;
	private int unmarkedVertices;
	private long start2;
	private long end2;
	int fillEdgesAdded = 0;
	public MinimalSplit() {
		this.g = new Grafoi();
		//this.h = g;		
	}

	public void run() throws NumberFormatException, IOException {
		K = new ArrayList<Vertex>();
		I = new ArrayList<Vertex>();
		//input();
		testInput(40000, 0);  
		List<Pair<String,String>> empty = new ArrayList<Pair<String,String>>();
		g1 = Graph.from(g,empty);
		System.out.println("edge size = " + g1.getEdges().size());
		sortedVertexesbyDegree = new DoubleDegreeList[g1.getVertices().size()];
		unmarkedVertices = g1.getVertices().size();
		pointer = g1.getVertices().size() - 1;
		start2 = System.currentTimeMillis();
		for (int i = 0; i < sortedVertexesbyDegree.length; ++i) {
			sortedVertexesbyDegree[i] = new DoubleDegreeList();
		}
		
		computeDegreesInIncreasingOrder();

		while(unmarkedVertices!=0) {
			/// unmarked vertex v with minimum degree in G
			Vertex v = sortedVertexesbyDegree[pointer].getNode().getVertex();
			
			//Mark and add v to I 
			MarkAndAddVertexToI(v);
			
			
			//Mark and add neighbors of v to K 
			MarkAndAddNeighborsToK(v);
			
			sortedVertexesbyDegree[pointer].removeFront();
			
			// uppdate pointer to v with minimum degree in G
			pointer = updateMinimumPointer(pointer);
		}
		end2 = System.currentTimeMillis();
		System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
		System.out.println("Elapsed Time in seconds: "+ (end2-start2)/1000);
		// adding fill edges to make K set into a clique
		//File file = new File("C:\\Users\\Chris\\Desktop\\sample.txt");
		//PrintStream stream = new PrintStream(file);
		//PrintStream temp = System.out;
		//System.setOut(stream);
		//System.out.println("K = " + K);
		System.out.println("K = " + K.size());
		//stream.close();
		//System.setOut(temp);
		//System.out.println("I = " + I);
		System.out.println("I = " + I.size());
		//System.out.print("FillEdges = [");
		//addFillEdges();	
		//System.out.print("]");
		//stream.close();
		//System.setOut(temp);
		System.out.println("edge size = " + g1.getEdges().size());
		addFillEdges2();	
		System.out.println("filledges added " + fillEdgesAdded);
		int total = fillEdgesAdded + g1.getEdges().size();
		System.out.println("total = " + total);
	}
	
	private void MarkAndAddVertexToI(Vertex v) {
		v.setDeleted(true);
		unmarkedVertices--;
		I.add(v);
		
	}

	private int updateMinimumPointer(int index) {
		if(unmarkedVertices == 0) return index;
		
		while(sortedVertexesbyDegree[index].getSize() == 0) {
			index = index + 1;
			if(index == g1.getVertices().size()) break;
		}	
		while((index < g1.getVertices().size()) && sortedVertexesbyDegree[index].getNode().getVertex().isDeleted()) {
			sortedVertexesbyDegree[index].removeFront();
			while(index < g1.getVertices().size() && sortedVertexesbyDegree[index].getSize() == 0) {
				index = index + 1;
			}				
		}
		return index;
	}
	


	private void MarkAndAddNeighborsToK(Vertex v) {
		List<Vertex> neighbors = v.getNeighbors();	
		for(Vertex n : neighbors) {
			if(!n.isDeleted()) {
				K.add(n);
				unmarkedVertices--;
				n.setDeleted(true);
			}				
		}		
	}

	public void computeDegreesInIncreasingOrder() {		
		int d;
		for(Vertex s : g1.getVertices()) {
			d = s.getDegree();
			sortedVertexesbyDegree[d].addFront(s);
			if(pointer > d) {
				pointer = d;
			}
		}	
	}

	/*private void addFillEdges() {
		for(int j = 0; j<K.size()-1; j++) {
			for(int i = j + 1; i<K.size(); i++) {
				if(!h.containsEdge(K.get(j), K.get(i))) {
					h.addEdge(K.get(j), K.get(i));
					Pair<String,String> p = new Pair<String,String>(K.get(j),K.get(i));
					f.add(p);
					System.out.print(K.get(j)+"-"+K.get(i)+", ");
				}					
			}
		}
	}*/
	private void addFillEdges2() {
		for(int j = 0; j<K.size()-1; j++) {
			for(int i = j + 1; i<K.size(); i++) {
				Pair<String, String> pair1 = new Pair<String, String>(K.get(j).getId(), K.get(i).getId());
				Pair<String, String> pair2 = new Pair<String, String>(K.get(i).getId(), K.get(j).getId());
				if(!g1.getFillEdges2().contains(pair2) && !g1.getFillEdges2().contains(pair1) ) {
					fillEdgesAdded++;
				}					
			}
		}
	}
	private void input() throws NumberFormatException, IOException{
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Chris\\Desktop\\minimalSplit Input.txt"))) {
		    String line;
		    int vertices = Integer.parseInt(br.readLine());
		    for(int i = 1; i <= vertices; i++) {
		    	g.addVertex("" + i);
		    }
		    g.initializeDegreeMap();
		    g.initializeNeigborsMap();
		    while ((line = br.readLine()) != null) {
		    	String[] s = line.split(",");
		    	Pair<String,String> edge = new Pair<String, String>(s[0],s[1]);
				g.addEdge(edge);
				g.increaseDegreeOfVertex(s[0]);
				g.increaseDegreeOfVertex(s[1]);
				g.increaseNeighborsOfVertex(s[0], s[1]);	
		    }
		}
	}
	
	private void testInput(int numberOfVertexes, double p){
	    for(int i = 1; i <= numberOfVertexes; i++) {
	    	g.addVertex("" + i);
	    }
	    g.initializeDegreeMap();
	   // g.initializeNeigborsMap();
		for(int i = 1; i<=numberOfVertexes - 1; i++) {
			for(int j = i+1; j<=numberOfVertexes; j++) {
				if(StdRandom.bernoulli(p)) {
					String u = ""+i;
					String v = ""+j;
					Pair<String,String> edge = new Pair<String, String>(u,v);
					g.addEdge(edge);
					g.increaseDegreeOfVertex(u);
					g.increaseDegreeOfVertex(v);
					//g.increaseNeighborsOfVertex(u, v);
				}	
		    }
		}
	}
	
	
	


	
}
