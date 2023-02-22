function getdietList (params) {
    return $axios({
        url: '/diet/page',
        method: 'get',
        params
    })
}

function getalldietList () {
    return $axios({
        url: '/diet/getall',
        method: 'get',
    })
}
