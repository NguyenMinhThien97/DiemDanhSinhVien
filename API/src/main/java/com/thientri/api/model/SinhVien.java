package com.thientri.api.model;

public class SinhVien {
	private long maSinhVien;
	
	private String tenSinhVien;
	
	private String tenLop;
	
	private String gioiTinh;

	public SinhVien(long maSinhVien, String tenSinhVien, String tenLop, String gioiTinh) {
		super();
		this.maSinhVien = maSinhVien;
		this.tenSinhVien = tenSinhVien;
		this.tenLop = tenLop;
		this.gioiTinh = gioiTinh;
	}

	public long getMaSinhVien() {
		return maSinhVien;
	}

	public void setMaSinhVien(long maSinhVien) {
		this.maSinhVien = maSinhVien;
	}

	public String getTenSinhVien() {
		return tenSinhVien;
	}

	public void setTenSinhVien(String tenSinhVien) {
		this.tenSinhVien = tenSinhVien;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	
}
