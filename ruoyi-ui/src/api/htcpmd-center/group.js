import request from '@/utils/request'

// 查询群组列表
export function listGroup(query) {
  return request({
    url: '/htcpmd-center/group/list',
    method: 'get',
    params: query
  })
}

// 查询群组详细
export function getGroup(id) {
  return request({
    url: '/htcpmd-center/group/' + id,
    method: 'get'
  })
}

// 新增群组
export function addGroup(data) {
  return request({
    url: '/htcpmd-center/group',
    method: 'post',
    data: data
  })
}

// 修改群组
export function updateGroup(data) {
  return request({
    url: '/htcpmd-center/group',
    method: 'put',
    data: data
  })
}

// 删除群组
export function delGroup(id) {
  return request({
    url: '/htcpmd-center/group/' + id,
    method: 'delete'
  })
}
