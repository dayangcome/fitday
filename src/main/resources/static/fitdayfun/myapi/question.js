
function getquesList () {
    return $axios({
        url: '/question/all',
        method: 'get',
    })
}