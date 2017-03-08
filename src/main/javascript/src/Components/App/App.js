import React, {Component} from 'react';
import PlayerList from '../Player-List/Player-List';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            players: []
        };
    }

    componentDidMount() {
        fetch(`http://127.0.0.1:8080/players`)
            .then(result=>result.json())
            .then(players=>this.setState({players}))
    }

    render() {
        return (
            <PlayerList players={this.state.players} />
        );
    }
}

export default App;
