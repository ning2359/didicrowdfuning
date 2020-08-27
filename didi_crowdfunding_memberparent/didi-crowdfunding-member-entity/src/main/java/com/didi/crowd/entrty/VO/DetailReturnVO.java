package com.didi.crowd.entrty.VO;

import java.io.Serializable;

public class DetailReturnVO implements Serializable {
    private static final long serialVersionUID = 7078394581446196282L;
    // 回报信息主键
    private Integer returnId;
    // 当前档位需支持的金额
    private Integer supportMoney;
    // 单笔限购， 取值为 0 时无限额， 取值为 1 时有限额
    private Integer signalPurchase;
    // 具体限额数量
    private Integer purchase;
    // 当前该档位支持者数量
    private Integer supproter;
    // 运费， 取值为 0 时表示包邮
    private Integer freight;
    // 众筹成功后多少天发货
    private Integer returnDate;
    // 回报内容
    private String content;

    public DetailReturnVO() {

    }

    public DetailReturnVO(Integer returnId, Integer supportMoney, Integer signalPurchase, Integer purchase, Integer supproter, Integer freight, Integer returnDate, String content) {
        this.returnId = returnId;
        this.supportMoney = supportMoney;
        this.signalPurchase = signalPurchase;
        this.purchase = purchase;
        this.supproter = supproter;
        this.freight = freight;
        this.returnDate = returnDate;
        this.content = content;
    }

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public Integer getSupportMoney() {
        return supportMoney;
    }

    public void setSupportMoney(Integer supportMoney) {
        this.supportMoney = supportMoney;
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

    public Integer getSupproter() {
        return supproter;
    }

    public void setSupproter(Integer supproter) {
        this.supproter = supproter;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Integer returnDate) {
        this.returnDate = returnDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DetailReturnVO{" +
                "returnId=" + returnId +
                ", supportMoney=" + supportMoney +
                ", signalPurchase=" + signalPurchase +
                ", purchase=" + purchase +
                ", supproter=" + supproter +
                ", freight=" + freight +
                ", returnDate=" + returnDate +
                ", content='" + content + '\'' +
                '}';
    }
}
