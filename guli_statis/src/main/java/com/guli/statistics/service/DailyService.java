package com.guli.statistics.service;

import com.guli.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author guli
 * @since 2020-02-22
 */
public interface DailyService extends IService<Daily> {
    //获取某一天注册人数，把注册人数添加统计分析表
    void createStatisticsByDay(String day);
    //返回进行统计的数据，两个数组
    Map<String, Object> getCountData(String begin, String end, String type);
}
