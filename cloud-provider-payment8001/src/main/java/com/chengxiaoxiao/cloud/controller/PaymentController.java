package com.chengxiaoxiao.cloud.controller;

import com.chengxiaoxiao.cloud.entities.CommonResult;
import com.chengxiaoxiao.cloud.entities.Payment;
import com.chengxiaoxiao.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.ResultSet;

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

    @PostMapping("/payment/create")
    public CommonResult create(Payment payment){
        int r = paymentService.create(payment);
        log.info("********插入结果"+r);
        if(r>=1){
            return new CommonResult(200,"数据库插入成功",r);
        }
        else{
            return new CommonResult(444,"数据插入失败");
        }
    }

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult getPaymentById(@PathVariable(name = "id") Long id){

        Payment payment = paymentService.getPaymentById(id);
        log.info("********查询");
        if(payment!=null){
            return new CommonResult(200,"查询成功", payment);
        }
        else{
            return new CommonResult(444,"没有对应记录，查询ID："+id);
        }

    }


}
