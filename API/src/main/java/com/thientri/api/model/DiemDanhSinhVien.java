package com.thientri.api.model;

public class DiemDanhSinhVien {
	private String ngayDiemDanh;
	
	private int status;

	public DiemDanhSinhVien(String ngayDiemDanh, int status) {
		super();
		this.ngayDiemDanh = ngayDiemDanh;
		this.status = status;
	}

	public String getNgayDiemDanh() {
		return ngayDiemDanh;
	}

	public void setNgayDiemDanh(String ngayDiemDanh) {
		this.ngayDiemDanh = ngayDiemDanh;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
