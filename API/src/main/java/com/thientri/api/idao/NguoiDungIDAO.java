package com.thientri.api.idao;

import java.util.List;

import com.thientri.api.model.NguoiDung;

public interface NguoiDungIDAO{
	public List<NguoiDung> getAllNguoiDung();
	public boolean createNguoiDung(NguoiDung nguoiDung);
	public boolean updateNguoiDung(long ma, String matKhau, String soDienThoai, String email);
	public boolean deleteNguoiDungById(long ma);
	public NguoiDung findById(long ma);
	public NguoiDung dangNhap(long ma ,String matKhau);
	public boolean doiMatKhau(long ma, String matKhauCu, String matKhauMoi);
	public boolean quenMatKhau(long ma,String email);
}
