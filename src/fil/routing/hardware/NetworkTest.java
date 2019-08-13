package fil.routing.hardware;

import java.util.LinkedList;
import java.util.Scanner;

import fil.routing.algo.BFS;

public class NetworkTest {

	public static void main(String[] args) {
		NetworkTopology topoTest = new NetworkTopology();
		LinkedList<NetworkSwitch> pathChosen = new LinkedList<NetworkSwitch>();
		LinkedList<NetworkSwitch> listSwitch = topoTest.getListSwitch();

		topoTest.initTopo();
		
		Scanner input = new Scanner(System.in);
		System.out.println("Choose start node: \n");
		int start = input.nextInt();
		System.out.println("Choose end node: \n");
		int end = input.nextInt();
		
		NetworkSwitch startNode = listSwitch.get(start);
		NetworkSwitch endNode = listSwitch.get(end);
		System.out.println("Start finding way .... \n");
		BFS testBFS = new BFS(startNode, endNode, 100);
		
		pathChosen = testBFS.run(topoTest, pathChosen);
		if(pathChosen.isEmpty()) {
			System.out.println("No link found :( \n");
			input.close();
			return;
		}
		//Done path finding .................................//
		System.out.println("It travels through " + pathChosen.size() + " switches \n");
		for (int i = 0; i < pathChosen.size(); i++) {
			System.out.println("Switch" + pathChosen.get(i).getNameNetworkSwitch() + " \n");
		}
		input.close();
	}

}
