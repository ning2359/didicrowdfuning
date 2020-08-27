package com.didi.entrty.utils;

import java.util.List;

public class CertImg  {
    private String certName;
    private String qualificationpcture;

    public CertImg() {
    }

    public CertImg(String certName, String qualificationpcture) {
        this.certName = certName;
        this.qualificationpcture = qualificationpcture;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getQualificationpcture() {
        return qualificationpcture;
    }

    public void setQualificationpcture(String qualificationpcture) {
        this.qualificationpcture = qualificationpcture;
    }

    @Override
    public String toString() {
        return "CertImg{" +
                "certName='" + certName + '\'' +
                ", qualificationpcture='" + qualificationpcture + '\'' +
                '}';
    }
}
