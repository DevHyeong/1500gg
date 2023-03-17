import React, {useState, useEffect} from 'react';
import Header from '../component/Header.js';
import Footer from '../component/Footer.js';
import axios from 'axios';
import {BoardContent} from '../../css/Styles'


export default () => {

    const [board, setBoard] = useState();

    const getBoardList = async () =>{

        try{
            const response = await axios.get("/api/board/getBoardList");
            if(response.status === 200){
               
                setBoard(response.data);


            }

        }catch(e){console.log(e)}


    }

    
    const handleClick = (e) =>{
        console.log(e);
        location.href = "/board/detail/" + e.boardId;
    }
    

    useEffect(()=>{
        getBoardList();
    },[]);


    return (
        <>
            <Header/>
            <div className="mx-auto my-60 flex flex-col content-center">
                <div className="flex mx-auto max-w-4xl">
                    <div className="relative" style={{width:"420px"}}>
                        <svg width="20" height="20" fill="currentColor" className="absolute left-3 top-1/2 -mt-2 text-slate-400 pointer-events-none group-focus-within:text-blue-500" aria-hidden="true">
                            <path fill-rule="evenodd" clip-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" />
                        </svg>
                        <input className="w-full focus:ring-2 focus:ring-blue-500 focus:outline-none appearance-none text-sm leading-6 text-slate-900 placeholder-slate-400 py-2 pl-10 ring-1 ring-slate-200 shadow-sm" 
                        type="text" name="summoner" aria-label="Filter projects" placeholder="소환사명 검색"/>
                    </div>
                    <div className="ml-1 bg-blue-500 text-center text-white text-sm font-medium pl-2 pr-3 py-2 cursor-pointer shadow-sm hover:bg-blue-400" 
                    style={{width: "80px"}}
                    
                    onClick={ e=>{
                        let name = document.querySelector("input[name=summoner]").value;
                        location.href = "/find/" + name;
                    }} >
                        검색
                    </div>

                </div>  
            </div>
            <div className="h-60 mx-auto max-w-4xl flex flex-col">
                <h2 className="flex pb-3 font-bold">공지사항</h2>
                <div className="flex">
                {
                    board && board.map( (e, i)=> {
                        if(i < 5){
                            return (
                                <div key={e.boardId} className="bg-white px-2 py-2 w-64 shadow-slate-50 cursor-pointer h-full" 
                                onClick={ () => handleClick(e)}>
                                    <p className="font-bold text-sm">{e.title}</p>
                                    <BoardContent dangerouslySetInnerHTML={{__html: e.content}}/>

                                </div>
                                
                                
                            )
                        }
                        
                    })
                    


                }
                </div>

            </div>


            <Footer/>

        </>
    )

};