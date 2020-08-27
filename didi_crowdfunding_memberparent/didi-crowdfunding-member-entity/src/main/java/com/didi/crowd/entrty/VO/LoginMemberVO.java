package com.didi.crowd.entrty.VO;

import java.io.Serializable;

public class LoginMemberVO  implements Serializable {

    private static final long serialVersionUID = -3316112194728289463L;

    private Integer id;

    private String userName;

    private String email;
    //认证状态
    private Integer authstatus;
    //认证状态说明
    private String authstatusText;
    //用户状态
    private Integer accttype;

    private String loginAcct;

    public LoginMemberVO() {
    }

    public LoginMemberVO(Integer id, String userName, String email, Integer authstatus, String authstatusText, Integer accttype, String loginAcct) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.authstatus = authstatus;
        this.authstatusText = authstatusText;
        this.accttype = accttype;
        this.loginAcct = loginAcct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAuthstatusText() {
        return authstatusText;
    }

    public void setAuthstatusText(String authstatusText) {
        this.authstatusText = authstatusText;
    }

    public Integer getAccttype() {
        return accttype;
    }

    public void setAccttype(Integer accttype) {
        this.accttype = accttype;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    @Override
    public String toString() {
        return "LoginMemberVO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", authstatus=" + authstatus +
                ", authstatusText='" + authstatusText + '\'' +
                ", accttype=" + accttype +
                ", loginAcct='" + loginAcct + '\'' +
                '}';
    }
}
