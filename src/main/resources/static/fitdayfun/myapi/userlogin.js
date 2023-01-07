function loginApi(data) {
    return $axios({
        'url': '/user/login',
        'method': 'post',
        data
    })
}
function registerApi(data) {
    return $axios({
        'url': '/user/register',
        'method': 'post',
        data
    })
}
function phoneloginApi(data) {
    return $axios({
        'url': '/user/phonelogin',
        'method': 'post',
        data
    })
}
function nologinfeedbackApi(data) {
    return $axios({
        'url': '/feedback/nologinfeedback',
        'method': 'post',
        data
    })
}