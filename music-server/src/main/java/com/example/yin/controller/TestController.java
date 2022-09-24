package com.example.yin.controller;

import com.example.yin.common.Result;
import com.example.yin.handler.BizException;
import com.example.yin.model.domain.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Slf4j
@Validated
@RestController
public class TestController {
    @PostMapping("/test")
    public String test(@RequestParam("id") @NotNull @Min(1L) Integer id){

        log.info("/test");
        return "成功";
    }
}
