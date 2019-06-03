package com.zic.diemdanhapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.model.ThongBao;

public class ChiTietThongBaoSinhVien extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    TextView txtTenThongBao, txtNoiDungThongBao , txtTenGiaoVien, txtNgayTao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thong_bao_sinh_vien);
        Anhxa();
        ActionToolbar();
        GetInformation();
    }

    private void GetInformation() {
        String tenGiaoVien = "";
        String tenThongBao = "";
        String noiDung = "";
        String ngayTao = "";
        ThongBao thongBao = (ThongBao) getIntent().getSerializableExtra("thongbaoSV");
        tenGiaoVien  = thongBao.getTenGiaoVien();
        tenThongBao = thongBao.getTenThongBao();
        noiDung = thongBao.getNoiDung();
        ngayTao = thongBao.getNgayTao();
        txtTenThongBao.setText(tenThongBao);
        txtNoiDungThongBao.setText(noiDung);
        txtTenGiaoVien.setText("Giáo viên: "+tenGiaoVien);
        txtNgayTao.setText("Ngày: "+ngayTao);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void Anhxa() {
        toolbarChiTiet = findViewById(R.id.toolbarChiTietSV);
        txtTenThongBao = findViewById(R.id.textviewTenThongBaoSV);
        txtNoiDungThongBao = findViewById(R.id.textviewNoiDungThongBaoSV);
        txtTenGiaoVien = findViewById(R.id.textviewTenGiaoVienSV);
        txtNgayTao = findViewById(R.id.textviewNgayTaoSV);

    }
}
