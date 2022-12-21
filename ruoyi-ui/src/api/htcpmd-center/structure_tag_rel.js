import request from '@/utils/request'

// 查询structure 标签关联列表
export function listStructure_tag_rel(query) {
  return request({
    url: '/htcpmd-center/structure_tag_rel/list',
    method: 'get',
    params: query
  })
}

// 查询structure 标签关联详细
export function getStructure_tag_rel(id) {
  return request({
    url: '/htcpmd-center/structure_tag_rel/' + id,
    method: 'get'
  })
}

// 新增structure 标签关联
export function addStructure_tag_rel(data) {
  return request({
    url: '/htcpmd-center/structure_tag_rel',
    method: 'post',
    data: data
  })
}

// 修改structure 标签关联
export function updateStructure_tag_rel(data) {
  return request({
    url: '/htcpmd-center/structure_tag_rel',
    method: 'put',
    data: data
  })
}

// 删除structure 标签关联
export function delStructure_tag_rel(id) {
  return request({
    url: '/htcpmd-center/structure_tag_rel/' + id,
    method: 'delete'
  })
}
