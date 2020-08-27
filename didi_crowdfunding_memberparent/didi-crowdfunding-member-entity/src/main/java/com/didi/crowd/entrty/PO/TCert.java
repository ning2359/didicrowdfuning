package com.didi.crowd.entrty.PO;

import java.io.Serializable;

public class TCert implements Serializable {

    private static final long serialVersionUID = -769439160330052904L;
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
                ", name='" + name + '\'' +
                '}';
    }
}
