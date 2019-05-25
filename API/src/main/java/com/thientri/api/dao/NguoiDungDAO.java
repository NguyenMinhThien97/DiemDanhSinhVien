package com.thientri.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Repository;

import com.thientri.api.config.ApplicationContextConfig;
import com.thientri.api.idao.NguoiDungIDAO;
import com.thientri.api.model.NguoiDung;

@Repository
public class NguoiDungDAO implements NguoiDungIDAO {
	
	ApplicationContextConfig app;
	
	public List<NguoiDung> getAllNguoiDung() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		ArrayList<NguoiDung> list = new ArrayList<NguoiDung>() ;
		PreparedStatement smt = null;
		try {
			Connection con = app.getConnection();
			String sql = "select * from  nguoidung";
			smt = con.prepareStatement(sql);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				long maNguoiDung = rs.getLong("ma");
				String tenNguoiDung = rs.getString("ten");
				String hinh = rs.getString("hinh");
				Date ngayS = rs.getDate("ngaysinh");
				String ngaySinh = dateFormat.format(ngayS);
				String gioiTinh = rs.getString("gioitinh");
				String tenLop = rs.getString("tenlop");
				String trinhDo = rs.getString("trinhdo");
				String chucVu = rs.getString("chucvu");
				
				String tenKhoa = rs.getString("tenkhoa");
				String matKhau = rs.getString("matkhau");
				String sodienthoai = rs.getString("sodienthoai");
				String email = rs.getString("email");

				int status = rs.getInt("status");
				NguoiDung n = new NguoiDung(maNguoiDung, tenNguoiDung, hinh, ngaySinh, sodienthoai, email, gioiTinh, tenLop, trinhDo, chucVu, tenKhoa, matKhau, status);
				list.add(n);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean createNguoiDung(NguoiDung nguoiDung) {
		PreparedStatement smt = null;
		int n = 0;
		try {
			Connection con = app.getConnection();
			String sql = "insert into nguoidung values (?,?,?,?,?,?,?,?,?,?,?)";
			smt = con.prepareStatement(sql);
			smt.setLong(1, nguoiDung.getMaNguoiDung());
			smt.setString(2, nguoiDung.getChucVu());
			smt.setString(3, nguoiDung.getGioiTinh());
			smt.setString(4, nguoiDung.getHinh());
			smt.setString(5, nguoiDung.getMatKhau());
			smt.setString(6,nguoiDung.getNgaySinh());
			smt.setInt(7, nguoiDung.getStatus());
			smt.setString(8, nguoiDung.getTenNguoiDung());
			smt.setString(9, nguoiDung.getTenKhoa());
			smt.setString(10, nguoiDung.getTenLop());
			smt.setString(11, nguoiDung.getTrinhDo());
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

	public boolean updateNguoiDung(long ma, String matKhau, String soDienThoai, String email) {
		PreparedStatement smt = null;
		int n = 0;
		try {
			Connection con = app.getConnection();
			String sql = "UPDATE nguoidung n SET n.sodienthoai = ? , n.email = ? WHERE n.ma = ?, n.matkhau = ?";
			smt = con.prepareStatement(sql);
			smt.setString(1,soDienThoai );
			smt.setString(2, email);
			smt.setLong(3, ma);
			smt.setString(4, matKhau);
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

	public boolean deleteNguoiDungById(long ma) {
		PreparedStatement smt = null;
		int n = 0;
		try {
			Connection con = app.getConnection();
			String sql = "DELETE FROM nguoidung WHERE ma= ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, ma);
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

	public NguoiDung findById(long ma) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		NguoiDung n = null ;
		PreparedStatement smt = null;
		try {
			Connection con = app.getConnection();
			String sql = "select * from  nguoidung n where n.ma = ?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, ma);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				String tenNguoiDung = rs.getString("ten");
				String hinh = rs.getString("hinh");
				Date ngayS = rs.getDate("ngaysinh");
				String ngaySinh = dateFormat.format(ngayS);
				String gioiTinh = rs.getString("gioitinh");
				String tenLop = rs.getString("tenlop");
				String trinhDo = rs.getString("trinhdo");
				String chucVu = rs.getString("chucvu");
				
				String tenKhoa = rs.getString("tenkhoa");
				String matKhau = rs.getString("matkhau");
				String sodienthoai = rs.getString("sodienthoai");
				String email = rs.getString("email");
				int status = rs.getInt("status");
				n = new NguoiDung(ma, tenNguoiDung, hinh, ngaySinh, sodienthoai, email, gioiTinh, tenLop, trinhDo, chucVu, tenKhoa, matKhau, status);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	public NguoiDung dangNhap(long ma, String matKhau) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		NguoiDung n = null ;
		PreparedStatement smt = null;
		try {
			Connection con = app.getConnection();
			String sql = "select * from  nguoidung n where n.ma = ? and n.matkhau=?";
			smt = con.prepareStatement(sql);
			smt.setLong(1, ma);
			smt.setString(2, matKhau);
			ResultSet rs= smt.executeQuery();
			while(rs.next()) {
				String tenNguoiDung = rs.getString("ten");
				String hinh = rs.getString("hinh");
				Date ngayS = rs.getDate("ngaysinh");
				String ngaySinh = dateFormat.format(ngayS);
				String gioiTinh = rs.getString("gioitinh");
				String tenLop = rs.getString("tenlop");
				String trinhDo = rs.getString("trinhdo");
				String chucVu = rs.getString("chucvu");
				
				String tenKhoa = rs.getString("tenkhoa");
				String sodienthoai = rs.getString("sodienthoai");
				String email = rs.getString("email");

				int status = rs.getInt("status");
				n = new NguoiDung(ma, tenNguoiDung, hinh, ngaySinh, sodienthoai, email, gioiTinh, tenLop, trinhDo, chucVu, tenKhoa, matKhau, status);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	public boolean doiMatKhau(long ma, String matKhauCu, String matKhauMoi) {
		PreparedStatement smt = null;
		int n = 0;
		try {
			Connection con = app.getConnection();
			String sql = "update nguoidung n set n.matkhau=? where n.ma=? and n.matkhau = ?";
			smt = con.prepareStatement(sql);
			smt.setString(1, matKhauMoi);
			smt.setLong(2, ma);
			smt.setString(3,matKhauCu);
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}
	
	public boolean doiMatKhauRandom(long ma, String email, String matKhauMoi) {
		PreparedStatement smt = null;
		int n = 0;
		try {
			Connection con = app.getConnection();
			String sql = "update nguoidung n set n.matkhau=? where n.ma=? and n.email = ?";
			smt = con.prepareStatement(sql);
			smt.setString(1, matKhauMoi);
			smt.setLong(2, ma);
			smt.setString(3,email);
			n = smt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}
	
	public boolean quenMatKhau(long ma, String email) {
		String matKhauMoi = UUID.randomUUID().toString().substring(0, 6);
		boolean n = doiMatKhauRandom(ma, email, matKhauMoi);
		// Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", 465);
 
        // get Session
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("diemdanhsinhvienapp@gmail.com", "01627085898");
            }
        });
     // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.trim()));
            message.setSubject("Quen mat khau dang nhap cho tai khoan.");
            message.setText("Chao ban,\nGan day ban da quen mat khau cua minh, ban vui long nhap mat khau: \n\n\t\t" + matKhauMoi + "\n\nde dang nhap lai tai khoan.");
             
            // send message
            Transport.send(message);
             
            System.out.println("Message change password sent successfully");
        } catch (MessagingException e) {
        	e.printStackTrace();
        	return false;
        }
		
		return n;
	}

}
