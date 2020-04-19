package com.chengxiaoxiao.springcloud.service;

import com.chengxiaoxiao.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * PaymentFeignService
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020-04-19 16:14
 */
@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult getPaymentById(@PathVariable("id") long id);
}
