package com.lawer.pojo;

public class CaseFile {
    private int id;
    private int fielid;
    private String filename;
    private String url;
    private String createTime;
    private int caseId;
    private int upId;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFielid() {
        return fielid;
    }

    public void setFielid(int fielid) {
        this.fielid = fielid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getUpId() {
        return upId;
    }

    public void setUpId(int upId) {
        this.upId = upId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CaseFile{" +
                "id=" + id +
                ", fielid=" + fielid +
                ", filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", createTime='" + createTime + '\'' +
                ", caseId=" + caseId +
                ", upId=" + upId +
                ", type='" + type + '\'' +
                '}';
    }
}
