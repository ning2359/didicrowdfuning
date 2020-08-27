package com.didi.crowd.entrty.PO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 *
 * @author jobob
 * @since 2020-07-03
 */
public class TMemberConfirmInfo implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员 id
     */
    private Integer memberid;

    /**
     * 易付宝企业账号
     */
    private String paynum;

    /**
     * 法人身份证号
     */
    private String cardnum;

    public TMemberConfirmInfo() {
    }

    public TMemberConfirmInfo(Integer memberid, String paynum, String cardnum) {
        this.memberid = memberid;
        this.paynum = paynum;
        this.cardnum = cardnum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
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
        return "TMemberConfirmInfo{" +
        "id=" + id +
        ", memberid=" + memberid +
        ", paynum=" + paynum +
        ", cardnum=" + cardnum +
        "}";
    }
}
