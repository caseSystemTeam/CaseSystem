package com.lawer.pojo;

public class User {
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



	public int getFtag() {
		return ftag;
	}
	public void setFtag(int ftag) {
		this.ftag = ftag;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", name=" + name + ", password=" + password + ", profile="
				+ profile + ", gender=" + gender + ", position=" + position + ", solve=" + solve + ", ftag=" + ftag
				+ ", unsolve=" + unsolve + ", phonenumber=" + phonenumber + "]";
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
