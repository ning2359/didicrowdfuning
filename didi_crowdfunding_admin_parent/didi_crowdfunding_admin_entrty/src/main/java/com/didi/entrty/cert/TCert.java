package com.didi.entrty.cert;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
/**
 * @author jobob
 * @since 2020-07-24
 */
public class TCert implements Serializable {
private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    public TCert() {

    }

    public TCert(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TCert{" +
        "id=" + id +
        ", name=" + name +
        "}";
    }
}
