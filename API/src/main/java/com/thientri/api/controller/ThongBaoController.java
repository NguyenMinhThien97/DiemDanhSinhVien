package com.thientri.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thientri.api.idao.ThongBaoIDAO;
import com.thientri.api.model.ThongBao;

@RestController
@RequestMapping("thongbao")
@CrossOrigin(origins = "*")
public class ThongBaoController {
	
	@Autowired
	private ThongBaoIDAO thongBaoIDAO;
	
	@GetMapping(value = "/getThongBaoSinhVien/{maSinhVien}",  produces = "application/json;charset=UTF-8" )
	public List<ThongBao> getThongBaoSinhVien(@PathVariable("maSinhVien") long maSinhVien){
		List<ThongBao> list = thongBaoIDAO.getAllThongBaoSV(maSinhVien);
		return list;
	}
	
	@GetMapping(value = "/getThongBaoGiaoVien/{maGiaoVien}",  produces = "application/json;charset=UTF-8" )
	public List<ThongBao> getThongBaoGiaoVien(@PathVariable("maGiaoVien") long maGiaoVien){
		List<ThongBao> list = thongBaoIDAO.getAllThongBaoGV(maGiaoVien);
		return list;
	}
	
	@PostMapping(value="/create",  produces = "application/json;charset=UTF-8")
	public boolean create( String maGiaoVien,String maMonHoc,String tenThongBao,String noiDung) {
		return thongBaoIDAO.themThongBao(maGiaoVien, maMonHoc, tenThongBao, noiDung);
	}
	
	@PostMapping(value="/update",produces = "application/json;charset=UTF-8")
	public boolean update(String maThongBao,String tenThongBao, String noiDung) {
		return thongBaoIDAO.suaThongBao(Long.valueOf(maThongBao), tenThongBao, noiDung);
	}
	
	@GetMapping(value="/deleteByID/{maThongBao}",produces = "application/json;charset=UTF-8")
	public boolean delete(@PathVariable("maThongBao") long maThongBao){
		return thongBaoIDAO.xoaThongBao(maThongBao);
	}
}
