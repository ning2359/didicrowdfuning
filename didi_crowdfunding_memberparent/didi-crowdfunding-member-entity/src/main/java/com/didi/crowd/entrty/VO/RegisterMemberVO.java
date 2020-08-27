package com.didi.crowd.entrty.VO;

public class RegisterMemberVO {
    private String loginAcct;
    private String userPasswd;
    private String userName;
    private String email;
    private String phoneNum;
    private String code;

    public RegisterMemberVO() {
    }

    public RegisterMemberVO(String loginAcct, String userPasswd, String userName, String email, String phoneNum, String code) {
        this.loginAcct = loginAcct;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.code = code;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RegisterMemberVO{" +
                "loginAcct='" + loginAcct + '\'' +
                ", userPasswd='" + userPasswd + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
