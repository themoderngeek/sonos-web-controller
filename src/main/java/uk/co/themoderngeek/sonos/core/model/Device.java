package uk.co.themoderngeek.sonos.core.model;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.upnp.schemas.device_1_0.DeviceType;


import java.net.URI;

public class Device {

    private final DeviceType deviceType;

    private final URI uri;

    public Device(DeviceType deviceType, URI uri) {
        this.deviceType = deviceType;
        this.uri = uri;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public String toString() {
        for (Object o : deviceType.getAny()) {
            ElementNSImpl element = (ElementNSImpl) o;
            if(((ElementNSImpl) o).getLocalName().equals("roomName")) {
                return element.getFirstChild().getNodeValue();
            }
        }
        return "";
    }
}
