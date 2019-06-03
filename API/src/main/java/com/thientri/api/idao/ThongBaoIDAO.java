package com.thientri.api.idao;

import java.util.List;

import com.thientri.api.model.ThongBao;

public interface ThongBaoIDAO {
	public List<ThongBao> getAllThongBaoSV(long maSinhVien);
	public List<ThongBao> getAllThongBaoGV(long maGiaoVien);
	public boolean themThongBao(String maGiaoVien,String maMonHoc,String tenThongBao,String noiDung);
	public boolean xoaThongBao(long maThongBao);
	public boolean suaThongBao(long maThongBao, String tenThongBao, String noiDung);
	
}
