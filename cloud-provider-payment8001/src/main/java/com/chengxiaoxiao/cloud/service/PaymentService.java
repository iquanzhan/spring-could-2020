package com.chengxiaoxiao.cloud.service;

import com.chengxiaoxiao.cloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020/3/27 10:33 下午
 * @Description:
 */
public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(Long id);
}
