

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

public class SplitGraphSequence {
	private Grafoi g1 = new Grafoi();
	private Grafoi g2 = new Grafoi();
	private Grafoi gOut;
	private int j;
	private int maxDegree;
	private String x;
	private Set<Pair<String,String>> f = new HashSet<Pair<String,String>>();
	private Set<String> K = new HashSet<String>();
	private Set<String> I = new HashSet<String>();
	private Set<String> K_ = new HashSet<String>();
	private Set<String> I_ = new HashSet<String>();
	private Set<String> T = new HashSet<String>();
	private Set<String> T_ = new HashSet<String>();
	private long start2;
	private long end2;
	public SplitGraphSequence() {
		
	}
	public void run() throws NumberFormatException, IOException {
		input();
		testInput(15000,0.1, 0.023);
		System.out.println("g1  edges " + g1.edgeList().size());
		System.out.println(" g2  edges " + g2.edgeList().size());
		System.out.println(" f edges " + f.size());
		start2 = System.currentTimeMillis();
		j = f.size();
		findT();
		for(String v : T) {
			T_.add(v);
		}
		gOut = this.g2;
		removeFillEdges();
		if(f.size() !=0 ) {
			x = maximumVertexDegreeOfI_();
			if(maxDegree >= K.size()) {
				removeNeighborsOfVertexFromT(x);
			}	
		}
		String str;
		while(f.size() != 0) {
			if(T.size() != 0) {
				str = T.iterator().next();
				T_.remove(str);
				T.remove(str);
				K_.remove(str);
				I_.add(str);
				List<Pair<String, String>> temp = new ArrayList<Pair<String, String>>();
				for(Pair<String,String> edge : gOut.getIncidentEdgesOfVertex().get(str)) {
					f.remove(edge);
					temp.add(edge);
				}
				/*for(Pair<String,String> edge : temp) {
					gOut.getIncidentEdgesOfVertex().get(edge.getTarget()).remove(edge);
					//gOut.removeEdge(edge);
					//printGraphAfterDeleteOfFillEdge(gOut);
				}*/
			}else {
				K_.add(x);
				I_.remove(x);
				T = T_;
			}
		}
		System.out.println("to f exei " + f.size());
		end2 = System.currentTimeMillis();
		System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
		System.out.println("Elapsed Time in seconds: "+ (end2-start2)/1000);
	}
	
	private void removeNeighborsOfVertexFromT(String x) {
		List<String> neighbors = gOut.getNeighborsListOf(x);
		for(String n : neighbors) {
			if(T.contains(n)) { 
				T.remove(n);
			}
		}
	}
	
	private String maximumVertexDegreeOfI_() {
		String v = "";
		int temp;
		for(String key : I_) {
			temp = gOut.degreeOf(key);
			if(temp > maxDegree) {
				maxDegree = temp;
				v = key;
			}
		}	
		return v;
	}
	
	private boolean testIncidence(Pair<String,String> e, String v) {
		if(e.getSource().equals(v) || e.getTarget().equals(v)) return true;
		else return false;
	}
	

	private  void removeFillEdges() {
		List<Pair<String,String>> pair = new ArrayList<Pair<String,String>>();
		for(Pair<String, String> e : f) {
			if(K_.contains(e.getSource()) && I_.contains(e.getTarget()) || I_.contains(e.getSource()) && K_.contains(e.getTarget())){
				//gOut.removeEdge(e);
				j = j - 1;
				//printGraphAfterDeleteOfFillEdge(gOut);
				pair.add(e);
			}
		}
		for(Pair<String, String> e : pair) {
			f.remove(e);
		}
	}
	
	private void printGraphAfterDeleteOfFillEdge(Grafoi g) {
		System.out.print("G" + j + "edges = ");
		g.printEdgeList();
		System.out.println();
	}

	
	private  void findT(){
		if(K_.size() < I.size()) {
			for(String x : K_) {
		 		if(I.contains(x)) {
		 			T.add(x);
		 		}
		 	}		 	
		}else {
			for(String x : I) {
		 		if(K_.contains(x)) {
		 			T.add(x);
		 		}
		 	}		 
		}			 	
	}
	
	private void testInput(int numberOfVertices, double p, double fill){
		List<String> I = new ArrayList<String>();
		List<String> K = new ArrayList<String>();
		    for(int i = 1; i <= numberOfVertices; i++) {
		    	if(StdRandom.bernoulli(p)) {
		    		this.I.add("" + i);
		    		I.add(""+i);
		    		I_.add(""+i);
		    	}else {
		    		this.K.add("" + i);
		    		K.add(""+i);
		    		K_.add(""+i);
		    	}
		    	/*if(StdRandom.bernoulli(p)) {
		    		I_.put("" + i, true);
		    	}else {
		    		K_.put("" + i, true);
		    	}*/
		    		
		    	g1.addVertex("" + i);
		    	g2.addVertex("" + i);
		    }
		    g1.initializeDegreeMap();
		    g2.initializeDegreeMap();
		    g2.initializeNeigborsMap();
		    g1.initializeNeigborsMap();
		    g1.initializeIncidenceMap();
		    g2.initializeIncidenceMap();
		    //System.out.println("K =" + K);
		    //System.out.println("I =" + I);
		    for(int i = 0; i<K.size() - 1; i++) {
				for(int j = i+1; j<K.size(); j++) {
					String u = K.get(i);
					String v = K.get(j);
					Pair<String,String> edge = new Pair<String, String>(u,v);
					g1.addEdge(edge);
					g2.addEdge(edge);
					g1.increaseDegreeOfVertex(u);
					g1.increaseDegreeOfVertex(v);
					g2.increaseDegreeOfVertex(u);
					g2.increaseDegreeOfVertex(v);
					g1.increaseNeighborsOfVertex(u, v);
					g2.increaseNeighborsOfVertex(u, v);
					g1.increaseIncidenceMapOfVertex(v, edge);
					g1.increaseIncidenceMapOfVertex(v, edge);
					g2.increaseIncidenceMapOfVertex(v, edge);
					g2.increaseIncidenceMapOfVertex(v, edge);
				
				}
		    }
		    for(int i = 0; i<I.size(); i++) {
				for(int j = i+1; j<K.size(); j++) {
					if(StdRandom.bernoulli(p)) {
						String u = I.get(i);
						String v = K.get(j);
						Pair<String,String> edge = new Pair<String, String>(u,v);
						g1.addEdge(edge);
						g2.addEdge(edge);
						g1.increaseDegreeOfVertex(u);
						g1.increaseDegreeOfVertex(v);
						g2.increaseDegreeOfVertex(u);
						g2.increaseDegreeOfVertex(v);
						g1.increaseNeighborsOfVertex(u, v);
						g2.increaseNeighborsOfVertex(u, v);
						g1.increaseIncidenceMapOfVertex(v, edge);
						g1.increaseIncidenceMapOfVertex(u, edge);
						g2.increaseIncidenceMapOfVertex(v, edge);
						g2.increaseIncidenceMapOfVertex(u, edge);
					}else {
						if(StdRandom.bernoulli(fill)) {
							String u = I.get(i);
							String v = K.get(j);
							Pair<String,String> edge = new Pair<String, String>(u,v);
							g2.addEdge(edge);
							g2.increaseDegreeOfVertex(u);
							g2.increaseDegreeOfVertex(v);
							g2.increaseNeighborsOfVertex(u, v);
							g2.increaseIncidenceMapOfVertex(v, edge);
							g2.increaseIncidenceMapOfVertex(u, edge);
							f.add(edge);
						}
					}
				}
		    }
		    /*if(!g2.isSplit()) {
				JOptionPane.showMessageDialog(null, 
	                    "The graph isnt a split graph. Graph needs to be split for the algorithm to work", 
	                    "Warning", 
	                    JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}	*/
	}
	
	private void input() throws NumberFormatException, IOException{
		try (BufferedReader br = new BufferedReader(new FileReader("************"))) {
		    String line;
		    int j = 0;
		    int vertices = Integer.parseInt(br.readLine());
		    for(int i = 1; i <= vertices; i++) {
		    	g1.addVertex("" + i);
		    	g2.addVertex("" + i);
		    }
		    g1.initializeDegreeMap();
		    g1.initializeNeigborsMap();
		    g2.initializeDegreeMap();
		    g2.initializeNeigborsMap();
		    int numEdges = Integer.parseInt(br.readLine());
		    line = br.readLine();
		    String[] k = line.split(",");
		    line = br.readLine();
		    String[] i = line.split(",");
		    for(int x = 0; x<k.length; x++) {
		    	K.add(k[x]);
		    }
		    for(int y = 0; y<i.length; y++) {
		    	I.add(i[y]);
		    }
		    if(K.size()+I.size()!=vertices) {
		    	JOptionPane.showMessageDialog(null, 
	                    "The number of vertexes inside K and I doesnt match the vertexes of the graph.", 
	                    "Warning", 
	                    JOptionPane.WARNING_MESSAGE);
				System.exit(0);
		    }
		    while ((line = br.readLine()) != null) {
		    	String[] s = line.split(",");
		    	Pair<String,String> edge = new Pair<String, String>(s[0],s[1]);
				g1.addEdge(edge);
				g2.addEdge(edge);
				g1.increaseDegreeOfVertex(s[0]);
				g1.increaseDegreeOfVertex(s[1]);
				g1.increaseNeighborsOfVertex(s[0], s[1]);
				g2.increaseDegreeOfVertex(s[0]);
				g2.increaseDegreeOfVertex(s[1]);
				g2.increaseNeighborsOfVertex(s[0], s[1]);
				j++;
				if(j == numEdges) break;
		    }
		    line = br.readLine();
			String[] k_ = line.split(",");
			line = br.readLine();
			String[] i_ = line.split(",");
			for(int x = 0; x<k_.length; x++) {
				K_.add(k_[x]);
				
			}
			 for(int y = 0; y<i_.length; y++) {
			    I_.add(i_[y]);
			}
		    while ((line = br.readLine()) != null) {
		    	String[] s = line.split(",");
		    	Pair<String,String> edge = new Pair<String, String>(s[0],s[1]);
				f.add(edge);
				g2.addEdge(edge);
				g2.increaseDegreeOfVertex(s[0]);
				g2.increaseDegreeOfVertex(s[1]);
				g2.increaseNeighborsOfVertex(s[0], s[1]);
				
		    }
		}
		if(!g1.isSplit()) {
			JOptionPane.showMessageDialog(null, 
                    "The graph isnt a split graph. Graph needs to be split for the algorithm to work", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
		if(!g2.isSplit()) {
			JOptionPane.showMessageDialog(null, 
                    "The graph isnt a split graph. Graph needs to be split for the algorithm to work", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}		
	}	
}
