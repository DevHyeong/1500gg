import { createAction, handleActions } from "redux-actions";


const LOGIN = 'user/LOGIN';
const LOGOUT = 'user/LOGOUT';
export const login = createAction(LOGIN);
export const logout = createAction(LOGOUT);

const initialState = {
    id: '',
    nickname: '',
    socialType: '',
    accessToken: '',
    authenticated: false
}

const user = handleActions({
    [LOGIN] : (state, { payload: {id, nickname, socialType, accessToken, authenticated} }) => ({ 
        //...state,
        id,
        nickname,
        socialType,
        accessToken,
        authenticated
    }),
    [LOGOUT] : (state, action) => ({ 
        initialState
     }),
 }, initialState,);
 
 export default user;