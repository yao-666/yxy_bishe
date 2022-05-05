import request from '@/utils/request'

// 查询订单明细列表
export function listOrderItem(query) {
  return request({
    url: '/neuqer/orderItem/list',
    method: 'get',
    params: query
  })
}

// 查询订单明细详细
export function getOrderItem(id) {
  return request({
    url: '/neuqer/orderItem/' + id,
    method: 'get'
  })
}

// 新增订单明细
export function addOrderItem(data) {
  return request({
    url: '/neuqer/orderItem',
    method: 'post',
    data: data
  })
}

// 修改订单明细
export function updateOrderItem(data) {
  return request({
    url: '/neuqer/orderItem',
    method: 'put',
    data: data
  })
}

// 删除订单明细
export function delOrderItem(id) {
  return request({
    url: '/neuqer/orderItem/' + id,
    method: 'delete'
  })
}
