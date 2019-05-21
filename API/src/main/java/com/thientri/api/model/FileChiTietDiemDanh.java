package com.thientri.api.model;

public class FileChiTietDiemDanh {
	
	private long maSinhVien;
	
	private String ngayDiemDanh;
	
	private String lyDoNghi;
	
	private int status;

	public FileChiTietDiemDanh(long maSinhVien, String ngayDiemDanh, String lyDoNghi, int status) {
		super();
		this.maSinhVien = maSinhVien;
		this.ngayDiemDanh = ngayDiemDanh;
		this.lyDoNghi = lyDoNghi;
		this.status = status;
	}

	public long getMaSinhVien() {
		return maSinhVien;
	}

	public void setMaSinhVien(long maSinhVien) {
		this.maSinhVien = maSinhVien;
	}

	public String getNgayDiemDanh() {
		return ngayDiemDanh;
	}

	public void setNgayDiemDanh(String ngayDiemDanh) {
		this.ngayDiemDanh = ngayDiemDanh;
	}

	public String getLyDoNghi() {
		return lyDoNghi;
	}

	public void setLyDoNghi(String lyDoNghi) {
		this.lyDoNghi = lyDoNghi;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
