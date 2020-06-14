package com.guli.ucenter.service;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author guli
 * @since 2020-02-22
 */
public interface MemberService extends IService<Member> {
    /**
     * 统计所有的用户
     * @param day
     * @return
     */
    Integer countRegisterByDay(String day);
}
