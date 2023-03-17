import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import moment from 'moment';


export default ({ page }) =>{

    const [board, setBoard] = useState();


    const getBoardList = async () =>{
        page = page ? page : 1;

        
        console.log('getBoardList' + page);
        try{
            const response = await axios.get("/api/board/getBoardList?page=" + page);
            if(response.status === 200){
               
                setBoard(response.data);


            }

        }catch(e){console.log(e)}


    }    

    useEffect(()=>{
        getBoardList();
    },[]);


    return (
        <div className="container mx-auto max-w-4xl my-8">
            <div>공지사항</div>

            <div className="py-2">
                <div>
                    <div>

                    </div>
                    {
                        board && board.map( (e)=>{
                            return (
                                <div className="flex">
                                    <div className="flex flex-row w-1/12 justify-center">{e.boardId}</div>
                                    <div className="flex flex-row w-9/12 justify-start"><Link to={"/board/detail/" + e.boardId}>{e.title}</Link></div>
                                    <div className="flex flex-row w-2/12 justify-center">{moment(new Date(e.createDt)).format('YYYY-MM-DD HH:mm')}</div>
                                </div>
                            )
                        })

                    }

                </div>
                <div>

                </div>
            </div>
        
        </div>
    )



}