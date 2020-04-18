package com.chengxiaoxiao.springcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * PaymentController
 *
 * @Description:
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020-04-18 15:33
 */
@RestController
public class PaymentController {
    private String url = "http://CLOUD-PROVIDER-PAYMENT";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("consumer/payment/consul")
    public String cpaymentConsul() {
        String forObject = restTemplate.getForObject(url + "/payment/consul", String.class);
        return forObject;
    }
}
