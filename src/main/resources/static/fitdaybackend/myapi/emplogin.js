function emploginApi(data) {
  return $axios({
    'url': '/employee/login',
    'method': 'post',
    data
  })
}

function emplogoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}
