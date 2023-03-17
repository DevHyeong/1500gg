import React, {useMemo} from 'react';
import Header from '../component/Header.js';
import List from '../component/board/List';
import { useParams } from 'react-router-dom';
import Footer from '../component/Footer.js';
import axios from 'axios';



export default () =>{

    let params = useParams();

    console.log(params.id);

    return (
        <>
            <Header index={1}/>
            <div>
                <div></div>

                <div>

                </div>
                <div>

                    
                </div>


            </div>
            <Footer/>
        </>
    )
}