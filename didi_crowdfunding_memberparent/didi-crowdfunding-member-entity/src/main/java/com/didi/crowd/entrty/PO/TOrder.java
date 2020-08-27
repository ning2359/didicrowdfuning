package com.didi.crowd.entrty.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-07-08
 */
public class TOrder implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 支付宝流水号
     */
    private String payOrderNum;

    /**
     * 订单金额
     */
    private Double orderAmount;

    /**
     * 是否开发票（0 不开， 1 开） 
     */
    private Integer invoice;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 收货地址 id
     */
    private String addressId;
    /**
     * 支付时间
     */
    private String payTime;

    public TOrder() {
    }

    public TOrder(Integer id, String orderNum, String payOrderNum, Double orderAmount, Integer invoice, String invoiceTitle, String orderRemark, String addressId, String payTime) {
        this.id = id;
        this.orderNum = orderNum;
        this.payOrderNum = payOrderNum;
        this.orderAmount = orderAmount;
        this.invoice = invoice;
        this.invoiceTitle = invoiceTitle;
        this.orderRemark = orderRemark;
        this.addressId = addressId;
        this.payTime = payTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
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

    @Override
    public String toString() {
        return "TOrder{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", payOrderNum='" + payOrderNum + '\'' +
                ", orderAmount=" + orderAmount +
                ", invoice=" + invoice +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", addressId='" + addressId + '\'' +
                ", payTime='" + payTime + '\'' +
                '}';
    }
}
