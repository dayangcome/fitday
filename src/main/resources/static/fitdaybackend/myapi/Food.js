function getFoodList (params) {
    return $axios({
        url: '/food/page',
        method: 'get',
        params
    })
}

function addfoodApi (data) {
    return $axios({
        'url': '/food/addfood',
        'method': 'post',
        data
    })
}
