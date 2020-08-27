package com.didi.crowd.entrty.VO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QualificationCert implements Serializable {
    private static final long serialVersionUID = 2446625071140329165L;
    private List<Integer> certIds;
    private List<String> qualificationpctureList;
    private List<Map<String,Object>> certImgsMap;
    public QualificationCert() {
    }

    public QualificationCert(List<Integer> certIds, List<String> qualificationpctureList) {
        this.certIds = certIds;
        this.qualificationpctureList = qualificationpctureList;
    }

    public List<Integer> getCertIds() {
        return certIds;
    }

    public void setCertIds(List<Integer> certIds) {
        this.certIds = certIds;
    }

    public List<String> getQualificationpctureList() {
        return qualificationpctureList;
    }

    public void setQualificationpctureList(List<String> qualificationpctureList) {
        this.qualificationpctureList = qualificationpctureList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Map<String, Object>> getCertImgsMap() {
        return certImgsMap;
    }

    public void setCertImgsMap(List<Map<String, Object>> certImgsMap) {
        this.certImgsMap = certImgsMap;
    }

    @Override
    public String toString() {
        return "QualificationCert{" +
                "certIds=" + certIds +
                ", qualificationpctureList=" + qualificationpctureList +
                ", certImgsMap=" + certImgsMap +
                '}';
    }
}
