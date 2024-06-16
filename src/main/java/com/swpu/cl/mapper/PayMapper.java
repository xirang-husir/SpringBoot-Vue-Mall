package com.swpu.cl.mapper;

import com.swpu.cl.pojo.Pay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface PayMapper {

    @Insert("INSERT INTO pay (trade_no, out_trade_no, total_amount, username, pay_date,subject) " +
            "VALUES (#{tradeNo}, #{outTradeNo}, #{totalAmount}, #{username}, #{payDate},#{subject})")
    int insertPayInfo(Pay pay);

    @Select("SELECT COALESCE(MAX(out_trade_no), 0) FROM pay")
    double queryLastPay();
}
