package com.thientri.api.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thientri.api.idao.GiaoVienIDAO;
import com.thientri.api.model.ChiTietDiemDanh;
import com.thientri.api.model.FileChiTietDiemDanh;
import com.thientri.api.model.Lich;
import com.thientri.api.model.MonHoc;
import com.thientri.api.model.MonHocHienTai;
import com.thientri.api.model.SinhVien;

@RestController
@RequestMapping("giaovien")
@CrossOrigin(origins = "*")
public class GiaoVienController {

	@Autowired
	private GiaoVienIDAO giaoVienIDAO;

	private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}

	@GetMapping(value = "/xemLichDay/{magiaovien}", produces = "application/json;charset=UTF-8")
	public List<Lich> xemLichDay(@PathVariable("magiaovien") long magiaovien) {
		return giaoVienIDAO.xemLichDay(magiaovien);
	}

	// ngày điểm danh set theo định dạng 2019-04-28 { yyyyMMdd }
	@GetMapping(value = "/xemChiTietDiemDanh/{maMonHoc}/{ngayDiemDanh}", produces = "application/json;charset=UTF-8")
	public List<ChiTietDiemDanh> xemChiTietDiemDanh(@PathVariable("maMonHoc") long maMonHoc,
			@PathVariable("ngayDiemDanh") String ngayDiemDanh) {
		String ngay =ngayDiemDanh.substring(0, 2);
		String thang =ngayDiemDanh.substring(2, 4);
		String nam = ngayDiemDanh.substring(4,8);
		String ngayBD = nam+"-"+thang+"-"+ngay;
		
		return giaoVienIDAO.xemChiTietDiemDanh(maMonHoc, ngayBD);
	}

	
	//Phần này sai cần check 2 lần nên cần 1 table temp check lần 1, check lần 2 sẽ update vao bang chi tiet diem danh
	@GetMapping(value = "/quetQRDiemDanh/{maSinhVien}/{maGiaoVien}/{matKhauGiaoVien}", produces = "application/json;charset=UTF-8")
	public boolean quetQRDiemDanh(@PathVariable("maSinhVien") long maSinhVien,@PathVariable("maGiaoVien") long maGiaoVien, @PathVariable("matKhauGiaoVien") String matKhauGiaoVien) {
		int status = giaoVienIDAO.getStatusChiTietDiemDanh(maSinhVien, maGiaoVien);
		//quet lan dau
		if(status == 0) {
			System.out.println(status);
			return giaoVienIDAO.quetQRDiemDanhLan1(maSinhVien, maGiaoVien, matKhauGiaoVien);
		}
		else if (status ==2) {
			return giaoVienIDAO.quetQRDiemDanhLan2(maSinhVien, maGiaoVien, matKhauGiaoVien);
		}
		return false;
	}

	@GetMapping(value = "/monHocHienTai/{maGiaoVien}/{matKhau}", produces = "application/json;charset=UTF-8")
	public MonHocHienTai monHocHienTai(@PathVariable("maGiaoVien") long maGiaoVien,
			@PathVariable("matKhau") String matKhau) {
		return giaoVienIDAO.monHocHienTai(maGiaoVien, matKhau);
	}

	@GetMapping(value = "/getTenMonHoc/{maGiaoVien}", produces = "application/json;charset=UTF-8")
	public List<MonHoc> getTenMonHoc(@PathVariable("maGiaoVien") long maGiaoVien) {
		return giaoVienIDAO.getTenMonHoc(maGiaoVien);
	}

	// Can lay ngay hoc trong ban chi tiet mon
	@GetMapping(value = "/getNgayHoc/{maGiaoVien}/{maMonHoc}", produces = "application/json;charset=UTF-8")
	public List<String> getNgayHoc(@PathVariable("maGiaoVien") long maGiaoVien,
			@PathVariable("maMonHoc") long maMonHoc) {
		return giaoVienIDAO.getNgayHoc(maGiaoVien, maMonHoc);
	}

	@GetMapping(value = "/taoListChiTietDiemDanh/{maGiaoVien}/{maMonHoc}", produces = "application/json;charset=UTF-8")
	public boolean taoListChiTietDiemDanh(@PathVariable("maGiaoVien") long maGiaoVien,@PathVariable("maMonHoc") long maMonHoc) {
		return giaoVienIDAO.taoListChiTietDiemDanh(maGiaoVien, maMonHoc);
	}
	
	
	@GetMapping(value = "/fileChiTietDiemDanh/{maGiaoVien}/{maMonHoc}", produces = "application/json;charset=UTF-8")
	public boolean fileChiTietDiemDanh(@PathVariable("maGiaoVien") long maGiaoVien,
			@PathVariable("maMonHoc") long maMonHoc) {
		boolean n = false;
		String tenMonHoc = giaoVienIDAO.tenMonHoc(maMonHoc);
		List<String> listNgayHoc = giaoVienIDAO.getNgayHoc(maGiaoVien, maMonHoc);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Môn "+tenMonHoc);
		List<SinhVien> listSV = giaoVienIDAO.getThongTinSinhVien(maMonHoc);

		int rownum = 0;
		Cell cell;
		Row row;
		//
		HSSFCellStyle style = createStyleForTitle(workbook);

		row = sheet.createRow(rownum);

		// maSinhVien
		cell = row.createCell(0, CellType.STRING);
		cell.setCellValue("Mã Sinh Viên");
		cell.setCellStyle(style);
		// tenSinhVien
		cell = row.createCell(1, CellType.STRING);
		cell.setCellValue("Tên Sinh Viên");
		cell.setCellStyle(style);
		// tenLop
		cell = row.createCell(2, CellType.STRING);
		cell.setCellValue("Lớp");
		cell.setCellStyle(style);
		// gioitinh
		cell = row.createCell(3, CellType.STRING);
		cell.setCellValue("Giới Tính");
		cell.setCellStyle(style);
		
		for (int i = 0; i < listNgayHoc.size(); i++) {
			// ngayDiemDanh
			int cell_N = 4+i;
			cell = row.createCell(cell_N, CellType.STRING);
		    cell.setCellValue(listNgayHoc.get(i));
			cell.setCellStyle(style);
			
		}
		
		
		
		for (SinhVien c : listSV) {
            rownum++;
            row = sheet.createRow(rownum);
 
            // maSinhVien (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(c.getMaSinhVien());
            // tenSinhVien (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(c.getTenSinhVien());
            // tenLop (C)
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(c.getTenLop());
            // gioitinh (D)
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(c.getGioiTinh());
			// ngayDiemDanh (E)
            for (int i = 0; i < listNgayHoc.size(); i++) {
            	int cell_N = 4+i;
            	cell = row.createCell(cell_N, CellType.STRING);	
            	//Ngay nhận 20-05-2019
            	String ngay =listNgayHoc.get(i).substring(0, 2);
        		String thang =listNgayHoc.get(i).substring(3, 5);
        		String nam = listNgayHoc.get(i).substring(6,10);
        		String ngayHoc = nam+"-"+thang+"-"+ngay;
        		//2019-05-20
//        		System.out.println(ngayHoc);
            	String checkDiemDanh = giaoVienIDAO.CheckDiemDanh(maMonHoc, ngayHoc,c.getMaSinhVien());
    			if(checkDiemDanh == null) {
    				cell.setCellValue("Không");
    			}else {
    				cell.setCellValue("Có");
    			}
    		}
            
        }
		String tenmonhoc = giaoVienIDAO.tenMonHoc(maMonHoc);
        File file = new File("D:/Android/DiemDanhSinhVien/API/chi tiết điểm danh "+tenMonHoc+".xls");
        file.getParentFile().mkdirs();
 
        FileOutputStream outFile;
		try {
			outFile = new FileOutputStream(file);
			workbook.write(outFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

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
 
        String email = giaoVienIDAO.getEmailGV(maGiaoVien);
        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("diemdanhsinhvienapp@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            String temp = Normalizer.normalize(tenmonhoc, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String TenMH = pattern.matcher(temp).replaceAll("");
            message.setSubject("File chi tiet diem danh mon "+TenMH);
 
            // 3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("App diem danh sinh vien goi ban file chi tiet diem danh mon "+ TenMH);
 
            // 4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
 
            String filename = file.getAbsolutePath();
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName("chi tiet diem danh mon "+TenMH+".xls");
 
            // 5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
 
            // 6) set the multiplart object to the message object
            message.setContent(multipart);
 
            // 7) send message
            Transport.send(message);
            System.out.println("Message sent successfully");
            
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
	}
}
