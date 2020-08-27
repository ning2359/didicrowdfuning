package com.didi.crowd.entrty.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-07-08
 */
@TableName("t_order_project")
public class TOrderProject implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 发起人
     */
    private String launchName;

    /**
     * 回报内容
     */
    private String returnContent;

    /**
     * 回报数量
     */
    private Integer returnCount;

    /**
     * 支持单价
     */
    private Integer supportPrice;

    /**
     * 配送费用
     */
    private Integer freight;

    /**
     * 优惠
     */
    private Integer discount;

    /**
     * 订单表的主键
     */
    private Integer orderId;

    /**
     * 项目表的主键
     */
    private Integer projectId;

    public TOrderProject() {
    }

    public TOrderProject(Integer id, String projectName, String launchName, String returnContent, Integer returnCount, Integer supportPrice, Integer freight, Integer discount, Integer orderId, Integer projectId) {
        this.id = id;
        this.projectName = projectName;
        this.launchName = launchName;
        this.returnContent = returnContent;
        this.returnCount = returnCount;
        this.supportPrice = supportPrice;
        this.freight = freight;
        this.discount = discount;
        this.orderId = orderId;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "TOrderProject{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", launchName='" + launchName + '\'' +
                ", returnContent='" + returnContent + '\'' +
                ", returnCount=" + returnCount +
                ", supportPrice=" + supportPrice +
                ", freight=" + freight +
                ", discount=" + discount +
                ", orderId=" + orderId +
                ", projectId=" + projectId +
                '}';
    }
}
