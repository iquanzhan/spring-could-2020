package com.chengxiaoxiao.springcloud.entities;

import lombok.*;

import java.io.Serializable;

/**
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020/3/27 10:16 下午
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
