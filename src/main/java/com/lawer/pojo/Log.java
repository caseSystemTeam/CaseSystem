package com.lawer.pojo;

import com.lawer.common.DateUtil;

import java.util.UUID;

/**
 * 日志表
 */
public class Log {
    private String id;
    /*
    *用户名
     */
    private  String username;
    /**
     * ip地址
     */
    private String ip_address;
    /**
     * 日志类型（0：登录 1：操作）
     */
    private int style;

    /**
     * 操作名称
     */
    private String operatename;
    /**
     * 操作结果
     */
    private String operateresult;
    /**
     * 操作描述
     */
    private String descript;
    /**
     * 创建时间
     */
    private String create_time;

    /**
     *公司id
     */
    private String busid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpaddress() {
        return ip_address;
    }

    public void setIpaddress(String ipaddress) {
        this.ip_address = ipaddress;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getOperatename() {
        return operatename;
    }

    public void setOperatename(String operatename) {
        this.operatename = operatename;
    }

    public String getOperateresult() {
        return operateresult;
    }

    public void setOperateresult(String operateresult) {
        this.operateresult = operateresult;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
    public Log(){
        super();
    }
    public Log(String id, String username, String ipaddress, int style, String operatename, String operateresult, String descript, String create_time,String busid) {
        this.id = id;
        this.username = username;
        this.ip_address = ipaddress;
        this.style = style;
        this.operatename = operatename;
        this.operateresult = operateresult;
        this.descript = descript;
        this.create_time = create_time;
        this.busid = busid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public Log createLog(String username,String ip_address,int style,String operatename,String result,String name,String busid){
        this.id = UUID.randomUUID().toString();
        this.create_time = DateUtil.getToday();
        this.username =username;
        this.ip_address = ip_address;
        this.style =style;
        this.operatename = operatename;
        this.operateresult = operatename+result;
        this.busid =busid;
        this.descript = name;
        return this;

    }
    public static Log ok(String username,String ip_address,int style,String operatename,String result,String name,String busid){
        return  new Log().createLog(username,ip_address,style,operatename,result,name,busid);
    }

}
