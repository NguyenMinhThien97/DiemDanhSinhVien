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

import com.thientri.api.idao.NguoiDungIDAO;
import com.thientri.api.model.NguoiDung;

@RestController
@RequestMapping("nguoidung")
@CrossOrigin(origins = "*")
public class NguoiDungController {
	
	@Autowired
	private NguoiDungIDAO nguoiDungIDAO;
	
	@GetMapping(value = "/getAll",  produces = "application/json;charset=UTF-8" )
	public List<NguoiDung> getAllNguoiDung(){
		List<NguoiDung> list = nguoiDungIDAO.getAllNguoiDung();
		return list;
	}
	
	@PostMapping(value="/create",produces = "application/json;charset=UTF-8")
	public boolean create(@RequestBody NguoiDung nguoiDung) {
		return nguoiDungIDAO.createNguoiDung(nguoiDung);
	}
	
	@PostMapping(value="/update/{ma}/{matKhau}/{soDienThoai}/{email}",produces = "application/json;charset=UTF-8")
	public boolean update(@PathVariable("ma") long ma,@PathVariable("matKhau") String matKhau,@PathVariable("soDienThoai") String soDienThoai,@PathVariable("email") String email) {
		return nguoiDungIDAO.updateNguoiDung(ma, matKhau, soDienThoai, email);
	}
	
	@GetMapping(value="/deleteByID/{ma}",produces = "application/json;charset=UTF-8")
	public boolean delete(@PathVariable("ma") long ma){
		return nguoiDungIDAO.deleteNguoiDungById(ma);
	}
	
	@GetMapping(value="/findById/{maNguoiDung}",produces = "application/json;charset=UTF-8")
	public NguoiDung findByID(@PathVariable("maNguoiDung") long maNguoiDung) {
		return nguoiDungIDAO.findById(maNguoiDung);
	}
	
	@GetMapping(value="/dangNhap/{ma}/{matkhau}",produces = "application/json;charset=UTF-8")
	public NguoiDung dangNhap(@PathVariable("ma") long ma,@PathVariable("matkhau") String matkhau) {
		return nguoiDungIDAO.dangNhap(ma, matkhau);
	}
	
	
	@GetMapping(value="/doiMatKhau/{ma}/{matkhaucu}/{matkhaumoi}",produces = "application/json;charset=UTF-8")
	public boolean doiMatKhau(@PathVariable("ma") long ma,@PathVariable("matkhaucu") String matkhaucu,@PathVariable("matkhaumoi") String matkhaumoi) {
		return nguoiDungIDAO.doiMatKhau(ma,matkhaucu, matkhaumoi);
	}
	
	@GetMapping(value="/quenMatKhau/{ma}/{email}",produces = "application/json;charset=UTF-8")
	public boolean quenMatKhau(@PathVariable("ma") long ma,@PathVariable("email") String email) {
		String gmail = email+"@gmail.com";
		return nguoiDungIDAO.quenMatKhau(ma, gmail);
	}
	
//	@GetMapping(value="/quenMatKhau/{ma}/{email}",produces = "application/json;charset=UTF-8")
//	public boolean quenMatKhau(@PathVariable("ma") long ma ,@PathVariable("email") String email) {
//		return nguoiDungIDAO.quenMatKhau(ma, email);
//	}
//	
}
