import axios from "axios";
import { useDispatch } from 'react-redux';
import {start, finish} from "../store/modules/progress";
import { useCallback } from "react";

const progressAxios = axios.create();


let progress = 0;
let timeout;

const setProgress = (progress) =>{
    console.log('ddd');
    const dispatch = useDispatch();
    const increaseCounter = useCallback(() => dispatch(finish()), []);
    increaseCounter();

   
    //const progress12 = useSelector(state => state.progress);
    //console.log(progress12);

};





progressAxios.interceptors.request.use(config =>{

    setProgress(0);
    /*timeout = setTimeout(()=>{

        if(progress < 100){
            setProgress(20);
           // dispatch(start());

        }

    }, 100);
*/

    return config;
}, error =>{

    return Promise.reject(error);
});


progressAxios.interceptors.response.use(config =>{
    //setProgress(100);
    //dispatch(finish());
    setProgress(100);
    console.log('ddd');


    return config;
}, error =>{

    return Promise.reject(error);
});

export default progressAxios;