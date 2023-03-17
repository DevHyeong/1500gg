import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.js';
import '../css/tailwind.css';
import {BrowserRouter} from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import { Provider } from 'react-redux';
import progress from './store/modules/progress';

const store = configureStore({
    reducer: progress,
});

ReactDOM.render(
    <Provider store = {store} >
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </Provider>
    , document.getElementById('root')
);