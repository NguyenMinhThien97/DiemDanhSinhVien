package com.zic.diemdanhapp.model;

import java.io.Serializable;

public class ThongBao implements Serializable {
    public long maThongBao;
    public String tenGiaoVien;
    public String tenMonHoc;
    public String tenThongBao;
    public String noiDung;
    public String ngayTao;

    public ThongBao(long maThongBao, String tenGiaoVien, String tenMonHoc, String tenThongBao, String noiDung, String ngayTao) {
        this.maThongBao = maThongBao;
        this.tenGiaoVien = tenGiaoVien;
        this.tenMonHoc = tenMonHoc;
        this.tenThongBao = tenThongBao;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
    }

    public long getMaThongBao() {
        return maThongBao;
    }

    public void setMaThongBao(long maThongBao) {
        this.maThongBao = maThongBao;
    }

    public String getTenGiaoVien() {
        return tenGiaoVien;
    }

    public void setTenGiaoVien(String tenGiaoVien) {
        this.tenGiaoVien = tenGiaoVien;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public String getTenThongBao() {
        return tenThongBao;
    }

    public void setTenThongBao(String tenThongBao) {
        this.tenThongBao = tenThongBao;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
