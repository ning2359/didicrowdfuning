package com.didi.crowd.entrty.VO;

import java.io.Serializable;

public class OrderProjectVO  implements Serializable {
    private static final long serialVersionUID = 5242600523309234531L;

    private Integer id;

    private String projectName;

    private String launchName;

    private String returnContent;

    private Integer returnCount;

    private Integer supportPrice;

    private Integer freight;

    private Integer orderId;

    private Integer signalPurchase;

    private Integer purchase;

    private Integer discount;

    private Integer projectId;

    public OrderProjectVO() {
    }

    public OrderProjectVO(Integer id, String projectName, String launchName, String returnContent, Integer returnCount, Integer supportPrice, Integer freight, Integer orderId, Integer signalPurchase, Integer purchase, Integer discount, Integer projectId) {
        this.id = id;
        this.projectName = projectName;
        this.launchName = launchName;
        this.returnContent = returnContent;
        this.returnCount = returnCount;
        this.supportPrice = supportPrice;
        this.freight = freight;
        this.orderId = orderId;
        this.signalPurchase = signalPurchase;
        this.purchase = purchase;
        this.discount = discount;
        this.projectId = projectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLaunchName() {
        return launchName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    public String getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(String returnContent) {
        this.returnContent = returnContent;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getSupportPrice() {
        return supportPrice;
    }

    public void setSupportPrice(Integer supportPrice) {
        this.supportPrice = supportPrice;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSignalPurchase() {
        return signalPurchase;
    }

    public void setSignalPurchase(Integer signalPurchase) {
        this.signalPurchase = signalPurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "OrderProjectVO{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", launchName='" + launchName + '\'' +
                ", returnContent='" + returnContent + '\'' +
                ", returnCount=" + returnCount +
                ", supportPrice=" + supportPrice +
                ", freight=" + freight +
                ", orderId=" + orderId +
                ", signalPurchase=" + signalPurchase +
                ", purchase=" + purchase +
                ", discount=" + discount +
                ", projectId=" + projectId +
                '}';
    }
}
