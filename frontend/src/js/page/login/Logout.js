import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../../store/modules/user";
import { _post } from "../../common/ApiUtil";

const Logout = () => {
    const [result, setResult] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const action = async () => {
        const response = await _post('/api/user/logout');
        setResult(true);
    }

    useEffect(() => {
        action();
        localStorage.removeItem("user");
        dispatch(logout())           
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