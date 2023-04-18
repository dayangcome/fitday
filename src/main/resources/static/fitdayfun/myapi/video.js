function getVideoList (params) {
    return $axios({
        url: '/uservideo/page',
        method: 'get',
        params
    })
}

function getallvideo () {
    return $axios({
        url: '/uservideo/all2',
        method: 'get'
    })
}

function getonevideo (videoId) {
    return $axios({
        url: `/uservideo/one/${videoId}`,
        method: 'get'
    })
}

function getauthor(videoId) {
    return $axios({
        url: `/user/getbyvideo/${videoId}`,
        method: 'get'
    })
}

function getcomments(videoId) {
    return $axios({
        url: `/videoComments/getcomments/${videoId}`,
        method: 'get'
    })
}

function addcomments(uid,videoId,content) {
    return $axios({
        url: `/videoComments/addcomments/${uid}/${videoId}/${content}`,
        method: 'post'
    })
}

function thumb(num,uid,videoId) {
    return $axios({
        url: `/uservideo/thumb/${num}/${uid}/${videoId}`,
        method: 'get'
    })
}