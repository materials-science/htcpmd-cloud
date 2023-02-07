import request from '@/utils/request'

// 查询server 信息列表
export function listServer(query) {
  return request({
    url: '/htcpmd-center/admin/server/list',
    method: 'get',
    params: query
  })
}

// 查询server 信息详细
export function getServer(id) {
  return request({
    url: '/htcpmd-center/admin/server/' + id,
    method: 'get'
  })
}

// 新增server 信息
export function addServer(data) {
  return request({
    url: '/htcpmd-center/admin/server',
    method: 'post',
    data: data
  })
}

// 修改server 信息
export function updateServer(data) {
  return request({
    url: '/htcpmd-center/admin/server',
    method: 'put',
    data: data
  })
}

// 删除server 信息
export function delServer(id) {
  return request({
    url: '/htcpmd-center/admin/server/' + id,
    method: 'delete'
  })
}
