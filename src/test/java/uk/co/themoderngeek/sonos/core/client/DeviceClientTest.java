package uk.co.themoderngeek.sonos.core.client;

import org.junit.Test;
import uk.co.themoderngeek.sonos.core.model.Device;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeviceClientTest {

    private DeviceClient unit;
    private URI uri = new URI("file:///Users/marktaylor/git-source/sonos-web-controller/src/test/resources/device.xml");

    public DeviceClientTest() throws URISyntaxException {
    }

    @Test
    public void getDeviceDataFromUri() throws Exception {
        unit = new DeviceClient(uri);

        Device device = unit.getDevice();

        assertThat(device.getDeviceType().getDeviceType(), is("urn:schemas-upnp-org:device:ZonePlayer:1"));
        assertThat(device.getDeviceType().getFriendlyName(), is("192.168.1.89 - Sonos PLAYBAR"));
    }
}