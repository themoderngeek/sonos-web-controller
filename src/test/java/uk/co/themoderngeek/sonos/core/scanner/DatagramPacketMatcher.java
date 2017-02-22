package uk.co.themoderngeek.sonos.core.scanner;

import org.hamcrest.Description;
import org.mockito.ArgumentMatcher;

import java.net.DatagramPacket;
import java.util.Arrays;

import static org.mockito.Matchers.argThat;

public class DatagramPacketMatcher extends ArgumentMatcher<DatagramPacket> {


    private final DatagramPacket expected;

    public DatagramPacketMatcher(DatagramPacket expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        DatagramPacket packet = (DatagramPacket) actual;
        return expected.getPort() == packet.getPort() && addressMatch(packet) && Arrays.equals(expected.getData(), packet.getData());
    }

    private boolean addressMatch(DatagramPacket actual) {
        if(expected.getAddress() == null && actual.getAddress() == null) {
            return true;
        }
        return expected.getAddress().equals(actual.getAddress());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected == null ? null : expected.toString());
    }

    static DatagramPacket datagramPacketEq(DatagramPacket expected) {
        return argThat(new DatagramPacketMatcher(expected));
    }

}

