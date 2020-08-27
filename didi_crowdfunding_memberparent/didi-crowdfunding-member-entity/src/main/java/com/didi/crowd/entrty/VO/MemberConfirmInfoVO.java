package com.didi.crowd.entrty.VO;

import java.io.Serializable;

public class MemberConfirmInfoVO implements Serializable {
    private static final long serialVersionUID = 7015512103928034801L;
    // 易付宝账号
    private String paynum;
    // 法人身份证号
    private String cardnum;

    public MemberConfirmInfoVO() {
    }

    public MemberConfirmInfoVO(String paynum, String cardnum) {
        this.paynum = paynum;
        this.cardnum = cardnum;
    }

    public String getPaynum() {
        return paynum;
    }

    public void setPaynum(String paynum) {
        this.paynum = paynum;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    @Override
    public String toString() {
        return "MemberConfirmInfoVO{" +
                "paynum='" + paynum + '\'' +
                ", cardnum='" + cardnum + '\'' +
                '}';
    }
}
