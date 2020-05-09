package com.lawer.pojo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -3662066498325187792L;
	private String id;
	private String username;
	private String name;
	private String password;
	private String profile;
	private String gender;
	private String position;
	private int solve;
	private int ftag;
	private String busId;
	private String salt;
	private String createTime;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 *
	 * 重写获取盐值方法，自定义realm使用
	 * Gets credentials salt.
	 *
	 * @return the credentials salt
	 */
	public String getCredentialsSalt() {
		return username + "nbclass.com" + salt;
	}


	/**
	 * 获取加密盐值
	 *
	 * @return salt - 加密盐值
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * 设置加密盐值
	 *
	 * @param salt 加密盐值
	 */
	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}
	public int getFtag() {
		return ftag;
	}
	public void setFtag(int ftag) {
		this.ftag = ftag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getSolve() {
		return solve;
	}
	public void setSolve(int solve) {
		this.solve = solve;
	}
	public int getUnsolve() {
		return unsolve;
	}
	public void setUnsolve(int unsolve) {
		this.unsolve = unsolve;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	private int unsolve;
	private String phonenumber;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}
}
