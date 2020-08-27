package com.didi.crowd.entrty.PO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * @author jobob
 * @since 2020-06-29
 */
public class TMember implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String loginAcct;

    private String userPasswd;

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

    public TMember() {
    }

    public TMember(Integer id, String loginAcct, String userPasswd, String userName, String email, String phone, Integer authstatus, Integer usertype, String realname, String cardnum, Integer accttype) {
        this.id = id;
        this.loginAcct = loginAcct;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.authstatus = authstatus;
        this.usertype = usertype;
        this.realname = realname;
        this.cardnum = cardnum;
        this.accttype = accttype;
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

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "TMember{" +
                "id=" + id +
                ", loginAcct='" + loginAcct + '\'' +
                ", userPasswd='" + userPasswd + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", authstatus=" + authstatus +
                ", usertype=" + usertype +
                ", realname='" + realname + '\'' +
                ", cardnum='" + cardnum + '\'' +
                ", accttype=" + accttype +
                '}';
    }
}
