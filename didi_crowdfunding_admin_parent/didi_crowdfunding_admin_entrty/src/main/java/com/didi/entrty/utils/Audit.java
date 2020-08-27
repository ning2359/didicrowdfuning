package com.didi.entrty.utils;


import java.io.Serializable;
import java.util.List;

public class Audit  implements Serializable {

    private static final long serialVersionUID = -7274429624920963276L;
    private Integer id;

    private String loginAcct;

    private String userName;

    private String email;

    private String phone;
    /**
     * 实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证
     */
    private Integer authstatus;

    /**
     *  0 - 个人， 1 - 企业
     */
    private Integer usertype;

    private String realname;

    private String cardnum;

    /**
     * 0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府
     */
    private Integer accttype;

    private List<CertImg> certImgList;

    public Audit() {
    }

    public Audit(Integer id, String loginAcct, String userName, String email, String phone, Integer authstatus, Integer usertype, String realname, String cardnum, Integer accttype, List<CertImg> certImgList) {
        this.id = id;
        this.loginAcct = loginAcct;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.authstatus = authstatus;
        this.usertype = usertype;
        this.realname = realname;
        this.cardnum = cardnum;
        this.accttype = accttype;
        this.certImgList = certImgList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAuthstatus() {
        return authstatus;
    }

    public void setAuthstatus(Integer authstatus) {
        this.authstatus = authstatus;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public Integer getAccttype() {
        return accttype;
    }

    public void setAccttype(Integer accttype) {
        this.accttype = accttype;
    }

    public List<CertImg> getCertImgList() {
        return certImgList;
    }

    public void setCertImgList(List<CertImg> certImgList) {
        this.certImgList = certImgList;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", loginAcct='" + loginAcct + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", authstatus=" + authstatus +
                ", usertype=" + usertype +
                ", realname='" + realname + '\'' +
                ", cardnum='" + cardnum + '\'' +
                ", accttype=" + accttype +
                ", certImgList=" + certImgList +
                '}';
    }
}
