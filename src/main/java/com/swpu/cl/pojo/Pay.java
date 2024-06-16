package com.swpu.cl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pay")
public class Pay {
    @TableId(value = "pay_id",type = IdType.AUTO)
    private int payId;
    private String tradeNo;
    private String outTradeNo;
    private double totalAmount;
    private String username;
    private String subject;
    private LocalDate payDate;

}
