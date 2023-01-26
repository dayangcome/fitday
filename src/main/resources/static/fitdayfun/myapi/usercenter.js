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