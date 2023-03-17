import React, {useEffect, useState} from 'react';
import axios from 'axios';
import moment from 'moment';
import {champUrl, toKda, getTimeStamp} from '../../Common';

export default ({ matchId, puuId }) =>{

    const [match, setMatch] = useState();
    const [participant, setParticipant] = useState();




    const getMatch = async () =>{
        try{
            const response = await axios.get("/api/users/matchInfo/" + matchId);
            const metadata = response.data.metadata;
            const {gameMode, gameType, participants, teams} = response.data.info;
            let index;


            // ARAM(450), CLASSIC(420)
            if(gameType !== 'MATCHED_GAME'){
                return;
            }
            metadata.participants.forEach( (e,i) =>{

                if(e === puuId){
                    index = i;
                    return;
                }
            });
           
            setParticipant(participants[index]);
            setMatch(response.data);
        }catch(e){}
    }


    useEffect(() =>{
        getMatch();
    }, [matchId])

    useEffect(() =>{

    }, [participant]);

    return (
        <>
        {
            (match && participant) &&
            <div className={(participant.win ? "border-sky-300 bg-sky-300": "border-red-300 bg-red-300") + " flex h-8"}>
                <div className="h-8 w-8">    
                    <img src={champUrl + participant.championName + ".png"} className="w-full h-full"/>
                </div>


                <div className="flex mx-3 text-center w-32 text-sm">
                    <p className="kill">{participant.kills}/{participant.deaths}/{participant.assists} ({toKda(participant.kills, participant.assists, participant.deaths)})</p>
                    <p>{participant.totalMinionsKilled + participant.neutralMinionsKilled} ()</p>
                    <p>{
                        match.info.teams.map(e =>{
                            if(participant.teamId === e.teamId){
                                return parseInt((participant.kills + participant.assists)/e.killsChampion * 100);
                            }
                        })
                    }%</p>
                </div>

                <div className="text-center mr-3 w-16 text-sm">
                    <p className="timestamp">{getTimeStamp(match.info.gameEndTimestamp)}</p>
                </div>
            </div>
        }
            

        </>

    )


}