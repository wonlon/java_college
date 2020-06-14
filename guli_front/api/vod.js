import request from '@/util/request'
const api_name = '/teacher'


export default {
    getPageList(page, limit, searchObj) {
      return request({
        url: `${api_name}/${page}/${limit}`,
        method: 'post',
        data: searchObj
      })
    }
  }
