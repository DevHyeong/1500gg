import React from "react";


export default ({ progress }) =>{

    console.log(progress);
    return (
        <div className="absolute bg-blue-400 h-full text-white py-2 px-4 rounded text-center" style={{width: "20%"}}>
            <span>{progress}</span>
        </div>
    )


};