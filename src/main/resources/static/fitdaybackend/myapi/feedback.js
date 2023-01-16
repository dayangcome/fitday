function getFeedbackList (params) {
    return $axios({
        url: '/feedback/page',
        method: 'get',
        params
    })
}
function changestatus (id) {
    return $axios({
        url: `/feedback/change/${id}`,
        method: 'get'
    })
}
function delfeedback (id) {
    return $axios({
        url: `/feedback/delfeed/${id}`,
        method: 'get'
    })
}

