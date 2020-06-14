package com.guli.statistics.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 在操作数据库的时候根据语句拦截，帮我们自动补充被拦截的语句的属性
 */
@Component
public class DataMetaObjectHandler implements MetaObjectHandler {
    //在执行insert语句的时候被拦截的操作
    @Override
    public void insertFill(MetaObject metaObject) {
        //自动补充teacher对象属性中的数据，isDeleted:Boolean 所以应该是true或者false
//        this.setFieldValByName("idDelete",0,metaObject);
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
    //修改被拦截的操作
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}