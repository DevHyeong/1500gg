import React, {useState, useEffect} from 'react';
import Profile from '../Profile';
import MatchList from '../MatchList';
import axios from 'axios';
import {set} from '../Common';
import progressAxios from '../api/ProgressAxios';
import ProgressBar from './ProgressBar';
import { useSelector } from "react-redux";

const Content = ({ params }) =>{

    const [summoner, setSummoner] = useState();
    const progress = useSelector(state => state.progress);

    const getSummoner = async (name) =>{
        set();        
        try{
            const response = await axios.get("/api/summoner/" + name);
            setSummoner(response.data);
        }catch(e){console.error(e);}
        
    }

    const handleClick = () =>{
        renewal();
        

    }

    const renewal = async () =>{
        
        try{
            const response = await axios.post("/api/renewal", {
                name : summoner.name
            });

            if(response.status === 200){
                
            }
            //setSummoner(response.data);
        }catch(e){}
        

    }

    useEffect(() =>{
        if(params.name){
            getSummoner(params.name);
        }
        
    },[]);

    useEffect(()=>{

    }, [summoner]);


    return (
        <main className="mx-auto flex-row max-w-4xl pt-6 h-fit" style={{width:'896px'}}>

            {
                summoner ? 
                <>
                <section className="flex flex-row mt-1">
                    <div className="h-24 w-24">
                        <img src={"https://ddragon.leagueoflegends.com/cdn/12.12.1/img/profileicon/"+ summoner.profileIconId+ ".png"}/>

                    </div>

                    <div className="pl-4">
                        <p className="text-xl font-bold">{summoner.name}</p>
                        <p className="text-sm">Lv.{summoner.summonerLevel}</p>
                        <div className="relative mt-2">
                            <button type="button" className="hover:bg-blue-400 bg-blue-500 text-white py-2 px-4 rounded" onClick={handleClick}>
                                전적갱신
                            </button>
                        </div>
                    </div>
                </section>



                <section className="relative w-full mt-4 pb-0 bg-white">
                    <Profile summoner={summoner}/>

                    <article>

                    </article>
                </section>

                <MatchList summoner={summoner}/>
                </>
                : <div>없습니다</div>
                
            }
        </main>
    )


}

export default Content;