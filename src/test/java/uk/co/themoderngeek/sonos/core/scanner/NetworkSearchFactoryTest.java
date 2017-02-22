package uk.co.themoderngeek.sonos.core.scanner;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NetworkSearchFactoryTest {

    NetworkSearchFactory unit = new NetworkSearchFactory();

    private static final String MSEARCH = "M-SEARCH * HTTP/1.1\n" +
            "Host: 239.255.255.250:1900\n" +
            "Man: \"ssdp:discover\"\n" +
            "ST: urn:schemas-upnp-org:service:AVTransport:1";

    @Test
    public void createsDefaultNetworkSearch() throws Exception {
        NetworkSearch networkSearch = unit.createDefaultNetworkSearch();

        assertThat(networkSearch.getSearchString(), is(MSEARCH));
        assertThat(networkSearch.getSearchDuration(), is(1000));
        assertThat(networkSearch.getSsdpAddress().getHostAddress(), is("239.255.255.250"));
        assertThat(networkSearch.getSsdpPort(), is(1900));
    }
}