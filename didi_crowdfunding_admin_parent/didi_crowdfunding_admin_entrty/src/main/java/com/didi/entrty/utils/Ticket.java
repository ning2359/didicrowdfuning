package com.didi.entrty.utils;

public class Ticket {
    private Integer id;
    private String procdefId;
    private Integer memberId;

    public Ticket(Integer id, String procdefId, Integer memberId) {
        this.id = id;
        this.procdefId = procdefId;
        this.memberId = memberId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcdefId() {
        return procdefId;
    }

    public void setProcdefId(String procdefId) {
        this.procdefId = procdefId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", procdefId='" + procdefId + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
