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

function getfreezeInfo(account) {
    return $axios({
        'url': `/user/freezeInfo/${account}`,
        'method': 'get'
    })
}

function phonegetfreezeInfo(phone) {
    return $axios({
        'url': `/user/phonefreezeInfo/${phone}`,
        'method': 'get'
    })
}

function userlogoutApi(){
    return $axios({
        'url': '/user/logout',
        'method': 'post',
    })
}