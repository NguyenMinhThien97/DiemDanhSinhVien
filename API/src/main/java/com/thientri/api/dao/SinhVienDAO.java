package com.thientri.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.thientri.api.config.ApplicationContextConfig;
import com.thientri.api.idao.SinhVienIDAO;
import com.thientri.api.model.DiemDanhSinhVien;
import com.thientri.api.model.Lich;
import com.thientri.api.model.MonHoc;
import com.thientri.api.model.MonHocHienTai;

@Repository
public class SinhVienDAO implements SinhVienIDAO{
	ApplicationContextConfig app;
	
	public List<Lich> xemLichHoc(long maNguoiDung) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<Lich> list = new ArrayList<Lich>() ;
		PreparedStatement smt = null;
		try {
			Connection con = app.getConnection();
			String sql = "SELECT n.ma, m.tenmonhoc,n.ten, p.tenphonghoc, c.buoihoc, c.giobatdau, c.gioketthuc, m.ngaybatdau, m.ngayketthuc, m.namhoc, m.hocky, c.thu FROM cahoc c, monhoc m, phonghoc p, nguoidung n, diemdanh d WHERE m.mamonhoc = d.mamonhoc AND d.masinhvien = n.ma AND c.maphonghoc = p.maphonghoc AND c.mamonhoc = m.mamonhoc AND n.ma = ? AND n.status= 0 ORDER BY c.giobatdau";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maNguoiDung);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				long ma = rs.getLong("ma");
				String tenMonHoc = rs.getString("tenmonhoc");
				String tenNguoiDung = rs.getString("ten");
				String tenPhongHoc = rs.getString("tenphonghoc");
				String buoiHoc = rs.getString("buoihoc");
				String gioBatDau = rs.getString("giobatdau");
				String gioKetThuc = rs.getString("gioketthuc");
				Date ngayBD = rs.getDate("ngaybatdau");
                String ngayBatDau = dateFormat.format(ngayBD);
				Date ngayKT = rs.getDate("ngayketthuc");
				String ngayKetThuc = dateFormat.format(ngayKT);
				int namHoc = rs.getInt("namhoc");
				String hocKy = rs.getString("hocky");
				int thu = rs.getInt("thu");
				  
				Lich l = new Lich(ma, tenMonHoc, tenNguoiDung, hocKy, namHoc, gioBatDau, gioKetThuc, buoiHoc, tenPhongHoc, thu, ngayBatDau, ngayKetThuc);
				list.add(l);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean quetQRDiemDanh(String tenPhongHoc, long maSinhVien, String matKhau) {
		long madiemdanh = getMaDiemdanh(maSinhVien, matKhau, tenPhongHoc);
		if(madiemdanh != 0) {
			// Ngày hiện tại
			LocalDate now = LocalDate.now();
			Date date = java.sql.Date.valueOf(now);
			// them du lieu vao ban chi tiet diem danh 
			PreparedStatement smt = null;
			int n = 0;
			try {
				Connection con = app.getConnection();
				String sql = "INSERT INTO chitietdiemdanh (madiemdanh,ngaydiemdanh,status) values (?,?,?)";
				smt = con.prepareStatement(sql);
				smt.setLong(1, madiemdanh);
				smt.setDate(2, (java.sql.Date) date);
				smt.setBoolean(3, true);
				n = smt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return n>0;
		}
		return false;
	}

	// lấy mã diem danh của môn học hiện tại
	public long getMaDiemdanh(long maSinhVien, String matKhau, String tenPhongHoc) {
		MonHocHienTai monHocHienTai = monHocHienTai(maSinhVien, matKhau);
		if(monHocHienTai != null && monHocHienTai.getTenPhongHoc().equalsIgnoreCase(tenPhongHoc)) {
			long madiemdanh = 0;
			PreparedStatement smt = null;
			try {
				Connection con = app.getConnection();
			String sql = "SELECT d.madiemdanh FROM diemdanh d WHERE d.masinhvien = ? AND d.mamonhoc = ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maSinhVien);
			smt.setLong(2, monHocHienTai.getMaMonHoc());
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				madiemdanh = rs.getLong("madiemdanh");
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return madiemdanh;
		}
		return 0;
	}
	
		
	public MonHocHienTai monHocHienTai(long maSinhVien, String matKhau) {
		// lay thu cua ngay hien tai
				Calendar calendar = Calendar.getInstance();
				int thu = calendar.get(Calendar.DAY_OF_WEEK);
				Date d = new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				MonHocHienTai monHocHienTai = null;
				PreparedStatement smt = null;
				try {
					Connection con = app.getConnection();
					String sql = "SELECT m.mamonhoc, m.tenmonhoc, m.ngaybatdau , m.ngayketthuc, p.tenphonghoc, c.giobatdau, c.gioketthuc FROM nguoidung n, monhoc m,diemdanh d, cahoc c, phonghoc p WHERE n.status = 0 AND n.ma = d.masinhvien AND d.mamonhoc = m.mamonhoc AND m.mamonhoc = c.macahoc AND c.maphonghoc = p.maphonghoc AND c.thu = ? AND  n.ma = ? AND n.matkhau = ?";
					smt = con.prepareStatement(sql);
					smt.setInt(1, thu);
					smt.setLong(2, maSinhVien);
					smt.setString(3, matKhau);
					ResultSet rs= smt.executeQuery();
					while(rs.next()) {
						long maMonHoc = rs.getLong("mamonhoc");
						String tenMonHoc = rs.getString("tenmonhoc");
						Date ngayBD = rs.getDate("ngaybatdau");
						String ngayBatDau = dateFormat.format(ngayBD);
						Date ngayKT = rs.getDate("ngayketthuc");
						String ngayKetThuc = dateFormat.format(ngayKT);
						String tenPhongHoc = rs.getString("tenphonghoc");
						String gioBatDau = rs.getString("giobatdau");
						String gioKetThuc = rs.getString("gioketthuc");
						if(d.after(ngayBD) && d.before(ngayKT)){
							LocalTime now = LocalTime.now();
							LocalTime timeStart = LocalTime.parse(gioBatDau);
							LocalTime timeEnd = LocalTime.parse(gioKetThuc);
							// hiển thị môn học hiện tại trước 10 phút đầu giờ và sau 10 phút cuối giờ
							if(now.isAfter(timeStart.minusMinutes(10)) && now.isBefore(timeEnd.minusMinutes(10))) {
								monHocHienTai = new MonHocHienTai(maMonHoc, tenMonHoc, ngayBatDau, ngayKetThuc, tenPhongHoc, gioBatDau, gioKetThuc);
							}
							
						}
					}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
				      
				return monHocHienTai;
	}
	public List<MonHoc> getTenMonHoc(long maSinhVien) {
		PreparedStatement smt = null;
		List<MonHoc> listMonHoc = new ArrayList<MonHoc>();
		MonHoc m = null;
		String tenmonhoc= null;
		long mamonhoc;
		
		try {
			Connection con = app.getConnection();
			String sql = "SELECT m.mamonhoc , m.tenmonhoc FROM monhoc m , nguoidung n, diemdanh d WHERE n.ma = d.masinhvien AND d.mamonhoc = m.mamonhoc AND n.ma = ? AND n.status = 0";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maSinhVien);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				mamonhoc = rs.getLong("mamonhoc");
				tenmonhoc = rs.getString("tenmonhoc");
				m = new MonHoc(mamonhoc, tenmonhoc);
				listMonHoc.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMonHoc;
	}

	public List<DiemDanhSinhVien> xemChiTietDiemDanh(long maSinhVien, long maMonHoc) {
		PreparedStatement smt = null;
		List<DiemDanhSinhVien> listMonHoc = new ArrayList<DiemDanhSinhVien>();
		DiemDanhSinhVien m = null;
		String ngayDD = null;
		Date ngaydiemdanh= null;
		int status;
		List<String> ngayhoc = getNgayHoc(maSinhVien, maMonHoc);
		DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Connection con = app.getConnection();
			String sql = "SELECT c.ngaydiemdanh ,c.status FROM diemdanh d, chitietdiemdanh c WHERE d.madiemdanh = c.madiemdanh AND d.masinhvien =? AND d.mamonhoc = ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maSinhVien);
			smt.setLong(2, maMonHoc);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				ngaydiemdanh = rs.getDate("ngaydiemdanh");
				ngayDD = simpleDateFormat.format(ngaydiemdanh);
				status = rs.getInt("status");
				m = new DiemDanhSinhVien(ngayDD, status);
				listMonHoc.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMonHoc;
	}
	
	public List<String> getNgayHoc(long maSinhVien, long maMonHoc) {
		PreparedStatement smt = null;
		List<String> list = new ArrayList<String>();
		String ngayDiemDanh = null;
		Date ngaydiemdanh = null;
		String thu = null;
		DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Connection con = app.getConnection();
			String sql = "SELECT DISTINCT c.ngaydiemdanh FROM diemdanh d, chitietdiemdanh c WHERE d.madiemdanh = c.madiemdanh AND d.masinhvien = ? AND d.mamonhoc = ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maSinhVien);
			smt.setLong(2, maMonHoc);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				ngaydiemdanh = rs.getDate("ngaydiemdanh");
				ngayDiemDanh = simpleDateFormat.format(ngaydiemdanh);
				list.add(ngayDiemDanh);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public int getStatusChiTietDiemDanh(long maSinhVien) {
		PreparedStatement smt = null;
		int status = 0;
		LocalDate now = LocalDate.now();
		try {
			Connection con = app.getConnection();
			String sql = "SELECT c.status FROM diemdanh d , chitietdiemdanh c WHERE d.madiemdanh = c.madiemdanh AND d.masinhvien = ? AND c.ngaydiemdanh = ? ";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maSinhVien);
			smt.setString(2, now.toString());
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				status = rs.getInt("status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
//	public List<String> getNgayHoc(long maSinhVien, long maMonHoc) {
//		PreparedStatement smt = null;
//		List<String> list = new ArrayList<String>();
//		String ngaybatdau = null;
//		String ngayketthuc = null;
//		String thu = null;
//		try {
//			Connection con = app.getConnection();
//			String sql = "SELECT m.ngaybatdau, m.ngayketthuc,c.thu FROM monhoc m , nguoidung n, cahoc c, diemdanh d WHERE d.masinhvien = n.ma AND d.mamonhoc = m.mamonhoc AND n.status = 0 AND m.mamonhoc = c.macahoc AND n.ma = ? AND m.mamonhoc = ?";
//			smt = con.prepareStatement(sql);
//			smt.setLong(1, maSinhVien);
//			smt.setLong(2, maMonHoc);
//			ResultSet rs= smt.executeQuery();
//			while(rs.next()) {
//				ngaybatdau = rs.getString("ngaybatdau");
//				ngayketthuc = rs.getString("ngayketthuc");
//				thu = rs.getString("thu");
//			}
//			
//
//			DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			
//			LocalDate start = LocalDate.parse(ngaybatdau);
//			LocalDate end = LocalDate.parse(ngayketthuc);
//			Calendar calendar = Calendar.getInstance();
//
//
//			Date  ngayBatDau = simpleDateFormat.parse(ngaybatdau);
//			Date  ngayKetThuc = simpleDateFormat.parse(ngayketthuc);
//
//			long getDiff = ngayKetThuc.getTime() - ngayBatDau.getTime();
//
//			long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
//			for (long i = 0; i < getDaysDiff; i++) {
//				
//				start=start.plusDays(1);
//				Date ngay = simpleDateFormat.parse(start.toString());
//		        calendar.setTime(ngay);
//		        String thuOfDay =calendar.get(Calendar.DAY_OF_WEEK)+"";
//				if(thuOfDay.trim().equals(thu)) {
//					list.add(start.toString());
//				}
//			}
//		
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	public boolean quetQRDiemDanhLan1(String tenPhongHoc, long maSinhVien, String matKhau) {
		// lấy mã diem danh của môn học hiện tại
		long madiemdanh = getMaDiemdanh(maSinhVien, matKhau);
		int n = 0;
		if(madiemdanh != 0 && checkQuetMaLan1(maSinhVien, matKhau) == true) {
			// Ngày hiện tại
			LocalDate now = LocalDate.now();
			PreparedStatement smt = null;
			try {
				Connection con = app.getConnection();
				String sql = "UPDATE chitietdiemdanh c, diemdanh d set c.status = 2 WHERE d.madiemdanh = c.madiemdanh AND d.masinhvien = ? AND c.ngaydiemdanh = ? AND d.madiemdanh = ?";
				smt = con.prepareStatement(sql);
				smt.setLong(1, maSinhVien);
				smt.setString(2, now.toString());
				smt.setLong(3, madiemdanh);
				n = smt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return n>0;
		
	}
	
	public boolean quetQRDiemDanhLan2(String tenPhongHoc, long maSinhVien, String matKhau) {
		// lấy mã diem danh của môn học hiện tại
		long madiemdanh = getMaDiemdanh(maSinhVien, matKhau);
		int n = 0;
		if(madiemdanh != 0 && checkQuetMaLan2(maSinhVien, matKhau) == true) {
			// Ngày hiện tại
			LocalDate now = LocalDate.now();
			PreparedStatement smt = null;
			try {
				Connection con = app.getConnection();
				String sql = "UPDATE chitietdiemdanh c, diemdanh d set c.status = 1 WHERE d.madiemdanh = c.madiemdanh AND d.masinhvien = ? AND c.ngaydiemdanh = ? AND d.madiemdanh=?";
				smt = con.prepareStatement(sql);
				smt.setLong(1, maSinhVien);
				smt.setString(2, now.toString());
				smt.setLong(3, madiemdanh);
				n = smt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return n>0;
		
	}
	
	// lấy mã diem danh của môn học hiện tại
	public long getMaDiemdanh(long maSinhVien, String matKhau) {
		MonHocHienTai monHocHienTai = monHocHienTai(maSinhVien, matKhau);
		long madiemdanh = 0;
		if(monHocHienTai != null) {
			PreparedStatement smt = null;
			try {
				Connection con = app.getConnection();
			String sql = "SELECT d.madiemdanh FROM diemdanh d WHERE d.masinhvien = ? AND d.mamonhoc = ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, maSinhVien);
			smt.setLong(2, monHocHienTai.getMaMonHoc());
			ResultSet rs= smt.executeQuery();
				while(rs.next()) {
					madiemdanh = rs.getLong("madiemdanh");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return madiemdanh;
	}
		
	public boolean checkQuetMaLan1(long maSinhVien, String matKhau) {
		MonHocHienTai monHoc = monHocHienTai(maSinhVien, matKhau);
				LocalTime now = LocalTime.now();
				LocalTime timeStart = LocalTime.parse(monHoc.getGioBatDau());
				// hiển thị môn học hiện tại trước 10 phút đầu giờ và sau 10 phút cuối giờ
				if(now.isAfter(timeStart.minusMinutes(10)) && now.isBefore(timeStart.plusMinutes(10))) {
					return true;
				}
		return false;
	}
	
	
	public boolean checkQuetMaLan2(long maSinhVien, String matKhau) {
		MonHocHienTai monHoc = monHocHienTai(maSinhVien, matKhau);
				LocalTime now = LocalTime.now();
				LocalTime timeEnd = LocalTime.parse(monHoc.getGioKetThuc());
				// hiển thị môn học hiện tại trước 10 phút đầu giờ và sau 10 phút cuối giờ
				if(now.isAfter(timeEnd.minusMinutes(10)) && now.isBefore(timeEnd.plusMinutes(10)) ) {
					return true;
				}
		
		return false;
	}
}
