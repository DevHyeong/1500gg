import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../../store/modules/user";
import axios from "axios";

const Logout = () => {
    const [result, setResult] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const action = async () => {
        const response = await axios.post('/api/user/logout');
        setResult(true);
    }

    useEffect(() => {
        localStorage.removeItem("user");
        dispatch(logout())
        action();   
    }, []);

    useEffect(() => {
        alert('로그아웃 되었습니다.');
        navigate('/login');
    }, [result]);

    return (
        <></>
    )
}
export default Logout;