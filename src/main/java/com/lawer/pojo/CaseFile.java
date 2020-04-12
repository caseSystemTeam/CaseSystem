package com.lawer.pojo;

public class CaseFile {
    private String id;
    private String fileid;
    private String filename;
    private String url;
    private String createTime;
    private String caseId;
    private String upId;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
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
                "id='" + id + '\'' +
                ", fileid='" + fileid + '\'' +
                ", filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", createTime='" + createTime + '\'' +
                ", caseId='" + caseId + '\'' +
                ", upId='" + upId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
