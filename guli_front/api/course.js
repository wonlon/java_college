import request from '@/util/request'
const api_name = '/course'


export default {
    getAllCourserPageList(page, limit) {
      return request({
        url: `${api_name}/${page}/${limit}`,
        method: 'get',
      })
    },
    getFrontCourseInfo(id) {
        return request({
          url: `${api_name}/${id}`,
          method: 'get',
        })
      },
  }
