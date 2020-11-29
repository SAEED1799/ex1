package ex1.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;



public class WGraph_Algo implements weighted_graph_algorithms {
	public weighted_graph g;


	public WGraph_Algo() {
		this.g=new WGraph_DS();
	}

	@Override
	public void init(weighted_graph g) {
		// TODO Auto-generated method stub
		this.g=g;
	}

	@Override
	public weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return g;
	}

	@Override
	public weighted_graph copy() {
		// TODO Auto-generated method stub
		weighted_graph g2= new WGraph_DS();

		for(node_info n:g.getV()) {
			g2.addNode(n.getKey());
		}

		return g2;



	}





	private void dijkstra(weighted_graph g, int src)
	{
		node_info node=g.getNode(src);
		this.g=g;
		double x=0;
		Queue<node_info> queue = new  PriorityQueue<node_info>();
		for (node_info n:g.getV())
			n.setTag(Integer.MAX_VALUE);

		node.setTag(0.0);
		queue.add(node);
		// Add source node to the priority queue
		//queue.add(g.getNode(src));


		// Distance to the source is 0

		while (!queue.isEmpty()) {
			node=queue.poll();
			for( node_info n:g.getV(node.getKey())) {

				x=g.getEdge(node.getKey(), n.getKey())+node.getTag();
				if(n.getTag()>x) {
					n.setTag(x);
					queue.add(n);
				}
			}
		}

	}
	//b is A INT for give me akey of node that i chose from loop
	int b;
	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		if(!g.getV().isEmpty())
		{
			if((g.getV().size()==0)||(g.getV().size()==1))return true;
			for(node_info nn:g.getV()) {
				nn.setInfo("0");
				b=nn.getKey();
			}
			if((g.getV(b).size()==1)&&(g.getV().size()==2))return true;

			Queue<Integer> queue = new LinkedList<Integer>();
			node_info n= g.getNode(b);
			n.setInfo("1");
			queue.add(n.getKey());
			while(!queue.isEmpty()) {
				n=g.getNode(queue.poll());
				for(node_info n1:g.getV(n.getKey())) {
					if(n1.getInfo()=="0") {
						n1.setInfo("1");

						queue.add(n1.getKey());
					}
				}
			}
			for(node_info nn:g.getV()) {
				if(nn.getInfo()!="1")return false;


			}



		}

		return true;

	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub

		double x=0;
		if(src==dest)return 0;
		if((g.getNode(dest)==null)||(g.getNode(src)==null))return -1;
		if((g.getV(dest)==null)||(g.getV(src)==null))return -1;


		dijkstra(g, src);
		x=g.getNode(dest).getTag();
		return x;
	}
	//here i am add all the nodes in stack from dest to src
	//while in the last is for rev
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub

		List<node_info> l= new ArrayList<node_info>() ;
		Stack<node_info> stack = new Stack<node_info>();
		double x=Double.MAX_VALUE;
		double y=0;
		if((g.getV(dest)==null)||(g.getV(src)==null))
			return l;

		if(src==dest) {
			l.add(g.getNode(dest));
			return l;
		}
		dijkstra(g, src);
		node_info nn=g.getNode(dest);
		node_info nnn=g.getNode(src);
		stack.add(nn);
		while(nn.getKey()!=nnn.getKey()) {

			for(node_info n1:g.getV(nn.getKey())) {



				if(n1.getTag()+g.getEdge(n1.getKey(),nn.getKey())==nn.getTag()) {

					nn=n1;
					break;


				}


			}
			stack.add(nn);


		}

		while(!stack.isEmpty()) {
			l.add(stack.pop());
		}






		return l;
	}


	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub


		try {
			FileOutputStream f = new FileOutputStream(new File(file));
			ObjectOutputStream o = new ObjectOutputStream(f);
			// Write objects to file
			o.writeObject(this.g);
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean load(String file) {


		FileInputStream f= null;
		try {
			f= new FileInputStream(file);
			ObjectInputStream o = new ObjectInputStream(f);
			g = (weighted_graph)o.readObject();

			o.close();
			f.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;

	}
}