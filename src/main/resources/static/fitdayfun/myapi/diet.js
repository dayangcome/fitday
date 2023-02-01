function getdietList (params) {
    return $axios({
        url: '/diet/page',
        method: 'get',
        params
    })
}