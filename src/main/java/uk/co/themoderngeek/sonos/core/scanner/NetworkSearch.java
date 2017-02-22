package uk.co.themoderngeek.sonos.core.scanner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class NetworkSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkSearch.class);

    private DatagramSocket clientSocket;

    private final InetAddress ssdpAddress;

    private final int ssdpPort;

    private final String searchString;

    private final int searchDuration;

    NetworkSearch(DatagramSocket clientSocket, InetAddress ssdpAddress, int ssdPort, String searchString, int searchDuration) {
        this.clientSocket = clientSocket;
        this.ssdpAddress = ssdpAddress;
        this.ssdpPort = ssdPort;
        this.searchString = searchString;
        this.searchDuration = searchDuration;
    }

    private void sendSearchPacket() throws IOException {
        clientSocket.send(new DatagramPacket(searchString.getBytes(), searchString.length(), ssdpAddress, ssdpPort));
    }

    private String receiveData() throws IOException {
        byte[] response = new byte[1024];
        DatagramPacket packet = new DatagramPacket(response, response.length);
        clientSocket.receive(packet);
        return new String(packet.getData());
    }

    public List<String> search() {
        List<String> results = new ArrayList<>();
        try {
            this.sendSearchPacket();
            new Thread(() -> {
                sleep(searchDuration);
                clientSocket.close();
            }).start();

            while (!clientSocket.isClosed()) {
                results.add(receiveData());
            }
        } catch (IOException e) {
            //Do nothing, the socket has been closed already so don't process any more data.
        } finally {
            clientSocket.close();
        }
        return results;
    }

    private void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            LOGGER.warn("Unable to pause current thread", e);
        }
    }

    public DatagramSocket getClientSocket() {
        return clientSocket;
    }

    public InetAddress getSsdpAddress() {
        return ssdpAddress;
    }

    public int getSsdpPort() {
        return ssdpPort;
    }

    public String getSearchString() {
        return searchString;
    }

    public int getSearchDuration() {
        return searchDuration;
    }
}
