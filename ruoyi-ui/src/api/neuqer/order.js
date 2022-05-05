import request from '@/utils/request'
import { getToken } from '@/utils/auth'

// 查询订单列表列表
export function listOrder(query) {
  return request({
    url: '/neuqer/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单列表详细
export function getOrder(id) {
  return request({
    url: '/neuqer/order/' + id,
    method: 'get'
  })
}

// 新增订单列表
export function addOrder(data) {
  return request({
    url: '/neuqer/order',
    method: 'post',
    data: data
  })
}

// 修改订单列表
export function updateOrder(data) {
  return request({
    url: '/neuqer/order',
    method: 'put',
    data: data
  })
}

// 删除订单列表
export function delOrder(id) {
  return request({
    url: '/neuqer/order/' + id,
    method: 'delete'
  })
}

// 删除订单列表
export function uploadOrder(formData,updateSupport,isImport) {
  return request({
  headers: { "isToken": true,
    "Content-Type": "multipart/form-data" },
    url: '/neuqer/order/importData' + '?updateSupport=' + updateSupport + '&isImport=' + isImport,
    method: 'post',
    data: formData
  })
}
