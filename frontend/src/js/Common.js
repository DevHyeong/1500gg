import axios from 'axios';
import moment from 'moment';

const version = "12.12.1";

let champObj = {};

const data = async(url) =>{
    try{
        const response = await axios.get(url);
        
        for (const [key, value] of Object.entries(response.data.data)){
            champObj[value.key] = key;

        }
    }catch(e){console.error(e)} 
};

export const champUrl = `https://ddragon.leagueoflegends.com/cdn/${version}/img/champion/`;
export const itemUrl = `https://ddragon.leagueoflegends.com/cdn/${version}/img/item/`;
export const spellUrl = `https://ddragon.leagueoflegends.com/cdn/${version}/img/spell/`;

export let runes, spell;

export const toKda = (kill, assist, death) =>{
    return ((kill + assist) / death).toFixed(2);
}

export const getChampionNameById = (id) =>{
    return champObj[id];

};

export const set = () =>{
    const url = `http://ddragon.leagueoflegends.com/cdn/${version}/data/en_US/champion.json`;

    data(url);
    setRunesReforged();
    setSpell();
}


const setRunesReforged = async () =>{
    try{
        const response = await axios.get(`https://ddragon.leagueoflegends.com/cdn/${version}/data/ko_KR/runesReforged.json`);
        if(response.status === 200){
            runes = response.data;
        }

    }catch(e){}
}

const setSpell = async () =>{
    try{
        const response = await axios.get(`https://ddragon.leagueoflegends.com/cdn/${version}/data/ko_KR/summoner.json`);
        if(response.status === 200){
           spell = response.data.data;
        }

    }catch(e){}
}

export const getDate = (timestamp) =>{

    return moment(new Date(timestamp)).format("yyyy년 MM월 DD일 HH시 mm분 ss초");

}

    

/**
 * @description 변환함수
 * @param {*} timestamp 
 * @returns 
 */
export const getTimeStamp = (timestamp) =>{
    const current = moment(new Date()); 
    const date = moment(new Date(timestamp));
    const duration = moment.duration(current.diff(date));
    const minute = parseInt(duration.asMinutes());
    const hour = parseInt(duration.asHours());
    const day = parseInt(duration.asDays());
    let text = `${minute}분전`;

    if(minute > 59){
        text = `${hour}시간전`;
    }

    if(hour > 23){
        text = `${day}일전`;
    }
    
    return text;
}