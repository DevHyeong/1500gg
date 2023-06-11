import React, { useState, useEffect } from "react";
import PostItem from "./PostItem";
import PostCreate from "./PostCreate";
import { get } from "../../common/ApiUtil";
import { useNavigate } from "react-router-dom";

const PostList = () =>{
    //const [] = useState(false);
    const [posts, setPosts] = useState([]);
    const [authenticated, setAuthenticated] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [items, setItems] = useState([]);
    const [error, setError] = useState(null);
    const [id, setId] = useState();
    const navigate = useNavigate();
    
    const getPosts = async () =>{
        setIsLoading(true);
        setError(null);

        try{
            const params = {};
            if(id)
                params['id'] = id;
            params['size'] = 10;
            const response = await get(`/api/posts`, params);
            console.log(response);
            if(response.status === 200){
                let data = response.data.body;
                data = data[data.length - 1];

                setPosts(prevItems => [...prevItems, ...response.data.body]);
                setId(data.id);
            }
        }catch(error){
            setError(error);
        }finally{
            setIsLoading(false);
        }
    }

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

    const handleScroll = () => {
        if (window.innerHeight + document.documentElement.scrollTop !== document.documentElement.offsetHeight || isLoading) {
          return;
        }
        console.log('loading');
        getPosts();
    };

    useEffect(() => {
        getPosts();
    }, [])

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [isLoading]);

    return (
        <>  
        {posts &&              
            <div className="mx-auto max-w-4xl pt-7">
                <div className="mb-5 flex flex-row-reverse">
                    <a href="javascript:;" onClick={() => isAuthenticated()}className="bg-sky-500 px-3 py-2 text-sm text-white rounded">글쓰기</a>
                </div>
                {
                    authenticated && <PostCreate/>
                }                  
                <PostItem items={posts}/>
            </div>
        }
        </>
    )
}

export default PostList;