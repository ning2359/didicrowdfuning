package com.didi.entrty.auth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * @author jobob
 * @since 2020-06-14
 */
public class TAuth implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String title;

    private Integer categoryId;

    public TAuth() {
    }

    public TAuth(Integer id, String name, String title, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.categoryId = categoryId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "TAuth{" +
        "id=" + id +
        ", name=" + name +
        ", title=" + title +
        ", categoryId=" + categoryId +
        "}";
    }
}
