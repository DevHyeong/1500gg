import React, {useEffect, useState} from 'react';
import axios from 'axios';

export default ({ summoner }) =>{
    const [league, setLeague] = useState();
    
    const getLeague = async() =>{
        try{
            const response = await axios.get("/api/users/league/" + summoner.id);
            setLeague(response.data);
        

        }catch(e){}

    }

    useEffect(() =>{
        getLeague();
    }, [summoner])


    return (
       
            <>
            
            {
                league && league.map(e => {

                    if(e.queueType === "RANKED_SOLO_5x5"){
                        
                        return (
                            <>
                            <p>{summoner.name}</p>   
                            <div className="flex">
                                <div className="w-2/5 h-32 pt-3">
                                    <img src={"/images/ranked-emblems/"+ e.tier + ".png"} alt="" className="w-20 h-24 object-cover m-auto" />
                                </div>
                                <div className="w-3/5 h-32 pt-4 text-sm">
                                    <p>솔로랭크</p>
                                    <p>{e.tier} {e.rank1} ({e.leaguePoints} Points)</p>
                                    <p>승급전 : </p>
                                    <p>{e.wins + e.losses}전 {e.wins}승 {e.losses}패 ({ (e.wins/(e.wins+e.losses) * 100).toFixed(2)}%)</p>
                                </div>
                            </div>
                            </>   
                        )
                    }
                    
                })
            }

            </>
        
    )

};