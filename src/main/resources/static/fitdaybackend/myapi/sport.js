
function getSportList (params) {
    return $axios({
        url: '/sport/page',
        method: 'get',
        params
    })
}

function addsportApi (data) {
    return $axios({
        'url': '/sport/addsport',
        'method': 'post',
        data
    })
}