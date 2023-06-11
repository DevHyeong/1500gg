import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.js';
import '../css/tailwind.css';
import {BrowserRouter} from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import { Provider } from 'react-redux';
import user from './store/modules/user';
import { login } from './store/modules/user';

const store = configureStore({
    reducer: user,
});

const loadUser = () => {
    try{
        const user = localStorage.getItem('user');
        if(user.authenticated) return;

        store.dispatch(login(JSON.parse(user)));

    }catch(e){
        console.error('localStorage is not working');
        let obj = {
            id: '',
            nickname: '',
            socialType: '',
            accessToken: '',
            authenticated: false
        };
        localStorage.setItem('user', JSON.stringify(obj))

    }
}

loadUser();

ReactDOM.render(
    <Provider store = {store} >
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </Provider>
    , document.getElementById('root')
);