package fil.routing.hardware;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class helps user to build new substrate network
 * 
 * @author Van Huynh Nguyen
 *
 */
public class NetworkTopoFailed {
	private Map<NetworkSwitch, LinkedList<NetworkSwitch>> map;
	private LinkedList<NetworkLink> linkBandwidth; // bandwidth of all links
//  private Map<PhysicalServer, NetworkSwitch> listLinksServer;
//	private Map<Integer, PhysicalServer> listPhyServers;
//  private LinkedList<NetworkLink> listNetworkLink; // bandwidth of all Phy->Edge switch link
	private LinkedList<NetworkSwitch> listSwitch;
	
	//for link mapping
	//private Map<NetworkSwitch, LinkedList<NetworkSwitch>> listAggConnectEdge; // list agg switch connect to edge switch
	
	//private Map<NetworkSwitch, LinkedList<NetworkSwitch>> listCoreConnectAgg; // list core switch connect to agg switch
	
	//private Map<Integer, LinkedList<NetworkSwitch>> listEdgeSwitchInPod; // list edge switch in pod
	//private Map<Integer, LinkedList<NetworkSwitch>> listAggSwitchInPod; // list agg switch in pod
	//private LinkedList<NetworkSwitch> listPhySwitch; // gia physical server la mot switch
	/**
	 * Constructs new topology
	 */
	public NetworkTopoFailed() {
		map = new HashMap<>();
//		listLinksServer = new HashMap<PhysicalServer, NetworkSwitch>();
//		listPhyServers = new HashMap<>();
		linkBandwidth = new LinkedList<>();
		//listNetworkLink = new LinkedList<>();
		listSwitch = new LinkedList<>();
//		listAggConnectEdge = new HashMap<>();
//		listCoreConnectAgg = new HashMap<>();
//		listEdgeSwitchInPod = new HashMap<>();
//		listAggSwitchInPod = new HashMap<>();
//		listPhySwitch = new LinkedList<>();
	}
	public NetworkTopoFailed(Map<NetworkSwitch, LinkedList<NetworkSwitch>> mapInput, LinkedList<NetworkLink> linkBandwidthInput, Map<PhysicalServer, 
			NetworkSwitch> listLinksServerInput, Map<Integer, PhysicalServer> listPhyServersInput, LinkedList<NetworkLink> listNetworkLinkInput, 
			LinkedList<NetworkSwitch> listSwitchInput, Map<NetworkSwitch, LinkedList<NetworkSwitch>> listAggConnectEdgeInput, Map<NetworkSwitch, LinkedList<NetworkSwitch>> listCoreConnectAggInput
			, Map<Integer, LinkedList<NetworkSwitch>> listEdgeSwitchInPodInput, Map<Integer, LinkedList<NetworkSwitch>> listAggSwitchInPodInput, LinkedList<NetworkSwitch> listPhySwitchInput)
	{
		map = mapInput;
//		listLinksServer = listLinksServerInput;
//		listPhyServers = listPhyServersInput;
		linkBandwidth = linkBandwidthInput;
//		listNetworkLink = listNetworkLinkInput;
		listSwitch =listSwitchInput;
//		listAggConnectEdge = listAggConnectEdgeInput;
//		listCoreConnectAgg = listCoreConnectAggInput;
//		listEdgeSwitchInPod = listEdgeSwitchInPodInput;
//		listAggSwitchInPod = listAggSwitchInPodInput;
//		listPhySwitch = listPhySwitchInput;
	}

	public void addEdge(NetworkSwitch node1, NetworkSwitch node2, double bandwidth) {
		linkBandwidth.add(new NetworkLink(node1, node2, bandwidth));
		LinkedList<NetworkSwitch> neighbor = map.get(node1);
		if (neighbor == null) {
			neighbor = new LinkedList<>();
			map.put(node1, neighbor);
		}
		neighbor.add(node2);
		if(!listSwitch.contains(node1))
		{
			listSwitch.add(node1);	
		}
		if(!listSwitch.contains(node2))
		{
			listSwitch.add(node2);
		}
	}

//	public void addPhysicalServer(NetworkSwitch edgeSwitch, PhysicalServer physicalServer, double bandwidth) {
//		listLinksServer.put(physicalServer, edgeSwitch);
//		//1: up, 0:down
//		listNetworkLink.add(new NetworkLink(physicalServer, edgeSwitch, bandwidth));
//		if(!listPhyServers.containsValue(physicalServer))
//			listPhyServers.put(Integer.parseInt(physicalServer.getName()), physicalServer);
//		// add physical serer switch - gia switch
//		
//		NetworkSwitch s = new NetworkSwitch(physicalServer.getName(), 0, false);
//		listPhySwitch.add(s);
//	}

	public void addNeighbor(NetworkSwitch node1, NetworkSwitch node2) {
		if (node1.getNameNetworkSwitch().equals(node2.getNameNetworkSwitch()))
			return;
		LinkedList<NetworkSwitch> neighbor = map.get(node1);
		if (neighbor == null) {
			neighbor = new LinkedList<NetworkSwitch>();
			map.put(node1, neighbor);
		}
		neighbor.add(node2);
	}

	public LinkedList<NetworkSwitch> adjacentNodes(NetworkSwitch node) {
		LinkedList<NetworkSwitch> adjacent = map.get(node);
		if (adjacent == null) {
			return new LinkedList<NetworkSwitch>();
		}
		return new LinkedList<NetworkSwitch>(adjacent);
	}

	public int nNeighbors(NetworkSwitch node) {
		LinkedList<NetworkSwitch> adjacent = map.get(node);
		if (adjacent == null) {
			return 0;
		}
		return map.get(node.getNameNetworkSwitch()).size();
	}

//	public LinkedList<String> getForgetLink() {
//		return forgetLink;
//	}
	// show all informations of Topology
	public void showInfo() {
		for (Entry<NetworkSwitch, LinkedList<NetworkSwitch>> entry : map.entrySet()) {
			System.out.println("Node " + entry.getKey());
			for (NetworkSwitch node : entry.getValue()) {
				System.out.print(node.getNameNetworkSwitch()+" ");
			}
			System.out.println();
		}
		System.out.println("server");
		for (Entry<PhysicalServer, NetworkSwitch> entry : listLinksServer.entrySet()) {
			System.out.println(
					"Server " + entry.getKey().getName() + " edgeSwitch " + entry.getValue().getNameNetworkSwitch());

		}
	}

	public Map<PhysicalServer, NetworkSwitch> getListLinksServer() {
		return listLinksServer;
	}

	public Map<Integer, PhysicalServer> getListPhyServers() {
		return listPhyServers;
	}

	public void setListPhyServers(Map<Integer, PhysicalServer> listPhyServers) {
		this.listPhyServers = listPhyServers;
	}
	public LinkedList<NetworkLink> getLinkBandwidth()
	
	{
		return linkBandwidth;
	}
	public void setLinkBandwidth(LinkedList<NetworkLink> linkBandwidth)
	{
		this.linkBandwidth = linkBandwidth;
	}

	public LinkedList<NetworkLink> getListNetworkLink() {
		return listNetworkLink;
	}

	public void setListNetworkLink(LinkedList<NetworkLink> listNetworkLink) {
		this.listNetworkLink = listNetworkLink;
	}
	public void removeEdge(NetworkSwitch egde1, NetworkSwitch edge2)
	{
		//get list Edge switch
		LinkedList<NetworkSwitch> listEdgeSwitch = new LinkedList<>();
		for(Entry<PhysicalServer, NetworkSwitch> entry: listLinksServer.entrySet())
		{
			if(entry.getValue().equals(egde1) || entry.getValue().equals(edge2))
				continue;
			else
			{
			if(!listEdgeSwitch.contains(entry.getValue()))
				listEdgeSwitch.add(entry.getValue());
			}
		}
		
		System.out.println("List edge switch");
		for(NetworkSwitch s: listEdgeSwitch)
			System.out.println(s.getNameNetworkSwitch());
		Iterator<Map.Entry<NetworkSwitch,LinkedList<NetworkSwitch>>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<NetworkSwitch,LinkedList<NetworkSwitch>> entry = iter.next();
			NetworkSwitch sKey = entry.getKey();
			LinkedList<NetworkSwitch> listNeighbor = entry.getValue();
			for(NetworkSwitch edge: listEdgeSwitch)
			{
				for(int i=0; i<listNeighbor.size(); i++)
					if(listNeighbor.get(i).equals(edge))
						listNeighbor.remove(i);
			}
			
			map.put(sKey, listNeighbor);
		}
		for(NetworkSwitch s: listEdgeSwitch)
			map.remove(s);
	}

	public LinkedList<NetworkSwitch> getListSwitch() {
		return listSwitch;
	}

	public Map<NetworkSwitch, LinkedList<NetworkSwitch>> getListAggConnectEdge() {
		return listAggConnectEdge;
	}

	public void setListAggConnectEdge(Map<NetworkSwitch, LinkedList<NetworkSwitch>> listAggConnectEdge) {
		this.listAggConnectEdge = listAggConnectEdge;
	}

	public Map<NetworkSwitch, LinkedList<NetworkSwitch>> getListCoreConnectAgg() {
		return listCoreConnectAgg;
	}

	public void setListCoreConnectAgg(Map<NetworkSwitch, LinkedList<NetworkSwitch>> listCoreConnectAgg) {
		this.listCoreConnectAgg = listCoreConnectAgg;
	}

	public Map<Integer, LinkedList<NetworkSwitch>> getListEdgeSwitchInPod() {
		return listEdgeSwitchInPod;
	}

	public void setListEdgeSwitchInPod(Map<Integer, LinkedList<NetworkSwitch>> listEdgeSwitchInPod) {
		this.listEdgeSwitchInPod = listEdgeSwitchInPod;
	}

	public Map<Integer, LinkedList<NetworkSwitch>> getListAggSwitchInPod() {
		return listAggSwitchInPod;
	}

	public void setListAggSwitchInPod(Map<Integer, LinkedList<NetworkSwitch>> listAggSwitchInPod) {
		this.listAggSwitchInPod = listAggSwitchInPod;
	}

	public void setListSwitch(LinkedList<NetworkSwitch> listSwitch) {
		this.listSwitch = listSwitch;
	}

	public LinkedList<NetworkSwitch> getListPhySwitch() {
		return listPhySwitch;
	}
	public double getRAMRes()
	{
		double totalRam = 0;
		for(Entry<Integer, PhysicalServer> entry: listPhyServers.entrySet())
		{
			totalRam += entry.getValue().getRam();
		}
		return totalRam;
	}
	
	public double getCPURes()
	{
		double totalCPU = 0;
		for(Entry<Integer, PhysicalServer> entry: listPhyServers.entrySet())
		{
			totalCPU += entry.getValue().getCpu();
		}
		return totalCPU;
	}
	public double getLinkBandwidthTopo()
	{
		
		double totalBW =0;
		for(NetworkLink link: linkBandwidth)
		{
			totalBW+= link.getBandwidth();
		}
		for(NetworkLink link: listNetworkLink)
		{
			totalBW+=link.getBandwidth();
		}
		return totalBW;
	}
	public Object clone(){
		NetworkTopoFailed t = new NetworkTopoFailed(map, linkBandwidth, listLinksServer, listPhyServers, listNetworkLink, listSwitch, listAggConnectEdge, listCoreConnectAgg, listEdgeSwitchInPod, listAggSwitchInPod, listPhySwitch);
		return t;		
	}
}
