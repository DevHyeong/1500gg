import React from 'react';
import Header from '../component/Header.js';
import Content from '../component/Content.js';
import { useParams } from 'react-router-dom';
import Footer from '../component/Footer.js';
import HeaderContainer from '../store/containers/common/HeaderContainer.js';



export default () =>{

    let params = useParams();

    return (
        <>
            <HeaderContainer visible={true} index={0}/>
            <Content params={params}/>
            <Footer/>

        </>
    )

}
