package com.zic.diemdanhapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;
import com.zic.diemdanhapp.model.ThongBao;

public class ChiTietThongBaoGiaoVien extends AppCompatActivity {

    Toolbar toolbarChiTiet;
    TextView txtTenThongBao, txtNoiDungThongBao , txtTenGiaoVien, txtNgayTao;
    Button btnXoa, btnSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thong_bao_giao_vien);
        Anhxa();
        ActionToolbar();
        GetInformation();
        Intent intentma = getIntent();
        final String maGiaoVien = intentma.getStringExtra("ma");

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongBao thongBao = (ThongBao) getIntent().getSerializableExtra("thongbaoGV");
                String url = MethodChung.CreateURL() + "thongbao/deleteByID/" + thongBao.maThongBao;
                new HttpAsyncTask().execute(url);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongBao thongBao = (ThongBao) getIntent().getSerializableExtra("thongbaoGV");
                Intent chuyenlayoutThemTB = new Intent(ChiTietThongBaoGiaoVien.this, ThemThongBaoGV.class);
                chuyenlayoutThemTB.putExtra("thongbaoGV", thongBao);
                chuyenlayoutThemTB.putExtra("ma", maGiaoVien);
                startActivity((chuyenlayoutThemTB));
            }
        });
    }
    //Hàm xử lý JSON
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return MethodChung.GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if (result.equals("true")) {
                Intent intentma = getIntent();
                String maGiaoVien = intentma.getStringExtra("ma");
                Intent intent = new Intent(ChiTietThongBaoGiaoVien.this, ThongBaoGiaoVien.class);
                intent.putExtra("ma", maGiaoVien);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Đã xóa thông báo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Xóa không thành công \n\tThử lại!", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void GetInformation() {
        String tenGiaoVien = "";
        String tenThongBao = "";
        String noiDung = "";
        String ngayTao = "";
        ThongBao thongBao = (ThongBao) getIntent().getSerializableExtra("thongbaoGV");
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
        toolbarChiTiet = findViewById(R.id.toolbarChiTietGV);
        txtTenThongBao = findViewById(R.id.textviewTenThongBaoGV);
        txtNoiDungThongBao = findViewById(R.id.textviewNoiDungThongBaoGV);
        txtTenGiaoVien = findViewById(R.id.textviewTenGiaoVienGV);
        txtNgayTao = findViewById(R.id.textviewNgayTaoGV);
        btnSua = findViewById(R.id.btnSuaThongBao);
        btnXoa = findViewById(R.id.btnXoaThongBao);

    }

}
