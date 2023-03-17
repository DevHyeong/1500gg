import React from 'react';

import {Routes, Route,Switch} from 'react-router-dom';
import Find from './page/Find';
import Main from './page/Main';
import BoardList from './page/BoardList';
import BoardDetail from './page/BoardDetail';
import Create from './component/Create';
import Multi from './page/Multi';


const App = () =>{

    return (
        <Routes>
            
            <Route path="/" element={<Main/>}/>
            
            <Route path="/find/:name" element={<Find/>}>
                <Route path=":name" element={<Find/>} />
            </Route>

            <Route path="/multi" element={<Multi/>}/>
            <Route path="/multi/:name" element={<Multi/>}>
                <Route path=":name" element={<Multi/>} />
            </Route>
            
            
            <Route path="/admin" element={<Create/>}/>
            <Route path="/board/list" element={<BoardList/>}/>
            <Route path="/board/detail/:id" element={<BoardDetail/>}>
                <Route path=":id" element={<BoardDetail/>} />
            </Route>
                        

        </Routes>
    )

}

export default App;