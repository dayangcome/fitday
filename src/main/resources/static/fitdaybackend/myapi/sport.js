
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

function delmysport (id) {
    return $axios({
        url: `/sport/delsport/${id}`,
        method: 'get'
    })
}

function changesportApi (sportId,data) {
    return $axios({
        'url': '/sport/changesport',
        'method': 'post',
        data:{sportId,...data}
    })
}