package com.chengxiaoxiao.cloud.controller;

import com.chengxiaoxiao.cloud.entities.CommonResult;
import com.chengxiaoxiao.cloud.entities.Payment;
import com.chengxiaoxiao.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020/3/27 10:34 下午
 * @Description:
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int r = paymentService.create(payment);
        log.info("********插入结果" + r);
        if (r >= 1) {
            return new CommonResult(200, "数据库插入成功" + serverPort, r);
        } else {
            return new CommonResult(444, "数据插入失败" + serverPort);
        }
    }

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult getPaymentById(@PathVariable(name = "id") Long id) {

        Payment payment = paymentService.getPaymentById(id);
        log.info("********查询");
        if (payment != null) {
            return new CommonResult(200, "查询成功" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应记录，查询ID：" + id + ",port" + serverPort);
        }

    }


}
