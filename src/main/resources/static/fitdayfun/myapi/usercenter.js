function deluser (uid) {
    return $axios({
        url: `/user/deluser/${uid}`,
        method: 'get'
    })
}

function changeuserinfo (uid,avatar,username,phone,sex,wxNumber) {
    return $axios({
        url: 'user/changeuserinfo',
        method: 'put',
        data: {uid,avatar,username,phone,sex,wxNumber}
    })
}

function questchange (uid,number,info) {
    return $axios({
        url: `user/quest/${uid}/${number}/${info}`,
        method: 'get',
    })
}
