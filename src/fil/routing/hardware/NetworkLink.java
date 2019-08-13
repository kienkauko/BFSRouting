package fil.routing.hardware;

public class NetworkLink {
	
	private NetworkSwitch startSwitch;
	private NetworkSwitch endSwitch;
	private double usedBandwidth;
	private String nameLink;
	public NetworkLink()
	{
		startSwitch = new NetworkSwitch();
		endSwitch = new NetworkSwitch();
		usedBandwidth = 0;
	}
	public NetworkLink(NetworkSwitch start, NetworkSwitch end, double bw )
	{
		this.startSwitch = start;
		this.endSwitch = end;
		this.usedBandwidth = bw;
		this.setNameLink(start.getNameNetworkSwitch() + end.getNameNetworkSwitch());
		
	}
	public NetworkSwitch getStartSwitch() {
		return startSwitch;
	}
	public void setStartSwitch(NetworkSwitch startSwitch) {
		this.startSwitch = startSwitch;
	}
	public NetworkSwitch getEndSwitch() {
		return endSwitch;
	}
	public void setEndSwitch(NetworkSwitch endSwitch) {
		this.endSwitch = endSwitch;
	}
	public double getUsedBandwidth() {
		return usedBandwidth;
	}
	public String getNameLink() {
		return nameLink;
	}
	public void setNameLink(String nameLink) {
		this.nameLink = nameLink;
	}

}
