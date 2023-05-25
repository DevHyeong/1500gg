import React, { useState } from "react";
import axios from "axios";
import PostItem from "./PostItem";
import PostCreate from "./PostCreate";
import { get } from "../../common/ApiUtil";
import { useNavigate } from "react-router-dom";

const PostList = () =>{
    //const [] = useState(false);
    const [authenticated, setAuthenticated] = useState(true);
    const navigate = useNavigate();

    const isAuthenticated = async() => {
        try{
            const user = JSON.parse(localStorage.getItem("user"));
            const response = await get("/api/user/validateToken", {});
            
            if(response.status === 200 && response.data.body){
                setAuthenticated(response.data.body);
            }else{
                navigate("/login")
            }
        }catch(e){
            console.error(e);
        }
    }

    return (
        <>               
            <div className="mx-auto max-w-4xl pt-7">
                <div className="mb-5 flex flex-row-reverse">
                    <a href="javascript:;" onClick={() => isAuthenticated()}className="bg-sky-500 px-3 py-2 text-sm text-white rounded">글쓰기</a>
                </div>
                {
                    authenticated && <PostCreate/>
                }                  
                <PostItem/>
            </div>
        </>
    )
}

export default PostList;