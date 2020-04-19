package com.chengxiaoxiao.springcloud.controller;

import com.chengxiaoxiao.springcloud.entities.CommonResult;
import com.chengxiaoxiao.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OrderFeignController
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020-04-19 16:17
 */
@RestController
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}",produces = "application/json;charset=UTF-8")
    public CommonResult getPaymentById(@PathVariable("id") long id) {
        return paymentFeignService.getPaymentById(id);
    }
}
