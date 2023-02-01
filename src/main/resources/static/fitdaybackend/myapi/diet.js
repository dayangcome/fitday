function getdietList (params) {
    return $axios({
        url: '/diet/page',
        method: 'get',
        params
    })
}

function adddietApi (data) {
    return $axios({
        'url': '/diet/adddiet',
        'method': 'post',
        data
    })
}

function delmydiet (id) {
    return $axios({
        url: `/diet/deldiet/${id}`,
        method: 'get'
    })
}

function changedietApi (dietId,data) {
    return $axios({
        'url': '/diet/changediet',
        'method': 'post',
        data:{dietId,...data}
    })
}