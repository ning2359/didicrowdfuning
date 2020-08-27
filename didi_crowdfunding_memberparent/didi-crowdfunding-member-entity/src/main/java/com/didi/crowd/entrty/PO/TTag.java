package com.didi.crowd.entrty.PO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 *
 * @author jobob
 * @since 2020-07-03
 */
public class TTag implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private String name;

    public TTag() {
    }

    public TTag(Integer pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TTag{" +
        "id=" + id +
        ", pid=" + pid +
        ", name=" + name +
        "}";
    }
}
