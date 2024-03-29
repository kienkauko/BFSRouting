package fil.routing.algo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import fil.routing.hardware.*;

/**
 * This class helps to find all paths exit between source node and destination
 * node
 * 
 * @author Van Huynh Nguyen
 * 
 * 
 */
public class BFS {

	private NetworkSwitch startNode;
	private NetworkSwitch endNode;
	private double bwDemand;
	private Map<Integer,LinkedList<NetworkSwitch>> mypath;
	private Map<Integer,LinkedList<NetworkSwitch>> listAvailablePath;
	private int i=1;
	public static double MAX_BW = 1024;
	/**
	 * The initial class
	 */
	
	public BFS(NetworkSwitch start, NetworkSwitch end, double bw) {
		startNode = start;
		endNode = end;
		bwDemand = bw;
		mypath = new HashMap<>();
		listAvailablePath = new HashMap<>();
	}

	/**
	 * Initial some parameters and run main function
	 * 
	 * @param topo
	 *            This is the topology object, which contains all informations
	 *            of Substrate Network
	 */
	public LinkedList<NetworkSwitch> run(NetworkTopology topo, LinkedList<NetworkSwitch> path) {
	
//		mypath.clear();
		LinkedList<NetworkSwitch> visited = new LinkedList<>();
		visited.add(startNode);
		breadthFirst(topo, visited); // start finding path from the startNode and topology
		getAvailablePath(topo);
		path = getShortestPath();
		System.out.println("Path " + path.size() + "\n");
		System.out.println("Done finding path! \n");
		return path;
	}

	/**
	 * This is the main function, which is used to find all paths exit between
	 * startNode and endNode
	 * 
	 * @param topo
	 *            This is the topology object, which contains all informations
	 *            of Substrate Network
	 * @param visited
	 *            This is the list of nodes, which are visited by algorithm
	 */
	public void breadthFirst(NetworkTopology topo, LinkedList<NetworkSwitch> visited) {
		LinkedList<NetworkSwitch> nodes = topo.adjacentNodes(visited.getLast());
		for (NetworkSwitch node : nodes) {

			if (visited.contains(node)) {
				continue;
			}
			// if found the end node, update path to mypath list
			if (node.equals(endNode)) {
				visited.add(node);
				printPath(visited);
				visited.removeLast();
				break;
			}
		}

		for (NetworkSwitch node : nodes) {

			if (visited.contains(node) || node.equals(endNode)) {
				continue;
			}
			visited.addLast(node);
			breadthFirst(topo, visited);
			visited.removeLast();
		}

	}

	
	public LinkedList<NetworkSwitch> getShortestPath() {
		int numMin = Integer.MAX_VALUE;
		LinkedList<NetworkSwitch> tempPath=  new LinkedList<>();
		for(Entry<Integer,LinkedList<NetworkSwitch>> entry: listAvailablePath.entrySet())
		{
			System.out.println("shortest path with num: " + entry.getValue().size() + "\n");
			if(entry.getValue().size()<numMin)
			{
				numMin = entry.getValue().size();
				tempPath = entry.getValue();
			}

		}
		System.out.println("temPath size: " + tempPath.size() + "\n");
		return tempPath;
	}
	
	public void getAvailablePath(NetworkTopology topo) {
		// sort all bandwidth-available paths
		int count = 1;
		int size = mypath.size();
		System.out.println("Number of path: " + size + " \n");
		for (int i=1; i <= size; i++) { // check all path
			System.out.println("Path number: " + i + " \n");
			LinkedList<NetworkSwitch> path = mypath.get(i); // get each specific path
			System.out.println("Size of path " + path.size() + " \n");
			for (int j = 0; j<(path.size() - 1); j++) {
				System.out.println("Loop number " + j + " \n");
				NetworkSwitch a = path.get(j);
				NetworkSwitch b = path.get(j+1);
				String nameLink = a.getNameNetworkSwitch() + b.getNameNetworkSwitch();
				System.out.println("Name: " + nameLink + " \n");
				Map<String, NetworkLink> listLink = topo.getListLink();
				if(listLink == null) System.out.println(" null listlink \n");
				
				double usedBw = listLink.get(nameLink).getUsedBandwidth();
				if(bwDemand > (MAX_BW - usedBw)) { // one route has failed
					System.out.println("This path has no remained bw \n");
					break;
				}
				if(j == (path.size() - 2)) { // all route is ok
					listAvailablePath.put(count, path);
					System.out.println("this path has passed bw test \n");
					count++;
				}
			}
		}
	}
	
	public void printPath(LinkedList<NetworkSwitch> visited) {

		// for NetworkSwitch khong duoc??? why
		LinkedList<NetworkSwitch> list = new LinkedList<>();
		for(int i=0; i< visited.size(); i++)
		{
			list.add(visited.get(i));
//			System.out.print(visited.get(i).getNameNetworkSwitch()+" ");
		}
		mypath.put(i, list);
		i++;
	}

	
	public Map<Integer,LinkedList<NetworkSwitch>> getMypath() {
		return mypath;
	}
	
	public static void main(String[] args) {
		
	}
}