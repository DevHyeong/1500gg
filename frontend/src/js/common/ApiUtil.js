import axios from "axios";


export const _post = async(url, params) => {
    const user = JSON.parse(localStorage.getItem("user"));
    const response = await axios.post(url, 
        params,
        {
            headers: {
                Authorization : "Bearer " + user.accessToken
            }
        }
    )
    return response;
}

export const get = async(url, params) => {
    const user = JSON.parse(localStorage.getItem("user"));
    const response = await axios.get(url, {
        params : params,
        headers : {
            Authorization : "Bearer " + user.accessToken
        }
    })
    return response;
}