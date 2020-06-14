package com.guli.teacher.client;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guli-vod")
@Component
public interface OssSourceClient {
    @DeleteMapping(value = "/video/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);
}