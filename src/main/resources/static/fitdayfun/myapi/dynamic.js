function adddynamic (userId,title,category,content,picture) {
    return $axios({
        url: '/dynamic/add',
        method: 'post',
        data:{userId,title,category,content,picture}
    })
}

function getdynamicList (params) {
    return $axios({
        url: '/dynamic/page',
        method: 'get',
        params
    })
}

function getallList () {
    return $axios({
        url: '/dynamic/all',
        method: 'get'
    })
}

function getonedynamic (theid) {
    return $axios({
        url: `/dynamic/one/${theid}`,
        method: 'get'
    })
}

function deldynamic (id) {
    return $axios({
        url: `/dynamic/del/${id}`,
        method: 'get'
    })
}


function addcomments2(uid,dyId,content) {
    return $axios({
        url: `/dynamicComment/addcomments/${uid}/${dyId}/${content}`,
        method: 'post'
    })
}

function getcomments2(dyId) {
    return $axios({
        url: `/dynamicComment/getcomments/${dyId}`,
        method: 'get'
    })
}
