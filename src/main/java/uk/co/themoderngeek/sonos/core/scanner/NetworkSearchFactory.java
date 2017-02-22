package uk.co.themoderngeek.sonos.core.scanner;


import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetworkSearchFactory {

    private final DatagramSocket clientSocket;

    private final InetAddress ssdpAddress;

    private int ssdpPort = 1900;

    private final String searchString;

    private int searchDuration = 1000;

    public NetworkSearchFactory()  {
        DatagramSocket socket = null;
        InetAddress address = null;
        try {
            socket =  new DatagramSocket();
            address = InetAddress.getByName("239.255.255.250");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        this.clientSocket = socket;
        this.ssdpAddress = address;
        this.searchString = getSearchString();
    }

    public NetworkSearch createDefaultNetworkSearch() {
        return new NetworkSearch(clientSocket, ssdpAddress, ssdpPort, searchString, searchDuration);
    }

    private String getSearchString() {
        return "M-SEARCH * HTTP/1.1\n" +
                "Host: " + ssdpAddress.getHostAddress() + ":" + ssdpPort + "\n" +
                "Man: \"ssdp:discover\"\n" +
                "ST: urn:schemas-upnp-org:service:AVTransport:1";
    }
}
