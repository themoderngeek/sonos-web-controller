import React from 'react';
import Player from '../Player/Player';
import './player-list.css';

export default ({players}) => <div className="Player-List">
    <h1>Rooms</h1>
    <ul>
        {players.map(player =>
            <li key={player.displayName}>
                <Player name={player.displayName} />
            </li>
        )}
    </ul>
</div>
