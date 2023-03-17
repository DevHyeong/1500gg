import React, { useEffect, useState } from "react";
import Rune from "./Rune.js";
import Spell from "./Spell.js";
import axios from 'axios';
import { getChampionNameById, champUrl, itemUrl } from './Common';


export default ({ info, match, team, max }) =>{

    const getTier = async () =>{
        try{
            console.log(team.length);
            for(const participant of team){
                console.log(participant);
                const response = await axios.get("/api/users/league/" + participant.summonerId);
                const data = response.filter(e=> e.queueType === "RANKED_SOLO_5x5");
                console.log(data);
            }
            //console.log(tier);
            //return `${tier[0].tier} ` + `${tier[0].rank}`;

        }catch(e){}

        return "test";
    }

    const toKda = (kill, assist, death) =>{
        return ((kill + assist) / death).toFixed(2);

    }

    useEffect(() =>{
        console.log(match);
        
        console.log(team);
        //getTier();
    },[]);

    return (
        <>
            <table className="w-full">
                <colgroup>
                    
                    <col width={"*"}/>
                    <col width={"8%"}/>
                    <col width={"15%"}/>
                    <col width={"10%"}/>
                    <col width={"23%"}/>
                    <col width={"15%"}/>
                    <col width={"8%"}/>
                </colgroup>
                <thead className="bg-slate-200">
                    <tr className="text-left text-gray-500">
                        <th className="text-sm py-2 pl-2">{info.teamId === 100 ? "블루팀" : "레드팀"}
                        {info.win ? <p className="text-blue-600 inline-block"> (승리)</p> : <p className="text-red-600 inline-block"> (패배)</p>}</th>
                        <th className="text-sm py-2">스펠/룬</th>
                        <th className="text-sm py-2">kda</th>
                        <th className="text-sm py-2">cs</th>
                        <th className="text-sm py-2">아이템</th>
                        <th className="text-sm py-2">딜량</th>
                        <th className="text-sm py-2">와드</th>
                    </tr>
                </thead>
                <tbody className={info.teamId === 100 ? "bg-sky-200" : "bg-red-200"}>
                    {
                        team.map(p =>(

                            <tr>
                               
                                <td className="pl-2">
                                    <div className="flex items-center">
                                        <div className="w-5 h-5">
                                            <img src={champUrl + p.championName + ".png"} className="w-full h-full"/>
                                        </div>
                                        <p className="text-xs"><a href={"/find/" + p.summonerName} target="_blank">{p.summonerName}</a></p> 

                                        

                                    </div>
                                </td>
                                <td>
                                    <div className="flex items-center justify-items-center">
                                        <div>
                                            <div className="w-4 h-4">
                                                <Spell id={p.summoner1Id}/>    
                    
                                            </div>
                                            <div className="w-4 h-4">
                                                <Spell id={p.summoner2Id}/>    
                                            </div> 
                                        </div>
                                        <div>   
                                            <div className="w-4 h-4">
                                                <Rune type={"pri"} perk={p.perks.styles[0]} />
                                            </div>
                                            <div className="w-4 h-4">
                                                <Rune type={"sub"} perk={p.perks.styles[1]} />
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <p className="text-xs">{p.kills}/{p.deaths}/{p.assists} ({toKda(p.kills, p.assists, p.deaths)})</p>

                                </td>
                                <td><p className="text-xs">{p.totalMinionsKilled + p.neutralMinionsKilled} ({ ((p.totalMinionsKilled + p.neutralMinionsKilled) / parseInt(match.info.gameDuration/60)).toFixed(1)})</p></td>
                                <td>
                                    <div className="flex flex-row">
                                        {
                                            [0,1,2,3,4,5,6].map(e=> (
                                                <div className="w-5 h-5">
                                                    <img src={itemUrl + p["item"+e] + ".png"} className="w-full h-full"/>
                                                </div>
                                            ))
                                        }
                                    </div>

                                </td>
                                <td>
                                    <p className="text-xs">{p.totalDamageDealtToChampions}</p>
                                    <div className="w-full h-2 border-solid border-1 border-gray-300">
                                        <div className={"h-full bg-red-900 w-2/4"} style={{width: ((p.totalDamageDealtToChampions/max)*100).toFixed(2) + "%"}}></div>
                                    </div>
                                </td>
                                <td>
                                    <p></p>
                                    <p className="text-xs">{p.wardsPlaced}/{p.wardsKilled}</p>

                                </td>
                            </tr>
                        ))

                    }
                    

                </tbody>

            </table>
            <div className="flex flex-row">
            {
                
                info.bans && info.bans.map(e=> (
                    <div className="w-8 h-8">
                        <img src={champUrl + getChampionNameById(e.championId) + ".png"} />
                    </div>
                ))
               

            }
            </div>
            

        </>
    )

}