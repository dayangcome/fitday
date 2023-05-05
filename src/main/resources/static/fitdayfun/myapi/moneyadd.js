
function moneyadd(uid,price,type) {
    return $axios({
        'url': 'recharge/moneyadd',
        'method': 'post',
        data:{uid,price,type}
    })
}

function getmyuser(uid) {
    return $axios({
        'url': `/user/getuser/${uid}`,
        'method': 'get'
    })
}

function getaddmoneyList (params) {
    return $axios({
        url: '/recharge/page',
        method: 'get',
        params
    })
}

function getbuyList (params) {
    return $axios({
        url: '/productOrder/page',
        method: 'get',
        params
    })
}