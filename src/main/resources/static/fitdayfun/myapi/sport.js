function getSportList (params) {
    return $axios({
        url: '/sport/page',
        method: 'get',
        params
    })
}

function getAllList () {
    return $axios({
        url: '/sport/all',
        method: 'get',
    })
}