package com.didi.crowd.entrty.PO;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * @author jobob
 * @since 2020-07-03
 */
@TableName("t_project_item_pic")
public class TProjectItemPic implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectid;

    private String itemPicPath;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getItemPicPath() {
        return itemPicPath;
    }

    public void setItemPicPath(String itemPicPath) {
        this.itemPicPath = itemPicPath;
    }

    @Override
    public String toString() {
        return "TProjectItemPic{" +
        "id=" + id +
        ", projectid=" + projectid +
        ", itemPicPath=" + itemPicPath +
        "}";
    }
}
