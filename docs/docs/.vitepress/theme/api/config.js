import axios from 'axios'

const createBaseInstance = () => {
    const instance = axios.create({
      baseURL: 'https://api.whoiszxl.top/blog',
      timeout: 3000
    })
    instance.interceptors.request.use(handleRequest, handleError)
    instance.interceptors.response.use(handleResponse, handleError)
    return instance
}
export const request = createBaseInstance()

function handleError(e) {
    throw e
}

function handleRequest(request) {
    return request;
}

function handleResponse(response) {
    return response.data
}