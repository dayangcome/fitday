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

function getAllList2() {
    return $axios({
        url: '/sport/all2',
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

function getheat (plans) {
    return $axios({
        url: '/sport/getheat',
        method: 'post',
        dataType:"json",
        contentType:"application/json", // 指定这个协议很重要
        data:JSON.stringify(plans) //只有这一个参数，json格式，后台解析为实体，后台可以直接用
    })
}

function getrecom (sport) {
    return $axios({
        url: `/sport/getrecom/${sport}`,
        method: 'get'
    })
}

