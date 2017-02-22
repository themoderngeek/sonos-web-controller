package uk.co.themoderngeek.sonos.core.mapper;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UrlMapperTest {

    UriMapper unit;

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

    @Before
    public void setUp() throws Exception {
        unit = new UriMapper();
    }

    @Test
    public void mapsNetworkResponseToUri() throws Exception {
        URI uri = unit.toUri(SEARCH_RESPONSE);

        assertThat(uri.getPort(), is(1400));
        assertThat(uri.getHost(), is("192.168.1.89"));
        assertThat(uri.getScheme(), is("http"));
        assertThat(uri.getPath(), is("/xml/device_description.xml"));
    }
}