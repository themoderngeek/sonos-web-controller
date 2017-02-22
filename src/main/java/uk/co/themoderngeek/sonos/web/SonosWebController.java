package uk.co.themoderngeek.sonos.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.themoderngeek.sonos.core.Search;
import uk.co.themoderngeek.sonos.web.model.Player;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SonosWebController {

    private List<Player> players;

    public SonosWebController() {
        players = new Search().searchForDevices().stream()
                .map(device -> new Player(device.toString()))
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/players")
    public List<Player> getPlayers() {
        return players;
    }
}
