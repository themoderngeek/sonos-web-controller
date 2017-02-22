package uk.co.themoderngeek.sonos.core.client;

import org.junit.Before;
import org.junit.Test;
import uk.co.themoderngeek.sonos.core.model.Device;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeviceClientTest {

    private DeviceClient unit;
    private URI uri;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        uri = classLoader.getResource("device.xml").toURI();
        unit = new DeviceClient(uri);
    }

    @Test
    public void getDeviceDataFromUri() throws Exception {

        Device device = unit.getDevice();

        assertThat(device.getDeviceType().getDeviceType(), is("urn:schemas-upnp-org:device:ZonePlayer:1"));
        assertThat(device.getDeviceType().getFriendlyName(), is("192.168.1.89 - Sonos PLAYBAR"));
    }
}