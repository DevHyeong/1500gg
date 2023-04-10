import React, {useState, useEffect} from 'react';
import axios from 'axios';
import MatchItem from './MatchItem.js';

const MatchList = ({ summoner }) =>{

    const [match, setMatch] = useState([]);
    const [info, setInfo] = useState([]);

    const getMatchList = async () =>{
        try{
            const response = await axios.get("/api/matches/" + summoner.puuid);
            setMatch(orderBy(response.data));
        }catch(e){

        }
    }

    const matchInfo = async() => {
        try{
            const response = await axios.post("/api/v1/matches", {
                ids: match
            })
            setInfo(response.data);
        }catch(e){console.error(e)}
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

    useEffect(() => {
        matchInfo();
    }, [match]);

    return (
        <section className="relative w-full mt-4 pb-24 overflow-x-auto">
            {
                info.length > 0 && info.map( (e,i) =>{
                    return <MatchItem puuId={summoner.puuid} data={e} />
                })
            }
        </section>
    )
};

export default MatchList;