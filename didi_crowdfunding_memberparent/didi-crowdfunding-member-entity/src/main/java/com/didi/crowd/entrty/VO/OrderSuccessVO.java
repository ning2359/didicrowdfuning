package com.didi.crowd.entrty.VO;

import java.io.Serializable;

public class OrderSuccessVO implements Serializable {
    private static final long serialVersionUID = 6462811639515500604L;
    // 订单号
    private String orderNum;
    // 支付宝流水单号
    private String payOrderNum;
    // 订单金额
    private Double orderAmount;

    public OrderSuccessVO() {
    }

    public OrderSuccessVO(String orderNum, String payOrderNum, Double orderAmount) {
        this.orderNum = orderNum;
        this.payOrderNum = payOrderNum;
        this.orderAmount = orderAmount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(String payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "OrderSuccessVO{" +
                "orderNum='" + orderNum + '\'' +
                ", payOrderNum='" + payOrderNum + '\'' +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
