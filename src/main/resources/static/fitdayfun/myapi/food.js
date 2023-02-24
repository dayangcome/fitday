function getFoodList (params) {
    return $axios({
        url: '/food/page',
        method: 'get',
        params
    })
}

function getallFoodList () {
    return $axios({
        url: '/food/getall',
        method: 'get',
    })
}

function breakfastplan (userid,foods) {
    return $axios({
        url: '/food/breakfast',
        method: 'post',
        data:{userid,foods}
    })
}

function lunchplan (userid,foods) {
    return $axios({
        url: '/food/lunch',
        method: 'post',
        data:{userid,foods}
    })
}

function dinnerplan (userid,foods) {
    return $axios({
        url: '/food/dinner',
        method: 'post',
        data:{userid,foods}
    })
}

function adddietplan (userid,foods) {
    return $axios({
        url: '/food/adddiet',
        method: 'post',
        data:{userid,foods}
    })
}

function geteatplan (uid,value2) {
    return $axios({
        url: `/food/geteatplan/${uid}/${value2}`,
        method: 'get',
    })
}

function getmyheat (breakfast,lunch,dinner,adddiet) {
    return $axios({
        url: '/food/getmyheat',
        method: 'post',
        dataType:"json",
        contentType:"application/json", // 指定这个协议很重要
        data:JSON.stringify([...breakfast,...lunch,...dinner,...adddiet]) //只有这一个参数，json格式，后台解析为实体，后台可以直接用
    })
    console.log([...breakfast,...lunch,...dinner,...adddiet])
}
