function getFoodList (params) {
    return $axios({
        url: '/food/page',
        method: 'get',
        params
    })
}