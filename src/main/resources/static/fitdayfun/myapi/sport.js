function getSportList (params) {
    return $axios({
        url: '/sport/page',
        method: 'get',
        params
    })
}

function getAllList () {
    return $axios({
        url: '/sport/all',
        method: 'get',
    })
}

function getplanList (uid,mydate) {
    return $axios({
        url: `/sport/plan/${uid}/${mydate}`,
        method: 'get',
    })
}

function addplanList (uid,context) {
    return $axios({
        url: `/sport/addplan/${uid}/${context}`,
        method: 'get'
    })
}

function myplandel (id) {
    return $axios({
        url: `/sport/delplan/${id}`,
        method: 'get'
    })
}

function myfin (id) {
    return $axios({
        url: `/sport/finplan/${id}`,
        method: 'get'
    })
}

