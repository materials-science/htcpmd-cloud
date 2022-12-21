import request from '@/utils/request'

// 查询Htcpmd业务任务列表
export function listJob(query) {
  return request({
    url: '/htcpmd-center/job/list',
    method: 'get',
    params: query
  })
}

// 查询Htcpmd业务任务详细
export function getJob(id) {
  return request({
    url: '/htcpmd-center/job/' + id,
    method: 'get'
  })
}

// 新增Htcpmd业务任务
export function addJob(data) {
  return request({
    url: '/htcpmd-center/job',
    method: 'post',
    data: data
  })
}

// 修改Htcpmd业务任务
export function updateJob(data) {
  return request({
    url: '/htcpmd-center/job',
    method: 'put',
    data: data
  })
}

// 删除Htcpmd业务任务
export function delJob(id) {
  return request({
    url: '/htcpmd-center/job/' + id,
    method: 'delete'
  })
}
