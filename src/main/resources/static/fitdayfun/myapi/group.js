function addgroup(data) {
    return $axios({
        'url': '/group/add',
        'method': 'post',
        data
    })
}

function addmysend(uid,groupid,content) {
    return $axios({
        url: `/group/add/${uid}/${groupid}/${content}`,
        method: 'post'
    })
}


function getinfo(groupid) {
    return $axios({
        url: `/group/getcomments/${groupid}`,
        method: 'get'
    })
}

function changeinfo(data) {
    return $axios({
        'url': '/group/change',
        'method': 'post',
        data
    })
}

function getmygroup() {
    return $axios({
        url: `/group/all`,
        method: 'get'
    })
}