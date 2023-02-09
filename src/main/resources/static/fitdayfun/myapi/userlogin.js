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

function getuserinfo(uid){
    return $axios({
        'url': `/user/userinfo/${uid}`,
        'method': 'get'
    })
}

function updatemyinfo(userId,height,weight){
    return $axios({
        'url': '/user/updatemyinfo',
        'method': 'post',
        data:{userId,height,weight}
    })
}
