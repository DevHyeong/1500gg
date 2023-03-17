import React, {useEffect , useState} from 'react';
import MultiItem from './multi/MultiItem';


export default ({ params }) =>{

    const [users, setUsers] = useState();
    const splitName = (name) =>{
        return name.split(","); //님이 방에 참가했습니다.
    };

    const searchMulti = () =>{
        const textarea = document.querySelector("textarea[name=users]").value;
        const split = ["," , "님이 로비에 참가하셨습니다."];

        for(let i =0; i<split.length; i++){
            const list = textarea.split(split[i]);
            console.log(list);
            if(list.length > 0){
                const result = list.filter((e, i)=>{
                    if(e != "" && i < 5){
                        return e.trim();
                    }
                    
                });
                setUsers(result);
                break;
            }
        }




    }

    useEffect(() =>{
        if(params.name){
            setUsers(splitName(params.name));
        }

    },[]);


    useEffect(() =>{

    }, [users])


    return (
        <main className="mx-auto flex flex-col max-w-4xl pt-6 md:pt-12 h-fit">
            
            <div className="flex flex-col mb-8">

                <textarea className="w-full h-28 text-sm" name="users"></textarea>
                <div className="mt-4 text-sm text-white">
                    <button type="button" className="float-right p-2 bg-blue-500" onClick={searchMulti}>여러명 검색하기</button>
                </div>
            </div>
            
            <div className="flex flex-row">
            {
                users && users.map( (e,i) =>{
                   
                    console.log(e);
                    return <MultiItem summonerName = {e}/>
                })            
            } 
            </div>
        </main>
    )



};