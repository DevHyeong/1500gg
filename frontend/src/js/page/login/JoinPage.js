import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import qs from 'qs';
import { set } from "../../Common";


const JoinPage = () =>{
    const [nickname, setNickname] = useState();
    const navigate = useNavigate();
    const query = qs.parse(window.location.search, {
        ignoreQueryPrefix: true
    });
    const signIn = async () =>{
        const result = await validateNickname(nickname);
        if(!result){
            return alert('중복된 닉네임입니다.');
        }

        const response = await axios.post("/api/user/signIn", {
            nickname : nickname,
            accessToken : query.access_token
        });
        if(response.status == 200){
              navigate('/success', {
                 state: response.data.body
             })
        }else{

        }
    }
    const validateNickname = async (name) =>{
        const response = await axios.post("/api/user/validate", {
            nickname: name
        });
        console.log(response);
        return response.data.body;
    }    
    const onChangeNickname = (e) =>{
        setNickname(e.target.value);
    }

    return (
      <> 
        <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
          <div className="sm:mx-auto sm:w-full sm:max-w-sm">
            <img
              className="mx-auto h-10 w-auto"
              src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
              alt="Your Company"
            />
            <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
              Sign in to your account
            </h2>
          </div>
  
          <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <div>
            <label htmlFor="nickname" className="block text-sm font-medium leading-6 text-gray-900">
                닉네임
            </label>
            <div className="mt-2">
                <input
                id="nickname"
                name="nickname"
                type="nickname"
                autoComplete="nickname"
                required
                className="p-4 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                onChange={onChangeNickname}
                />
            </div>
            </div>

            <div>
            <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                onClick={() => signIn()}
            >
                Sign in
            </button>
            </div> 
          </div>
        </div>
      </>
    )
}

export default JoinPage;