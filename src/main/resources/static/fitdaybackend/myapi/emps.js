function getEmpList (params) {
    return $axios({
        url: '/employee/page',
        method: 'get',
        params
    })
}

function freezeemp (eid) {
    return $axios({
        url: `/employee/freeze/${eid}`,
        method: 'get'
    })
}

function delfreezeemp (eid) {
    return $axios({
        url: `/employee/delfreeze/${eid}`,
        method: 'get'
    })
}
function registerempApi (data) {
    return $axios({
        'url': '/employee/register',
        'method': 'post',
        data
    })
}

function delemployee (eid) {
    return $axios({
        url: `/employee/delemp/${eid}`,
        method: 'get'
    })
}


