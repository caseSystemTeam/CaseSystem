package com.lawer.pojo;

public class Indictment {
    private String Id;
    private String caseId;
    private String writerId;
    private String helperId;
    private String idear;
    private int state;
    private int version;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getHelperId() {
        return helperId;
    }

    public void setHelperId(String helperId) {
        this.helperId = helperId;
    }

    public String getIdear() {
        return idear;
    }

    public void setIdear(String idear) {
        this.idear = idear;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Indictment{" +
                "Id='" + Id + '\'' +
                ", caseId='" + caseId + '\'' +
                ", writerId='" + writerId + '\'' +
                ", helperId='" + helperId + '\'' +
                ", idear='" + idear + '\'' +
                ", state=" + state +
                ", version=" + version +
                '}';
    }
}
