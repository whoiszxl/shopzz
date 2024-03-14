import { request } from './config'

export const getArticleViewCount = (id, pageUrl, call) => {
    request.get(`/article/view/${id}?pageUrl=${pageUrl}`, {}).then(result => {
        call(process(result))
    })
}

function process(result) {
    if (result.code === 200) {
        return result.data
    } else {
        console.log(result.msg)
    }
}

export default { getArticleViewCount }