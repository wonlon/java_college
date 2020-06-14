package com.guli.ucenter.mapper;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author guli
 * @since 2020-02-22
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    Integer selectRegisterCount(String day);
}
