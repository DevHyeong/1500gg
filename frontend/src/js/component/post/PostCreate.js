import React, {useState} from "react";
import { post } from "../../common/ApiUtil";

const PostCreate = () =>{
    const [post, setPost] = useState();
    
    const createPost =  () => {
        console.log(post);
        //const response = await post("/api/post/create", post);
        //console.log(response);
    }

    const onChangePost = (e) =>{
        const { name, value } = e.target;
        setPost({
            ...post,
            [name]: value
        })
    }
    
    return (
        <>
            <div className="flex flex-col w-full h-28 mb-10">
                <div className="flex">
                    <input type="text" className="w-full border-2 p-1 text-sm" name="title" onChange={onChangePost}/>
                </div>
                <div className="flex mt-1">
                    <textarea className="w-full border-gray-200 border-2 p-1 text-sm" name="content" onChange={onChangePost}/>
                </div>
                <div className="flex flex-row-reverse mt-1">
                    <a href="javascript:;" onClick={() => createPost()} className="bg-sky-500 px-3 py-1 text-sm text-white rounded">등록</a>               
                </div>
            </div>
        </>
    )
}

export default PostCreate;