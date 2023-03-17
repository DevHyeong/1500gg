import React from 'react';
import Header from '../component/Header';
import Footer from '../component/Footer';
import MultiContent from '../component/MultiContent';
import { useParams } from 'react-router-dom';


export default () => {

    let params = useParams();

    return (
        <>
            <Header visible={true} index={0}/>
            <MultiContent params={params}/>
            <Footer/>
        
        </>

    )



};