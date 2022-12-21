import request from '@/utils/request'

// 查询structure 信息列表
export function listStructure(query) {
  return request({
    url: '/htcpmd-center/structure/list',
    method: 'get',
    params: query
  })
}

// 查询structure 信息详细
export function getStructure(id) {
  return request({
    url: '/htcpmd-center/structure/' + id,
    method: 'get'
  })
}

// 新增structure 信息
export function addStructure(data) {
  return request({
    url: '/htcpmd-center/structure',
    method: 'post',
    data: data
  })
}

// 修改structure 信息
export function updateStructure(data) {
  return request({
    url: '/htcpmd-center/structure',
    method: 'put',
    data: data
  })
}

// 删除structure 信息
export function delStructure(id) {
  return request({
    url: '/htcpmd-center/structure/' + id,
    method: 'delete'
  })
}
