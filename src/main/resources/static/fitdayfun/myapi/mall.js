function gettypeall(type) {
    return $axios({
        'url': `/product/all/${type}`,
        'method': 'get'
    })
}

function buygoods(uid,proid,type,num,mycut,finalpay,address) {
    return $axios({
        'url': 'product/buy',
        'method': 'post',
        data:{uid,proid,type,num,mycut,finalpay,address}
    })
}
