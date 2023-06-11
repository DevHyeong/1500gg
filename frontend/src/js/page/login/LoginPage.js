import React, {useEffect, useState} from "react";
import { useSelector } from "react-redux";
import { NAVER_AUTH_URL, KAKAO_AUTH_URL } from "../../constants";
import { Navigate } from "react-router-dom";

const LoginPage = () =>{
    const user = useSelector(state=> state);
    const toLink = (name) =>{
        const link = name === 'naver' ? NAVER_AUTH_URL : KAKAO_AUTH_URL;
        location.href = link;
    }

    console.log(user);
    return (
        <>
        {
            user.authenticated && <Navigate to={"/"}/>
        }
        <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-sm">
            <img
                className="mx-auto h-10 w-auto"
                src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                alt="Your Company"
            />
            <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
                로그인
            </h2>
            </div>

            <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <div className="flex justify-center">
                <img
                    src="images/login/btn/naver-login-btn.png"
                    className="w-10 h-10 m-3 cursor-pointer" onClick={()=> toLink('naver')}
                />
            
                <img
                    src="/images/login/btn/kakao-login-btn.png"
                    className="w-10 h-10 m-3 cursor-pointer" onClick={()=> toLink('kakao')}
                />
                
            </div>
            
            <p className="mt-10 text-center text-sm text-gray-500">
                Not a member?{' '}
                <a href="#" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
                Start a 14 day free trial
                </a>
            </p>
            </div>
        </div>    
        </>    
        
    )


}

export default LoginPage;