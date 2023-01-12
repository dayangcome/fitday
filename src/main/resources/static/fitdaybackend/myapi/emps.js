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
