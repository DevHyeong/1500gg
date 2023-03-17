import React, {useState} from 'react';
import {runes} from "./Common";



export default ({ type, perk }) =>{

    const [hover, setHover] = useState(false);


    /**
     * 
     * @param {*} id
     * @description 
     *  
     */
     const getSubRune = (id) =>{
        
        let info;
        runes && runes.forEach(e=>{
            if(e.id === id){
                info = e;
                return;
            }
        });
        //console.log(img);
        return info;
    }

    const getPriRune = (id, subId) =>{
        
        let info;
        runes && runes.forEach(e=>{
            if(e.id === id){
                e.slots.forEach(s=>{
                    s.runes.forEach(r=>{
                        if(r.id === subId){
                            info = r;
                            return;
                        }
                    });
                });
            }
        });
        return info;
    }
    const rune = type === 'pri' ? getPriRune(perk.style, perk.selections[0].perk) : getSubRune(perk.style);
    
    //<Rune type={} rune={getPriRune(participant.perks.styles[0].style, participant.perks.styles[0].selections[0].perk)}/>
    //<Rune type={} rune={getSubRune(participant.perks.styles[1].style)}/>
    


    return (
        <div>
            <img src={"https://ddragon.leagueoflegends.com/cdn/img/" + rune.icon } 
                className="w-full h-full cursor-pointer"
                onMouseOver={(e)=>{
                    setHover(true);
                }}
                onMouseOut={(e)=>{
                    setHover(false);
                }}
                />
            {hover ? 
            <div className="absolute bg-gray-800 text-xs w-64 p-2 text-white">
                <p className="font-bold mb-4 text-emerald-300">{rune.name}</p>
                <p className="" dangerouslySetInnerHTML={{__html: rune.longDesc }}></p> 
            </div>
            : ''}
        </div>
    );


};