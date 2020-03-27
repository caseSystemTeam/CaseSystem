package com.lawer.pojo;

public class Password {
	private String ps1;
	private String ps2;
	private String ps3;
	public Password() {
		super();
	}
	public Password(String ps1, String ps2, String ps3) {
		super();
		this.ps1 = ps1;
		this.ps2 = ps2;
		this.ps3 = ps3;
	}
	@Override
	public String toString() {
		return "password [ps1=" + ps1 + ", ps2=" + ps2 + ", ps3=" + ps3 + "]";
	}
	public String getPs1() {
		return ps1;
	}
	public void setPs1(String ps1) {
		this.ps1 = ps1;
	}
	public String getPs2() {
		return ps2;
	}
	public void setPs2(String ps2) {
		this.ps2 = ps2;
	}
	public String getPs3() {
		return ps3;
	}
	public void setPs3(String ps3) {
		this.ps3 = ps3;
	}
	

}
