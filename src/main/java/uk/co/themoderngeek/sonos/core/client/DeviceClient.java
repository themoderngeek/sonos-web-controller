package uk.co.themoderngeek.sonos.core.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.upnp.schemas.device_1_0.Root;
import uk.co.themoderngeek.sonos.core.model.Device;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;


public class DeviceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceClient.class);

    private final URI deviceLocation;

    public DeviceClient(URI deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public Device getDevice() {
        Device device = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Root.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            URL url = deviceLocation.toURL();
            InputStream xml = url.openStream();
            Root deviceXml = unmarshaller.unmarshal(new StreamSource(xml), Root.class).getValue();
            xml.close();

            device = new Device(deviceXml.getDevice(), deviceLocation);
        } catch (JAXBException | IOException e) {
            LOGGER.warn("Unable to get device from URI: " + deviceLocation, e);
        }
        return device;
    }

    public URI getDeviceLocation() {
        return deviceLocation;
    }
}
