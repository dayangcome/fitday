function getVideoList (params) {
    return $axios({
        url: '/uservideo/page',
        method: 'get',
        params
    })
}