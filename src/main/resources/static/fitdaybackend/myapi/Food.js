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

function delmyfood (id) {
    return $axios({
        url: `/food/delfood/${id}`,
        method: 'get'
    })
}

function changefoodApi (foodId,data) {
    return $axios({
        'url': '/food/changefood',
        'method': 'post',
        data:{foodId,...data}
    })
}

