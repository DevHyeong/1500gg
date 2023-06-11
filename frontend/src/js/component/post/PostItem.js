import React, { useState, useEffect, useRef} from "react";
import moment from "moment";

const PostItem = ({ items }) => {
    const user = JSON.parse(localStorage.getItem("user"));
    
    return (
        <>
            {items.map(item => (
            <div className="box-content shadow-lg flex flex-col w-full mb-6">
                <div className="flex pt-6 pb-6 pl-3">
                    <p className="w-7/12 font-bold text-sm text-ellipsis whitespace-nowrap overflow-hidden">{item.title}</p>
                    <div className="flex w-2/12 justify-end">
                        <span className="text-sm">{item.nickname}</span>
                    </div>
                    <div className="flex w-3/12 justify-center">
                        <span className="text-sm">{moment(item.createdAt).format("YYYY-MM-DD HH:mm:ss")}</span>
                    </div>
                </div>
                <div className="pl-3 pb-3 h-24">
                    <p className="text-sm text-ellipsis whitespace-nowrap overflow-hidden">{item.content}</p>
                </div>
            </div>
            ))}            
        </>
    )
}

export default PostItem;