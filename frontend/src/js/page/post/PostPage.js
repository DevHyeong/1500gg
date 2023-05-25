import React from "react";
import PostList from "../../component/post/PostList";
import HeaderContainer from "../../store/containers/common/HeaderContainer";
import Footer from "../../component/Footer";

const PostPage = () =>{
    return (
        <>
            <HeaderContainer visible={true} index={1}/>
            <PostList/>
            <Footer/>
        </>
    )
}

export default PostPage;