package com.didi.crowd.entrty.VO;

import java.io.Serializable;
import java.util.List;

public class RealNameVO implements Serializable {
    private static final long serialVersionUID = 3272380388251710389L;
    private Integer memberId;
    private  Integer accttype;
    private String realname;
    private String email;
    private String phone;
    private  String cardnum;
    private List<Integer> certIds;
    private List<String> qualificationpctureList;
    private String code;
    private String procdefId;

    public RealNameVO() {

    }

    public RealNameVO(Integer memberId, Integer accttype, String realname, String email, String phone, String cardnum, List<Integer> certIds, List<String> qualificationpctureList, String code, String procdefId) {
        this.memberId = memberId;
        this.accttype = accttype;
        this.realname = realname;
        this.email = email;
        this.phone = phone;
        this.cardnum = cardnum;
        this.certIds = certIds;
        this.qualificationpctureList = qualificationpctureList;
        this.code = code;
        this.procdefId = procdefId;
    }

    public List<Integer> getCertIds() {
        return certIds;
    }

    public void setCertIds(List<Integer> certIds) {
        this.certIds = certIds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getAccttype() {
        return accttype;
    }

    public void setAccttype(Integer accttype) {
        this.accttype = accttype;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public List<String> getQualificationpctureList() {
        return qualificationpctureList;
    }

    public void setQualificationpctureList(List<String> qualificationpctureList) {
        this.qualificationpctureList = qualificationpctureList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProcdefId() {
        return procdefId;
    }

    public void setProcdefId(String procdefId) {
        this.procdefId = procdefId;
    }

    @Override
    public String toString() {
        return "RealNameVO{" +
                "memberId=" + memberId +
                ", accttype=" + accttype +
                ", realname='" + realname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cardnum='" + cardnum + '\'' +
                ", certIds=" + certIds +
                ", qualificationpctureList=" + qualificationpctureList +
                ", code='" + code + '\'' +
                ", procdefId='" + procdefId + '\'' +
                '}';
    }
}
