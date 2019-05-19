package com.thientri.api.model;

public class FileChiTietDiemDanh {
	
	private long maSinhVien;
	
	private String ngayDiemDanh;
	
	private String lyDoNghi;
	
	private boolean status;


	public FileChiTietDiemDanh(long maSinhVien, String ngayDiemDanh, String lyDoNghi, boolean status) {
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
