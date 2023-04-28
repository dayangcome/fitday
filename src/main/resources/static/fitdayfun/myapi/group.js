function addgroup(data) {
    return $axios({
        'url': '/group/add',
        'method': 'post',
        data
    })
}

function addmysend(uid,groupid,content,prepare1) {
    return $axios({
        url: `/group/addd/${uid}/${groupid}/${content}/${prepare1}`,
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

function joinmygroup(groupid,uid) {
    return $axios({
        url: `/group/join/${groupid}/${uid}`,
        method: 'get'
    })
}

function lout(uid) {
    return $axios({
        url: `/group/out/${uid}`,
        method: 'get'
    })
}