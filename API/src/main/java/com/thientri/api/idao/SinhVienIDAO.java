package com.thientri.api.idao;

import java.util.List;

import com.thientri.api.model.Lich;
import com.thientri.api.model.MonHoc;

public interface SinhVienIDAO {
	public List<Lich> xemLichHoc(long maNguoiDung);
	public boolean quetQRDiemDanh(long maPhongHoc);
	public MonHoc monHocHienTai(long maGiaoVien);
}
