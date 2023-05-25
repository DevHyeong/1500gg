import React from 'react';

import {Routes, Route,Switch} from 'react-router-dom';
import Find from './page/Find';
import Main from './page/Main';
import Multi from './page/Multi';
import LoginPage from './page/login/LoginPage';
import JoinPage from './page/login/JoinPage';
import Success from './store/containers/login/Success';
import Logout from './page/login/Logout';
import PostPage from './page/post/PostPage';


const App = () =>{

    return (
        <Routes>
            
            <Route path="/" element={<Main/>}/>
            <Route path="/login" element={<LoginPage/>}/> 
            <Route path="/join" element={<JoinPage/>} />
            <Route path="/success" element={ <Success/> } />
            <Route path="/logout" element={ <Logout/>} />
            
            <Route path="/find/:name" element={<Find/>}>
                <Route path=":name" element={<Find/>} />
            </Route>

            <Route path="/multi" element={<Multi/>}/>
            <Route path="/multi/:name" element={<Multi/>}>
                <Route path=":name" element={<Multi/>} />
            </Route>
            
            <Route path="/posts" element={<PostPage/>}/>

        </Routes>
    )

}

export default App;