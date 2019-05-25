package com.thientri.api.idao;

import java.util.List;

import com.thientri.api.model.ChiTietDiemDanh;
import com.thientri.api.model.Lich;
import com.thientri.api.model.MonHoc;
import com.thientri.api.model.MonHocHienTai;
import com.thientri.api.model.SinhVien;

public interface GiaoVienIDAO {
	public List<Lich> xemLichDay(long maNguoiDung);
	public List<ChiTietDiemDanh> xemChiTietDiemDanh(long maMonHoc, String ngayDiemDanh);
	public String CheckDiemDanh(long maMonHoc, String ngayDiemDanh, long maSinhVien);
	public boolean quetQRDiemDanhLan1(long maSinhVien, long maGiaoVien, String matKhauGiaoVien) ;
	public boolean quetQRDiemDanhLan2(long maSinhVien, long maGiaoVien, String matKhauGiaoVien);
	public MonHocHienTai monHocHienTai(long maGiaoVien, String matKhau);
	public List<MonHoc> getTenMonHoc(long maGiaoVien);
	public List<String> getNgayHoc(long maGiaoVien, long maMonHoc);
	public String tenMonHoc(long maMonHoc);
	public boolean taoListChiTietDiemDanh(long maGiaoVien, long maMonHoc);
	public List<SinhVien> getThongTinSinhVien(long maMonHoc);
	public String getEmailGV(long maGiaoVien);
	public int getStatusChiTietDiemDanh(long maSinhVien,long maGiaoVien);
}
