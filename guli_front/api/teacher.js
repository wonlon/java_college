import request from '@/util/request'
const api_name = '/teacher'


export default {
    getPageList(page, limit) {
      return request({
        url: `${api_name}/${page}/${limit}`,
        method: 'get'
      })
    },
    getTeacherInfoId(id) {
        return request({
          url: `${api_name}/${id}`,
          method: 'get'
        })
      }
  }
