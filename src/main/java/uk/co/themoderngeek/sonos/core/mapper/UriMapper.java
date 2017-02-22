package uk.co.themoderngeek.sonos.core.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class UriMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UriMapper.class);

    public URI toUri(String searchResponse) {
        URI uri = null;
        try {
            uri = new URI(Arrays.stream(searchResponse.split("\n"))
                    .filter(f -> f.contains("LOCATION"))
                    .findFirst().orElse("")
                    .substring("LOCATION".length() + 1)
                    .trim());
        } catch (URISyntaxException e) {
            LOGGER.warn("Unable to parse URL from: " + searchResponse, e);
        }
        return uri;
    }
}
