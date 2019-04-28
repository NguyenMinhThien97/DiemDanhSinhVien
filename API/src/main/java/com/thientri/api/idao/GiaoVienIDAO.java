package com.thientri.api.idao;

import java.util.List;

import com.thientri.api.model.ChiTietDiemDanh;
import com.thientri.api.model.Lich;
import com.thientri.api.model.MonHoc;
import com.thientri.api.model.MonHocHienTai;

public interface GiaoVienIDAO {
	public List<Lich> xemLichDay(long maNguoiDung);
	public List<ChiTietDiemDanh> xemChiTietDiemDanh(long maMonHoc);
	public boolean quetQRDiemDanh(long maSinhVien,long maGiaoVien, String matKhauGiaoVien);
	public MonHocHienTai monHocHienTai(long maGiaoVien, String matKhau);
}
