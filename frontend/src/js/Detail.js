import React from "react";
import Team from "./Team.js";


export default ({ match }) => {

    const { participants } = match.info;
    const max = participants.reduce((a,b) => { 
        let c = a.totalDamageDealtToChampions ? a.totalDamageDealtToChampions : a;
        return c > b.totalDamageDealtToChampions ? c : b.totalDamageDealtToChampions;
    });
    const team1 = participants.filter((e, i) => (i < 5));
    const team2 = participants.filter((e, i) => (i > 4));
    const blue = match.info.teams[0];
    const red = match.info.teams[1];


//totalDamageDealtToChampions
    return (
        <div>
            <Team info={blue} match={match} team={team1} max={max} />
            <div></div>
            <Team info={red} match={match} team={team2} max={max} />
        </div>
    )



};