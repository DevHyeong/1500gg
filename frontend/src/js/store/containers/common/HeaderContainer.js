import React from "react";
import { useSelector } from "react-redux";
import Header from "../../../component/Header";

const HeaderContainer = ({ visible, index }) =>{
    const user = useSelector(state => state);
    console.log(user);

    return <Header user={user} visible={visible} index={index}/>
}
export default HeaderContainer;