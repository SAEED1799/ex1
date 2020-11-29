package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;







public class WGraph_DS implements weighted_graph,Serializable {
	/**
	 * 
	 */
	//verticals is ahashmap for nodes
	//edges is hashmap between hash mab that have src id and dest id and double num for edge
	//neighpors is all the nodes in graph
	//k is count for id num
	//changes is achanges 
	//rdge count is num of edges
	private static final long serialVersionUID = 1L;
	private Collection<node_info> Neighbors= new HashSet<node_info>();
	 HashMap<Integer, node_info> verticals=new HashMap<Integer,node_info>();
	 HashMap<Integer, HashMap<Integer, Double> > edges=new HashMap<Integer, HashMap<Integer, Double> >();
	 int changes=0;
	private int edgecount;
	public static int k=0;
	
	
	public WGraph_DS() {
		edges = new HashMap<Integer, HashMap<Integer, Double>>();
		verticals= new HashMap<Integer,node_info>();
		Neighbors=new HashSet<node_info>(); 
		edgecount=0;
		changes=0;
		k=0;
		
		
	}
    
	@Override
	public node_info getNode(int key) {
		// TODO Auto-generated method stub
		if (this.verticals.get(key) == null)
			return null;
		node_info node = verticals.get(key);
		return node;
	}
    
	@Override
	public boolean hasEdge(int node1, int node2) {
		
	
	    	 
		return this.edges.get(node1) != null && this.edges.get(node1).containsKey(node2);
	   
		
		
	}

	@Override
	public double getEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		
		if((verticals.get(node1)==null)||(verticals.get(node2)==null)) {
			return  -1;
		}
		
		if(hasEdge(node1, node2)!=true) {
			return -1;
		}
				
	return	edges.get(node1).get(node2);
	
	}

	@Override
	public void addNode(int key) {
		// TODO Auto-generated method stub
		
		if(verticals.get(key)!=null) {
			
			return;
		}
		node_info n= new NodeInfo(key);
		verticals.put(key, n);
		changes++;
		k++;
		Neighbors.add(n);
		
		
	}
	// function that updaate edge if i have in the graph from src and dest
    void updateConnection(int node1 , int node2 , double w)
    {
    	
    	if (this.verticals.get(node1) == null || this.verticals.get(node2) == null) {
			
			return;
		}
    	
    	
    	if (this.edges.get(node1) == null) {	
    	    this.edges.put(node1, new HashMap<Integer, Double>());
	   }
    	
    	HashMap<Integer, Double> e = 	this.edges.get(node1);
	     e.put(node2, w);
	     
	 	
    }
    
//    boolean isConnected(int node1, int node2)
//    {
//    	return this.edges.get(node1) != null && this.edges.get(node1).containsKey(node2);
//    }
//    	
	@Override
		public void connect(int node1, int node2, double w) {
		if(!hasEdge(node1, node2)) {
		     this.changes++;
			this.edgecount++;
		     }
	     updateConnection(node1 ,node2 , w);
	     updateConnection(node2 ,node1 , w);
	     
	
					
		}
		
		
	

	@Override
	public Collection<node_info> getV() {
		// TODO Auto-generated method stub
		return  Neighbors;
		
	}
   //l is a collection for the node id 
	//e is ahash map that change naigpors naiphbors for the id node 
	@Override
	public Collection<node_info> getV(int node_id) {
		// TODO Auto-generated method stub
		Collection<node_info> l = new ArrayList<node_info>();
		if(this.edges.get(node_id) == null) {
			return l;
		}
		
	   	HashMap<Integer, Double> e = 	this.edges.get(node_id);
      for(Integer nieber : e.keySet() )
      {
    	 l.add(this.getNode(nieber)); 
      }
      
      return l;
		
	}
  //e is hashmap to remove edges that was connect with node key
	@Override
	public node_info removeNode(int key) {
		// TODO Auto-generated method stub
		if((verticals.get(key)==null))return null;
		if(edges!=null) {
			if(edges.containsKey(key))
			     edges.remove(key);
		
		for(Integer node : edges.keySet()) {
			HashMap<Integer, Double> e = 	this.edges.get(node);
			if(e.containsKey(key)) 
			    e.remove(key);
			 edgecount--;
			 changes++;
			
		}
		}
		node_info n=getNode(key);
		Neighbors.remove(getNode(key));
		verticals.remove(key);
		changes++;
		
		
		k--;
		return n;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		// TODO Auto-generated method stub
		if(!verticals.containsKey(node1)||(!verticals.containsKey(node2))) 
			return;
		
		// if edge is not exit return null
		
		if ((edges.get(node1)==null)||(edges.get(node1).get(node2) == null))
			return;
		// and remove the edges associated with that node too
		edges.get(node1).remove(node2);
		edges.get(node2).remove(node1);
		this.changes++;
		this.edgecount--;
		
	
		
	
	}
	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return verticals.size();
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return edgecount;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return changes;
	}

	 private class NodeInfo implements node_info,Serializable, Comparable<NodeInfo> {
			
			/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
			private int id;
			double tag;
			String info;
			 
			
			public NodeInfo() {
				this.id=k;
				k++;
				this.tag=0;
				this.info="";
			}
			public NodeInfo(int key) {
				this.id=key;
			}
		
			@Override
			public int getKey() {
				// TODO Auto-generated method stub
				return id;
			}
		
			@Override
			public String getInfo() {
				// TODO Auto-generated method stub
				return info;
			}
		
			@Override
			public void setInfo(String s) {
				// TODO Auto-generated method stub
				this.info=s;
			}
		
			@Override
			public double getTag() {
				// TODO Auto-generated method stub
				return tag;
			}
		
			@Override
			public void setTag(double t) {
				// TODO Auto-generated method stub
				this.tag=t;
			}

		
			@Override
			public int compareTo(NodeInfo node2) {
				return this.getKey() - node2.getKey();
				//return 0;
			}
		
		}
}
