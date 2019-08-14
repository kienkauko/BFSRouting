package fil.routing.hardware;

import java.util.Collections;
import java.util.LinkedList;
//import java.util.Scanner;

import fil.routing.algo.BFS;

public class NetworkRouting {
	private NetworkTopology topo;
	private LinkedList<NetworkSwitch> pathChosen;
	private LinkedList<NetworkSwitch> pathChosenTest;
	//private LinkedList<NetworkSwitch> listSwitch;
	public static int SERVER_POSITION = 9;
	
	public NetworkRouting() {
		this.topo = new NetworkTopology();
		this.topo.initTopo();
		this.pathChosen = new LinkedList<NetworkSwitch>();
		this.pathChosenTest = new LinkedList<NetworkSwitch>();
	}
	
	public boolean NetworkRun(int start, double bwDemand) {
		pathChosenTest.clear();
		boolean isSuccess = false;
		NetworkTopology topoTest = new NetworkTopology(this.topo);		
		NetworkSwitch startNode = topoTest.getListSwitch().get(start);
		NetworkSwitch endNode = topoTest.getListSwitch().get(SERVER_POSITION);
		
		BFS testBFS = new BFS(startNode, endNode, bwDemand);
		pathChosenTest = testBFS.run(topoTest, pathChosenTest);
		
		if(pathChosenTest.isEmpty()) {
			System.out.println("No network link found :( \n");
			return isSuccess = false;
		}
		//Done path finding .................................//
		System.out.println("It travels through " + pathChosenTest.size() + " switches \n");
		for (int i = 0; i < pathChosenTest.size(); i++) {
			System.out.println("Switch" + pathChosenTest.get(i).getNameNetworkSwitch() + " \n");
		}
		
		return isSuccess = true;
	}
	
	public void NetworkSet(double bwDemand) {
		// this.topo.link will be set 
        Collections.copy(this.pathChosen, this.pathChosenTest);
        int size = pathChosen.size();
        for (int i = 0; i < (size - 1); i++) {
        	System.out.println("Setting used bandwidth for network topology... \n");
        	String a = pathChosen.get(i).getNameNetworkSwitch();
        	String b = pathChosen.get(i+1).getNameNetworkSwitch();
        	this.topo.getListLink().get(a+b).setUsedBandwidth(bwDemand);
        	
        	System.out.println("Link " + (a+b) + " has been changed to " + 
        	this.topo.getListLink().get(b+a).getUsedBandwidth());
        	
        	this.topo.getListLink().get(b+a).setUsedBandwidth(bwDemand);
        }
		
	}

	public NetworkTopology getTopo() {
		return topo;
	}

	public void setTopo(NetworkTopology topo) {
		this.topo = topo;
	}

	public LinkedList<NetworkSwitch> getPathChosen() {
		return pathChosen;
	}

	public void setPathChosen(LinkedList<NetworkSwitch> pathChosen) {
		this.pathChosen = pathChosen;
	}

	public LinkedList<NetworkSwitch> getPathChosenTest() {
		return pathChosenTest;
	}

	public void setPathChosenTest(LinkedList<NetworkSwitch> pathChosenTest) {
		this.pathChosenTest = pathChosenTest;
	}
	

}
