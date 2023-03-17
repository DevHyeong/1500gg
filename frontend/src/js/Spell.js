import React from "react";
import {spellUrl, spell} from "./Common";



export default ({ id }) =>{

    const getSpellImgUrl = (id) =>{
        let img;
    
        Object.entries(spell).forEach(([key, value]) => {
            const obj = value;
            if(obj.key == id){
                img = obj.image.full;
                return;
            }
        });
        return img;
    }

    return (
        <img src={spellUrl + getSpellImgUrl(id)} className="w-full h-full"/>
    )

}