import request from '@/utils/request'

// 查询structure 标签信息列表
export function listStructure_tag(query) {
  return request({
    url: '/htcpmd-center/structure_tag/list',
    method: 'get',
    params: query
  })
}

// 查询structure 标签信息详细
export function getStructure_tag(id) {
  return request({
    url: '/htcpmd-center/structure_tag/' + id,
    method: 'get'
  })
}

// 新增structure 标签信息
export function addStructure_tag(data) {
  return request({
    url: '/htcpmd-center/structure_tag',
    method: 'post',
    data: data
  })
}

// 修改structure 标签信息
export function updateStructure_tag(data) {
  return request({
    url: '/htcpmd-center/structure_tag',
    method: 'put',
    data: data
  })
}

// 删除structure 标签信息
export function delStructure_tag(id) {
  return request({
    url: '/htcpmd-center/structure_tag/' + id,
    method: 'delete'
  })
}
