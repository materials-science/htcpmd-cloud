import { request } from '@/api/service' //改成这个请求真实后端
export function GetList (query) {
  return request({
    url: '/accounts/users/',
    method: 'get',
    params: query
  })
}
export function AddObj (obj) {
  return request({
    url: '/accounts/users/',
    method: 'post',
    data: obj
  })
}
export function UpdateObj (obj) {
  return request({
    url: '/accounts/users/',
    method: 'put',
    data: obj
  })
}
export function DelObj (id) {
  return request({
    url: '/accounts/users/',
    method: 'delete',
    params: { id }
  })
}