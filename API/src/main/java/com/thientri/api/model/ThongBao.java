package com.thientri.api.model;

public class ThongBao {
	private long maThongBao;
	private long maGiaoVien;
	private String tenThongBao;
	private String noiDung;
	private String ngayTao;
	
	public ThongBao(long maThongBao, long maGiaoVien, String tenThongBao, String noiDung, String ngayTao) {
		super();
		this.maThongBao = maThongBao;
		this.maGiaoVien = maGiaoVien;
		this.tenThongBao = tenThongBao;
		this.noiDung = noiDung;
		this.ngayTao = ngayTao;
	}
	
	public long getMaThongBao() {
		return maThongBao;
	}
	public void setMaThongBao(long maThongBao) {
		this.maThongBao = maThongBao;
	}
	public long getMaGiaoVien() {
		return maGiaoVien;
	}
	public void setMaGiaoVien(long maGiaoVien) {
		this.maGiaoVien = maGiaoVien;
	}
	public String getTenThongBao() {
		return tenThongBao;
	}
	public void setTenThongBao(String tenThongBao) {
		this.tenThongBao = tenThongBao;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	public String getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}
	
	
}
