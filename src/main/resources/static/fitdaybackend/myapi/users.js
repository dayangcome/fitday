function getUserList (params) {
    return $axios({
        url: '/user/page',
        method: 'get',
        params
    })
}

function getmyuser(uid) {
    return $axios({
        'url': `/user/getuser/${uid}`,
        'method': 'get'
    })
}

function freezeuserApi (reason,des,id,eid) {
    return $axios({
        url: '/user/freeze',
        method: 'put',
        data: {reason,des,id,eid}
    })
}

function delfreezeuser (id) {
    return $axios({
        url: `/user/delfreeze/${id}`,
        method: 'get'
    })
}
