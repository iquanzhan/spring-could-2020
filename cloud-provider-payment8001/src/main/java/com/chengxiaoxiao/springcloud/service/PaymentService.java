package com.chengxiaoxiao.springcloud.service;

import com.chengxiaoxiao.springcloud.entities.Payment;

/**
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020/3/27 10:33 下午
 * @Description:
 */
public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(Long id);
}
