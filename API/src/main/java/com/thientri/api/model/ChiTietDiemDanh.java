package com.thientri.api.model;

import java.io.Serializable;


public class ChiTietDiemDanh implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private long maSinhVien;

	private String tenSinhVien;
	
	private String tenLop;

	private String hinh;
	
	private String gioitinh;
	
	private String ngayDiemDanh;
	
	private String lyDoNghi;
	
	private int status;

	public ChiTietDiemDanh(long maSinhVien, String tenSinhVien, String tenLop, String hinh, String gioitinh,
			String ngayDiemDanh, String lyDoNghi, int status) {
		super();
		this.maSinhVien = maSinhVien;
		this.tenSinhVien = tenSinhVien;
		this.tenLop = tenLop;
		this.hinh = hinh;
		this.gioitinh = gioitinh;
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

	public String getHinh() {
		return hinh;
	}

	public void setHinh(String hinh) {
		this.hinh = hinh;
	}

	public String getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
