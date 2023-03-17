import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Profile from './Profile';
import MatchList from './MatchList';


export default ({ summonerName }) =>{
    const [summoner, setSummoner] = useState();


    const getSummoner = async () =>{
        

        
        try{
            const response = await axios.get("/api/users/" + summonerName);
            setSummoner(response.data);
        }catch(e){console.error(e);}
        

    }

    useEffect(() =>{
        getSummoner();
    }, [summonerName])

    return (
        
        <div className = "flex flex-col">
            <Profile summoner = {summoner}/>
            <MatchList summoner = {summoner} />
        
        </div>
    )

};