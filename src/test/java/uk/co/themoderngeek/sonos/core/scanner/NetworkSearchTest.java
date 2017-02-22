package uk.co.themoderngeek.sonos.core.scanner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static uk.co.themoderngeek.sonos.core.scanner.DatagramPacketMatcher.datagramPacketEq;

public class NetworkSearchTest {

    NetworkSearch unit;

    private static final String MSEARCH = "M-SEARCH * HTTP/1.1\n" +
            "Host: 239.255.255.250:1900\n" +
            "Man: \"ssdp:discover\"\n" +
            "ST: urn:schemas-upnp-org:service:AVTransport:1";

    private static final String SEARCH_RESPONSE = "HTTP/1.1 200 OK\n" +
            "CACHE-CONTROL: max-age = 1800\n" +
            "EXT:\n" +
            "LOCATION: http://192.168.1.89:1400/xml/device_description.xml\n" +
            "SERVER: Linux UPnP/1.0 Sonos/34.16-37101 (ZPS9)\n" +
            "ST: urn:schemas-upnp-org:service:AVTransport:1\n" +
            "USN: uuid:RINCON_5CAAFDA9374D01400_MR::urn:schemas-upnp-org:service:AVTransport:1\n" +
            "X-RINCON-HOUSEHOLD: Sonos_UKDldWRCLmfvimSg4aj5azm0tX\n" +
            "X-RINCON-BOOTSEQ: 5\n" +
            "X-RINCON-WIFIMODE: 0\n" +
            "X-RINCON-VARIANT: 2\n\n\n";

    private DatagramSocket clientSocket = mock(DatagramSocket.class);

    private InetAddress address = mock(InetAddress.class);

    private int searchLength = 1;

    @Before
    public void setUp() throws Exception {
        unit = new NetworkSearch(clientSocket, address, 1900, MSEARCH, searchLength);
    }

    @Test
    public void listensForResponses() throws Exception {

        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

        doAnswer(invocationOnMock -> {
            DatagramPacket packetArgument = (DatagramPacket) invocationOnMock.getArguments()[0];
            packetArgument.setData(SEARCH_RESPONSE.getBytes());
            return null;
        }).when(clientSocket).receive(datagramPacketEq(packet));

        when(clientSocket.isClosed()).thenReturn(false);

        doAnswer(invocationOnMock -> {
            Mockito.reset(clientSocket);
            when(clientSocket.isClosed()).thenReturn(true);
            return null;
        }).when(clientSocket).close();
        List<String> results = unit.search();

        assertThat(results.get(0), is(SEARCH_RESPONSE));
    }
}