
import { createAction, handleActions } from 'redux-actions';


const START = `progress/START`;
const FINISH = `progress/FINISH`;

export const start = createAction(START);
export const finish = createAction(FINISH);

const initialState = {
    progress : 20,
};

const progress = handleActions({
   [START] : (state, action) => ({ progress : 20 }),
   [FINISH] : (state, action) => { 
        return {
            progress : 100 
        }
    },
}, initialState,);

export default progress;