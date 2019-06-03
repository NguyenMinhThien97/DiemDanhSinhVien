package com.thientri.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.thientri.api.config.ApplicationContextConfig;
import com.thientri.api.idao.SinhVienIDAO;
import com.thientri.api.idao.ThongBaoIDAO;
import com.thientri.api.model.MonHoc;
import com.thientri.api.model.ThongBao;

@Repository
public class ThongBaoDAO implements ThongBaoIDAO {
	ApplicationContextConfig app;

	public List<ThongBao> getAllThongBaoSV(long maSinhVien) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SinhVienDAO sinhVienDAO = new SinhVienDAO();
		List<MonHoc> listMonHoc = sinhVienDAO.getTenMonHoc(maSinhVien);
		List<ThongBao> list = new ArrayList<ThongBao>() ;
		PreparedStatement smt = null;
		try {
			for (MonHoc m : listMonHoc) {
				Connection con = app.getConnection();
				String sql = "SELECT * FROM thongbao t WHERE t.mamonhoc = ?";
				smt = con.prepareStatement(sql);
				smt.setLong(1, m.getMaMonHoc());
				ResultSet rs = smt.executeQuery();
				while (rs.next()) {
					long maThongBao = rs.getLong("mathongbao");
					long maGiaoVien = rs.getLong("magiaovien");
					long maMonHoc = rs.getLong("mamonhoc");
					String tenThongBao = rs.getString("tenthongbao");
					String noiDung = rs.getString("noidung");
					Date ngayTao = rs.getDate("ngaytao");
					String ngaytao = dateFormat.format(ngayTao);
					String tenGiaoVien = getTenGiaoVien(maGiaoVien);
					String tenMonHoc = getTenMonHoc(maMonHoc);
					ThongBao t = new ThongBao(maThongBao, tenGiaoVien, tenMonHoc, tenThongBao, noiDung, ngaytao);
					list.add(t);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<ThongBao> getAllThongBaoGV(long maGiaoVien) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		List<ThongBao> list = new ArrayList<ThongBao>() ;
		PreparedStatement smt = null;
		try {
			Connection con = app.getConnection();
			String sql = "SELECT * FROM thongbao t WHERE t.magiaovien = ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maGiaoVien);
			ResultSet rs = smt.executeQuery();
			while (rs.next()) {
				long maThongBao = rs.getLong("mathongbao");
				long maGV = rs.getLong("magiaovien");
				long maMonHoc = rs.getLong("mamonhoc");
				String tenThongBao = rs.getString("tenthongbao");
				String noiDung = rs.getString("noidung");
				Date ngayTao = rs.getDate("ngaytao");
				String ngaytao = dateFormat.format(ngayTao);
				String tenGiaoVien = getTenGiaoVien(maGiaoVien);
				String tenMonHoc = getTenMonHoc(maMonHoc);
				ThongBao t = new ThongBao(maThongBao, tenGiaoVien, tenMonHoc, tenThongBao, noiDung, ngaytao);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getTenGiaoVien(long maGiaoVien) {
		PreparedStatement smt = null;
		String tenGiaoVien = null;
		try {
			Connection con = app.getConnection();
			String sql = "SELECT n.ten FROM nguoidung n WHERE n.ma= ? AND n.status = 1";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maGiaoVien);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				tenGiaoVien = rs.getString("ten");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenGiaoVien;
	}
	public String getTenMonHoc(long maMonHoc) {
		PreparedStatement smt = null;
		String tenMonHoc = null;
		try {
			Connection con = app.getConnection();
			String sql = "SELECT m.tenmonhoc FROM monhoc m WHERE m.mamonhoc = ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maMonHoc);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				tenMonHoc = rs.getString("tenmonhoc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenMonHoc;
	}

	public boolean themThongBao(String maGiaoVien,String maMonHoc,String tenThongBao,String noiDung) {
		PreparedStatement smt = null;
		int n = 0;
		LocalDate now = LocalDate.now();
		try {
			Connection con = app.getConnection();
			String sql = "insert into thongbao (mathongbao , magiaovien , mamonhoc , tenthongbao , noidung , ngaytao) values (null, ? , ? , ? , ? , ? )";
			
			smt = con.prepareStatement(sql);
			smt.setString(1, maGiaoVien);
			smt.setString(2, maMonHoc);
			smt.setString(3, tenThongBao);
			smt.setString(4,noiDung);
			smt.setString(5, now.toString());
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

	public boolean xoaThongBao(long maThongBao) {
		PreparedStatement smt = null;
		int n = 0;
		try {
			Connection con = app.getConnection();
			String sql = "DELETE FROM thongbao WHERE mathongbao= ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maThongBao);
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

	public boolean suaThongBao(long maThongBao, String tenThongBao, String noiDung) {
		PreparedStatement smt = null;
		int n = 0;
		try {
			Connection con = app.getConnection();
			String sql = "UPDATE thongbao SET tenthongbao = ? , noidung = ? WHERE  mathongbao = ? ";
			smt = con.prepareStatement(sql);
			smt.setString(1, tenThongBao);
			smt.setString(2, noiDung);
			smt.setLong(3, maThongBao);
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

}
