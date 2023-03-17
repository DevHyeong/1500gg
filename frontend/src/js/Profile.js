import React, {useEffect, useState} from "react";
import axios from 'axios';




export default ({ summoner }) =>{

    const [league, setLeague] = useState();
    const [soloState, setSoloState] = useState(false);
    const [flexState, setFlexState] = useState(false);

    const getLeague = async() =>{
        try{
            const response = await axios.get("/api/league/" + summoner.id);
            setLeague(response.data);
        

        }catch(e){}

    }




    useEffect( ()=> {

        getLeague();
    }, [summoner]);


    useEffect (() =>{

        league && league.forEach(e => {
            e.queueType === "RANKED_SOLO_5x5" ? setSoloState(true) : setFlexState(true);
        
        });


    },[league]);


    return (
        <div className="flex font-sans">

            {
                league && league.map(e => {

                    if(e.queueType === "RANKED_SOLO_5x5"){
                        
                        return (
                                
                            <div className="flex flex-1 w-2/5">
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
                        )
                    }
                    
                })
            }
            {
                soloState ? "" :
                <div className="flex flex-1 w-2/5">
                    <div className="w-2/5 h-32 pt-3">
                        <img src={"/images/ranked-emblems/Emblem_Gold.png"} alt="" className="w-20 h-24 object-cover m-auto" />
                    </div>
                    <div className="w-3/5 h-32 p-2 text-sm">
                        <p>솔로랭크</p>
                        <p>0전 0승 0패</p>       
                    </div>
                </div>   
                
            }
            {
                
                league && league.map(e => {
                    
                    if(e.queueType === "RANKED_FLEX_SR"){
                        return (
                                
                            <div className="flex flex-1 w-2/5">
                                <div className="w-2/5 h-32 pt-3">
                                    <img src={"/images/ranked-emblems/"+ e.tier + ".png"} alt="" className="w-20 h-24 object-cover m-auto" />
                                </div>
                                <div className="w-3/5 h-32 p-2 text-sm">
                                    <p>자유랭크</p>
                                    <p>{e.tier} {e.rank1} ({e.leaguePoints} Points)</p>
                                    <p>승급전 : </p>
                                    <p>{e.wins + e.losses}전 {e.wins}승 {e.losses}패 ({ (e.wins/(e.wins+e.losses) * 100).toFixed(2)}%)</p>       
                                </div>
                            </div>
                           
                        )
                    }    
                })
                
            }
            {
                flexState ? "" :
                <div className="flex flex-1 w-2/5">
                    <div className="w-2/5 h-32 pt-3">
                        <img src={"/images/ranked-emblems/Emblem_Gold.png"} alt="" className="w-20 h-24 object-cover m-auto" />
                    </div>
                    <div className="w-3/5 h-32 p-2 text-sm">
                        <p>자유랭크</p>
                        <p>0전 0승 0패</p>       
                    </div>
                </div>   
               
            }
            
        </div>
        

    )
};