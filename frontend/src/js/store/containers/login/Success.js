import React, {useEffect} from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { login } from "../../modules/user";
import qs from 'qs';

const Success = () =>{
    const dispatch = useDispatch();
    const location = useLocation();
    const navigate = useNavigate();
    
    useEffect(() => {
        if(location.state){ // 

            dispatch(login(location.state));
            localStorage.setItem("user", JSON.stringify(location.state));
        }else{
            const query = qs.parse(location.search, {
                ignoreQueryPrefix: true
            });
            const {id, nickname, social_type, userId, access_token} = query;
            const user = {
                id : id,
                userId: userId,
                nickname : nickname,
                provider : social_type,
                accessToken : access_token,
                authenticated : true
            }
            dispatch(login(user));
            localStorage.setItem("user", JSON.stringify(user));       
        }
        navigate('/');
        
    }, [location.state]);

    return (
        <></>
    )
};
export default Success;