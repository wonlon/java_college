package com.guli.teacher.handler;

import com.baomidou.mybatisplus.extension.api.R;
import com.guli.common.result.EduException;
import com.guli.common.result.Result;
import com.guli.common.result.ResultCode;
import com.guli.common.result.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("出错了");
    }

    //特殊异常
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public Result error(BadSqlGrammarException e){
        e.printStackTrace();
        return Result.error().code(ResultCode.SQL_ERROR).message("SQL语法错误");
    }

    //自定义异常
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e){
        e.printStackTrace();
//        log.error(e.getMessage());   这种形式不会输出到文件只是在控制台中打印出来了
        log.error(ExceptionUtil.getMessage(e)); //写入文件了
        return Result.error().code(e.getCode()).message(e.getMsg());
    }


}