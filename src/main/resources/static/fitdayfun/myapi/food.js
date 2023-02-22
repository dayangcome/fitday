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
