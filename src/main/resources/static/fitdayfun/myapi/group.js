function addgroup(data) {
    return $axios({
        'url': '/group/add',
        'method': 'post',
        data
    })
}