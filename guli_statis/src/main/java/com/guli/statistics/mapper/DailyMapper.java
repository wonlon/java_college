package com.guli.statistics.mapper;

import com.guli.statistics.entity.Daily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author guli
 * @since 2020-02-22
 */
@Mapper
public interface DailyMapper extends BaseMapper<Daily> {

}
