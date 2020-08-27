package com.didi.crowd.entrty.PO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 *
 * @author jobob
 * @since 2020-07-03
 */
public class TMemberLaunchInfo implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员 id
     */
    private Integer memberid;

    /**
     * 简单介绍
     */
    private String descriptionSimple;

    /**
     * 详细介绍
     */
    private String descriptionDetail;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 客服电话
     */
    private String serviceNum;

    public TMemberLaunchInfo() {
    }

    public TMemberLaunchInfo( Integer memberid, String descriptionSimple, String descriptionDetail, String phoneNum, String serviceNum) {
        this.memberid = memberid;
        this.descriptionSimple = descriptionSimple;
        this.descriptionDetail = descriptionDetail;
        this.phoneNum = phoneNum;
        this.serviceNum = serviceNum;
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

    public String getDescriptionSimple() {
        return descriptionSimple;
    }

    public void setDescriptionSimple(String descriptionSimple) {
        this.descriptionSimple = descriptionSimple;
    }

    public String getDescriptionDetail() {
        return descriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        this.descriptionDetail = descriptionDetail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum;
    }

    @Override
    public String toString() {
        return "TMemberLaunchInfo{" +
        "id=" + id +
        ", memberid=" + memberid +
        ", descriptionSimple=" + descriptionSimple +
        ", descriptionDetail=" + descriptionDetail +
        ", phoneNum=" + phoneNum +
        ", serviceNum=" + serviceNum +
        "}";
    }
}
