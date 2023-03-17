import React, {useMemo} from 'react';
import Header from '../component/Header.js';
import List from '../component/board/List';
import { useLocation } from 'react-router-dom';
import Footer from '../component/Footer.js';



export default () =>{

    const { search } = useLocation();
    const query = useMemo(()=>new URLSearchParams(search), [search]);

    return (
        <>
            <Header index={1}/>
            <List page={query.get("page")}/>
            <Footer/>

        </>
    )

}
