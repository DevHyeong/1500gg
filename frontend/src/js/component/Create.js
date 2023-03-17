import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'
import axios from 'axios';

export default () =>{

    const [value, setValue] = useState('');

    const modules = {
        toolbar: [
          [{ 'header': [1, 2, false] }],
          ['bold', 'italic', 'underline','strike', 'blockquote'],
          [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
          ['link', 'image'],
          ['clean']
        ],
      };
    
    const formats = [
        'header',
        'bold', 'italic', 'underline', 'strike', 'blockquote',
        'list', 'bullet', 'indent',
        'link', 'image'
    ];


    const createNotice = async (e) =>{
        console.log('createNotice');
        try{
            const response = await axios.post("/api/board/create", {
                author : '관리자',
                title : document.querySelector("input[name=title]").value,
                content : value
            });
            if(response.status === 200){
               



            }

        }catch(e){console.log(e)}
    }




    return (
        <>
        <div>
            <input type="text" name="title" className="w-full"/>
        </div>
        <>
            <ReactQuill theme='snow' 
            value={value} 
            modules={modules}
            formats={formats}
            onChange={setValue} 
            style={{height: "600px"}} />

            
        </>
        <div style={{position:"absolute", bottom:"0"}}>
        <button type="button" style={{cursor:"pointer"}}
            onClick={e => {
                createNotice(e);
                console.log(value);

            }}>등록</button>
        </div>
        </>
    )


};
