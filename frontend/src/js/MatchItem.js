import React, {useState, useEffect} from 'react';
import axios from 'axios';

import Participants from './Participants';
import Rune from './Rune';
import Detail from './Detail';
import Spell from './Spell';
import {champUrl, itemUrl, toKda, getTimeStamp, getDate} from './Common';



export default ({ puuId, data}) =>{
    

    const [match, setMatch] = useState();
    const [participant, setParticipant] = useState();
    const [detail, setDetail] = useState(false);
    const [hover, setHover] = useState(false);


    const getMatch = async () =>{
        try{
            //const response = await axios.get("/api/match/" + matchId);
            const metadata = data.metadata;
            const {gameMode, gameType, participants, teams} = data.info;
            let index;


            // ARAM(450), CLASSIC(420)
            if(gameType !== 'MATCHED_GAME'){
                return;
            }
            participants.forEach( (e,i) =>{

                if(e.puuid === puuId){
                    index = i;
                    return;
                }
            });
           
            setParticipant(participants[index]);
            setMatch(data);
        }catch(e){}
    }

    /**
     *  @descript gameType 변환
     * 
     * 
     */
    const getGameType = (queueId) =>{


        if(queueId === 450){
            return '총력전';
        }else if(queueId === 440){
            return '자랭';
        }
        else if(queueId === 420){
            return '솔랭';
        }else if(queueId === 430){
            return '일반';
        }

        return undefined;


    }

    useEffect(() =>{
        getMatch();

    },[data])


    useEffect(() => {

    }, [match])

    
    return (
        <>
            {    
                (match && participant) &&
                <div className={ (participant.win ? "border-sky-300 bg-sky-300": "border-red-300 bg-red-300") + " flex justify-start h-24 items-center shadow-2xl border-solid border-1 py-4 px-4 mb-2"}>       
                    <div className="text-center w-4">
                        <p className="text-sm">{participant.win ? "승" : "패"}</p>
                    </div>
                    <div className="mx-5 text-center w-12">
                        <p className="text-sm">{getGameType(match.info.queueId)}</p>
                    </div>
                    <div className="text-center mr-3 w-16 text-sm">
                        <p className="timestamp" 
                        onMouseOver={(e)=>{
                            setHover(true);
                        }}
                        onMouseOut={(e)=>{
                            setHover(false);
                        }}>{getTimeStamp(match.info.gameEndTimestamp)}</p>
                        
                        {
                            hover ? 
                            <div className="absolute bg-gray-800 text-xs w-60 p-2 text-white text-left">
                                <p>start: {getDate(match.info.gameStartTimestamp)}</p>
                                <p>end: {getDate(match.info.gameEndTimestamp)}</p>
                            </div> 
                            
                            
                            : ''
                        }


                        <p className="time">{parseInt(match.info.gameDuration/60)}분 {match.info.gameDuration%60}초</p>
                    </div>
                    
                    
                    <div className="h-12 w-12">    
                        <img src={champUrl + participant.championName + ".png"} className="w-full h-full"/>
                    </div>
                    
                    
        
                    <div className="ml-2">
                        <div className="w-6 h-6">
                            <Spell id={participant.summoner1Id}/>    
                        </div>
                        <div className="w-6 h-6">
                            <Spell id={participant.summoner2Id}/>
                        </div>    
                    </div>
        
                    <div>
                        <div className="w-6 h-6">
                            <Rune type={"pri"} perk={participant.perks.styles[0]} />
                        </div>
                        <div className="w-6 h-6">
                            <Rune type={"sub"} perk={participant.perks.styles[1]} />
                        </div>
                        
                    </div>
                   
                    <div className="mx-3">
                        <div className="flex flex-row">
                            <div className="w-8 h-8 p-0.5">
                                <img src={itemUrl + participant.item0 + ".png"} className="w-full h-full"/>
                            </div>
                            <div className="w-8 h-8 p-0.5">
                                <img src={itemUrl + participant.item1 + ".png"} className="w-full h-full"/>
                            </div>
                            <div className="w-8 h-8 p-0.5">
                                <img src={itemUrl + participant.item2 + ".png"} className="w-full h-full"/>
                            </div>
        
                            <div className="w-8 h-8 p-0.5">
                                <img src={itemUrl + participant.item6 + ".png"} className="w-full h-full"/>
                            </div>
                        </div>
                        <div className="flex flex-row">
                            <div className="w-8 h-8 p-0.5">
                                <img src={itemUrl + participant.item3 + ".png"} className="w-full h-full"/>
                            </div>
                            <div className="w-8 h-8 p-0.5">
                                <img src={itemUrl + participant.item4 + ".png"} className="w-full h-full"/>
                            </div>
                            <div className="w-8 h-8 p-0.5">
                                <img src={itemUrl + participant.item5 + ".png"} className="w-full h-full"/>
                            </div>
                            
                            <div className="w-8 h-8 p-0.5 relative">
                                <svg width="20" height="20" fill="currentColor" className="absolute top-1/2 -mt-2 text-slate-400 cursor-pointer group-focus-within:text-blue-500" aria-hidden="true" style={{left:'9px'}}>
                                    <path fill-rule="evenodd" clip-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" />
                                </svg>
                            </div>
                        </div>
                    </div>
                   
                    <div className="mx-3 text-center w-20 text-sm">
                        <p className="kill">{participant.kills}/{participant.deaths}/{participant.assists} ({toKda(participant.kills, participant.assists, participant.deaths)})</p>
                        <p>{participant.totalMinionsKilled + participant.neutralMinionsKilled} ({ ((participant.totalMinionsKilled + participant.neutralMinionsKilled) / parseInt(match.info.gameDuration/60)).toFixed(1)})</p>
                        <p>{
                            match.info.teams.map(e =>{
                                if(participant.teamId === e.teamId){
                                    return parseInt((participant.kills + participant.assists)/e.killsChampion * 100);
                                }
                            })
                        }%</p>
                    </div>
                   
                    <div className="flex w-60">    
                        <Participants p1={match.info.participants[0]} p2={match.info.participants[1]} p3={match.info.participants[2]} p4={match.info.participants[3]} p5={match.info.participants[4]} />
                        <Participants p1={match.info.participants[5]} p2={match.info.participants[6]} p3={match.info.participants[7]} p4={match.info.participants[8]} p5={match.info.participants[9]} />   
                    </div>

                    <div className="relative w-8 h-24 cursor-pointer" onClick={()=>{
                        detail ? setDetail(false) : setDetail(true);
                    }}>
                        <svg width="20" height="20" fill="currentColor" className="cursor-pointer absolute left-3 top-1/2 -mt-2.5 text-slate-400 pointer-events-none group-focus-within:text-blue-500" aria-hidden="true">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" />
                        </svg>
                    </div>

                </div>
                
                
            }
            {
                detail ? <Detail match={match} /> : ''
            }



        </>

        
        
    );
};

