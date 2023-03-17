import React, {useState, useEffect} from 'react';
import axios from 'axios';
import MatchItem from './MatchItem';

export default ({ summoner }) =>{

    const [match, setMatch] = useState();

    const getMatchList = async () =>{
        try{
            const response = await axios.get("/api/users/matchList/" + summoner.puuid);
            setMatch(response.data);

        }catch(e){

        }
    }

    useEffect(() =>{
        console.log(summoner); 
        getMatchList();
    }, [summoner])

    return (
        <div className="flex flex-col">
            {/* {
                match && match.map(e => {
                    return <MatchItem matchId = {e} puuId = {summoner.puuid}/>                    
                })

            }
         */}
        </div>
    )

};