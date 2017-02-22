package uk.co.themoderngeek.sonos.core;

import uk.co.themoderngeek.sonos.core.client.DeviceClient;
import uk.co.themoderngeek.sonos.core.mapper.UriMapper;
import uk.co.themoderngeek.sonos.core.model.Device;
import uk.co.themoderngeek.sonos.core.scanner.NetworkSearch;
import uk.co.themoderngeek.sonos.core.scanner.NetworkSearchFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Search {

    private final NetworkSearch networkSearch;
    private final UriMapper mapper;

    public Search() {
        this.mapper = new UriMapper();
        this.networkSearch = new NetworkSearchFactory().createDefaultNetworkSearch();
    }

    public List<Device> searchForDevices() {
        return networkSearch.search()
                .stream()
                .map(mapper::toUri)
                .collect(Collectors.toList())
                .stream()
                .map(uri -> new DeviceClient(uri).getDevice())
                .collect(Collectors.toList());
    }
}
