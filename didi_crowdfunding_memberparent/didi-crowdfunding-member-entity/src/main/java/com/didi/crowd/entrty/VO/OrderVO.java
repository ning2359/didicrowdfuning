package com.didi.crowd.entrty.VO;

import java.io.Serializable;

public class OrderVO implements Serializable {
    private static final long serialVersionUID = -5675161742102934249L;
    // 主键
    private Integer id;
    // 订单号
    private String orderNum;
    // 支付宝流水单号
    private String payOrderNum;
    // 订单金额
    private Double orderAmount;
    // 是否开发票
    private Integer invoice;
    // 发票抬头
    private String invoiceTitle;
    // 备注
    private String orderRemark;
    // 支付时间
    private String payTime;

    private Integer memberId;

    private String addressId;

    private OrderProjectVO orderProjectVO;

    public OrderVO() {
    }

    public OrderVO(Integer id, String orderNum, String payOrderNum, Double orderAmount, Integer invoice, String invoiceTitle, String orderRemark, String payTime, Integer memberId, String addressId, OrderProjectVO orderProjectVO) {
        this.id = id;
        this.orderNum = orderNum;
        this.payOrderNum = payOrderNum;
        this.orderAmount = orderAmount;
        this.invoice = invoice;
        this.invoiceTitle = invoiceTitle;
        this.orderRemark = orderRemark;
        this.payTime = payTime;
        this.memberId = memberId;
        this.addressId = addressId;
        this.orderProjectVO = orderProjectVO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public OrderProjectVO getOrderProjectVO() {
        return orderProjectVO;
    }

    public void setOrderProjectVO(OrderProjectVO orderProjectVO) {
        this.orderProjectVO = orderProjectVO;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", payOrderNum='" + payOrderNum + '\'' +
                ", orderAmount=" + orderAmount +
                ", invoice=" + invoice +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", payTime='" + payTime + '\'' +
                ", memberId=" + memberId +
                ", addressId='" + addressId + '\'' +
                ", orderProjectVO=" + orderProjectVO +
                '}';
    }
}
