import React, {useState, useEffect} from 'react';
import axios from 'axios';
import MatchItem from './MatchItem.js';

const MatchList = ({ summoner }) =>{

    //const runesReforeged = "https://ddragon.leagueoflegends.com/cdn/12.5.1/data/ko_KR/runesReforged.json";
    //const spell = "https://ddragon.leagueoflegends.com/cdn/12.5.1/data/ko_KR/summoner.json";
    
    const [match, setMatch] = useState([]);
    
    const getMatchList = async () =>{
        try{
            const response = await axios.get("/api/matches/" + summoner.puuid);

            setMatch(orderBy(response.data));

        }catch(e){

        }
    }


    const orderBy = (data) =>{
        data.sort( (a,b) =>{
            a = a.replace("KR_", "");
            b = b.replace("KR_", "");
            return b - a;
        });
        return data;
    }


    useEffect(() => {
        
        getMatchList();
    
    }, [summoner]);




    return (
        <section className="relative w-full mt-4 pb-24 overflow-x-auto">
            {
                match.length > 0 && match.map( (e,i) =>{
                   
                   
                    return <MatchItem puuId={summoner.puuid} matchId={e} />
                    
                   
                    
                })
                
            }
            
            


        </section>
    )


};

export default MatchList;