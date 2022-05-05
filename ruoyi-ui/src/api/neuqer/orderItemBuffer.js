import request from '@/utils/request'

// 查询订单明细导入缓冲区列表
export function listOrderItemBuffer(query) {
  return request({
    url: '/neuqer/orderItemBuffer/list',
    method: 'get',
    params: query
  })
}

// 查询订单明细导入缓冲区详细
export function getOrderItemBuffer(id) {
  return request({
    url: '/neuqer/orderItemBuffer/' + id,
    method: 'get'
  })
}

// 新增订单明细导入缓冲区
export function addOrderItemBuffer(data) {
  return request({
    url: '/neuqer/orderItemBuffer',
    method: 'post',
    data: data
  })
}

// 修改订单明细导入缓冲区
export function updateOrderItemBuffer(data) {
  return request({
    url: '/neuqer/orderItemBuffer',
    method: 'put',
    data: data
  })
}

// 删除订单明细导入缓冲区
export function delOrderItemBuffer(id) {
  return request({
    url: '/neuqer/orderItemBuffer/' + id,
    method: 'delete'
  })
}
