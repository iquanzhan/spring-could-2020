package com.chengxiaoxiao.springcloud.controller;

import com.chengxiaoxiao.springcloud.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * PaymentController
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020-04-19 21:15
 */
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentOk(@PathVariable("id") Integer id) {
        return paymentService.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentTimeout(@PathVariable("id") Integer id) {
        return paymentService.paymentInfo_TimeOut(id);
    }


}
