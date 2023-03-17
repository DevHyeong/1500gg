import React from 'react';

const champ_url = "https://ddragon.leagueoflegends.com/cdn/12.5.1/img/champion/";

export default ({ p1, p2, p3, p4, p5}) =>{
    
    
    return (
        <div className="ml-1 mr-2 w-1/2">
           
            <div className="flex flex-row">
                <div className="w-4 h-4">
                    <img src={champ_url + p1.championName + ".png"} className="w-full h-full"/>
                </div>
                <p className="text-xs truncate w-24"><a href={"/find/" + p1.summonerName} target="_blank">{p1.summonerName}</a></p>
            </div>
             
            <div className="flex flex-row">
                <div className="w-4 h-4">
                    <img src={champ_url + p2.championName + ".png"} className="w-full h-full"/>
                </div>
                <p className="text-xs truncate w-24"><a href={"/find/" + p2.summonerName} target="_blank">{p2.summonerName}</a></p>
            </div>

            <div className="flex flex-row">
                <div className="w-4 h-4">
                    <img src={champ_url + p3.championName + ".png"} className="w-full h-full"/>
                </div>
                <p className="text-xs truncate w-24"><a href={"/find/" + p3.summonerName} target="_blank">{p3.summonerName}</a></p>
            </div>

            <div className="flex flex-row">
                <div className="w-4 h-4">
                    <img src={champ_url + p4.championName + ".png"} className="w-full h-full"/>
                </div>
                <p className="text-xs truncate w-24"><a href={"/find/" + p4.summonerName} target="_blank">{p4.summonerName}</a></p>
            </div>

            <div className="flex flex-row">
                <div className="w-4 h-4">
                    <img src={champ_url + p5.championName + ".png"} className="w-full h-full"/>
                </div>
                <p className="text-xs truncate w-24"><a href={"/find/" + p5.summonerName} target="_blank">{p5.summonerName}</a></p>
            </div>

        </div>
    );

};