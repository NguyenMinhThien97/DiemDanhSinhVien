package com.thientri.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thientri.api.idao.SinhVienIDAO;
import com.thientri.api.model.DiemDanhSinhVien;
import com.thientri.api.model.Lich;
import com.thientri.api.model.MonHoc;
import com.thientri.api.model.MonHocHienTai;

@RestController
@RequestMapping("sinhvien")
@CrossOrigin(origins = "*")
public class SinhVienController {

	@Autowired
	private SinhVienIDAO sinhVienIDAO;
	
	@GetMapping(value = "/xemLichDay/{masinhVien}",  produces = "application/json;charset=UTF-8")
	public List<Lich> xemLichDay(@PathVariable("masinhVien") long masinhVien){
		return sinhVienIDAO.xemLichHoc(masinhVien);
	}
	
	@GetMapping(value = "/quetQRDiemDanh/{tenPhongHoc}/{maSinhVien}/{matKhau}",  produces = "application/json;charset=UTF-8")
	public boolean quetQRDiemDanh(@PathVariable("tenPhongHoc") String tenPhongHoc,@PathVariable("maSinhVien") long maSinhVien,@PathVariable("matKhau") String matKhau) {
		int status = sinhVienIDAO.getStatusChiTietDiemDanh(maSinhVien);
		//Check in
		if(status == 0) {
			return sinhVienIDAO.quetQRDiemDanhLan1(tenPhongHoc, maSinhVien, matKhau);
		}
		//Check out
		else if (status == 2) {
			return sinhVienIDAO.quetQRDiemDanhLan2(tenPhongHoc, maSinhVien, matKhau);
		}
		return false;
	}
	
	@GetMapping(value = "/monHocHienTai/{maSinhVien}/{matKhau}",  produces = "application/json;charset=UTF-8")
	public MonHocHienTai monHocHienTai(@PathVariable("maSinhVien") long maSinhVien,@PathVariable("matKhau") String matKhau) {
		return sinhVienIDAO.monHocHienTai(maSinhVien, matKhau);
	}
	
	@GetMapping(value = "/getTenMonHoc/{maSinhVien}", produces = "application/json;charset=UTF-8")
	public List<MonHoc> getTenMonHoc(@PathVariable("maSinhVien") long maSinhVien) {
		return sinhVienIDAO.getTenMonHoc(maSinhVien);
	}
	
	@GetMapping(value = "/xemChiTietDiemDanh/{maSinhVien}/{maMonHoc}", produces = "application/json;charset=UTF-8")
	public List<DiemDanhSinhVien> xemChiTietDiemDanh(@PathVariable("maSinhVien") long maSinhVien,@PathVariable("maMonHoc") long maMonHoc) {
		return sinhVienIDAO.xemChiTietDiemDanh(maSinhVien,maMonHoc);
	}
}
