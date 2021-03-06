package com.zic.diemdanhapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ThongTinGiaoVien extends AppCompatActivity {

    private String manhanduoc, passnhanduoc;

    private String ma, ten, hinh, ngaysinh, gioitinh, trinhdo, chucvu, tenkhoa, pass, tenlop;

    private String mamon, tenmon, ngaybd, ngaykt, tenphong, giobd, giokt;

//    ProgressDialog progressDialog;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_giao_vien);
        getSupportActionBar().hide();
        ImageView btnThongBao = findViewById(R.id.btnThongBao);

        // Nhận mã SV từ phần đăng nhập
        Intent nhanpass = getIntent();
        manhanduoc = nhanpass.getStringExtra("ma");
        passnhanduoc = nhanpass.getStringExtra("pass");

        // Lấy thông tin GV theo mã
        String urlthongtingiaovien = "nguoidung/findById/" + manhanduoc;
        new HttpAsyncTask().execute(MethodChung.CreateURL() + urlthongtingiaovien);

        //Lấy môn học hiện tại
        String urlmonhientai = "giaovien/monHocHienTai/" + manhanduoc + "/" + passnhanduoc;
        new HttpAsyncTaskMonHienTaiGV().execute(MethodChung.CreateURL() + urlmonhientai);


        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        final NavigationView navigationView = findViewById(R.id.navigationview);
        toggle.syncState();
        ImageView toolbar = findViewById(R.id.btnmenu);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThongTin = new Intent(ThongTinGiaoVien.this, ThongBaoGiaoVien.class);
                intentThongTin.putExtra("ma", manhanduoc);
                startActivity(intentThongTin);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);

        // Sự kiện bấm nút Quét mã QR
        Button btnqr = findViewById(R.id.btnQRGV);
        btnqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentqr = new Intent(ThongTinGiaoVien.this, QuetMaQR.class);
                intentqr.putExtra("ma", manhanduoc);
                intentqr.putExtra("status", "1");
                intentqr.putExtra("pass", pass);
                startActivity(intentqr);
            }
        });
    }

    public void selectItemDrawer(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.bangDiemDanh:
                Intent intentb = new Intent(ThongTinGiaoVien.this, ChiTietDiemDanh.class);
                intentb.putExtra("ma", manhanduoc);
                intentb.putExtra("status", "1");
                startActivity(intentb);
                break;
            case R.id.xemLichDay:
                Intent intentb1 = new Intent(ThongTinGiaoVien.this, XemLichHoc.class);
                intentb1.putExtra("ma", manhanduoc);
                intentb1.putExtra("status", "1");
                startActivity(intentb1);
                break;
            case R.id.doiMatKhau:
                Intent intentb2 = new Intent(ThongTinGiaoVien.this, ChangePass.class);
                intentb2.putExtra("ma", manhanduoc);
                intentb2.putExtra("status", "1");
                intentb2.putExtra("ten", ten);
                startActivity(intentb2);
                break;
            case R.id.dangXuat:
                Intent intentb3 = new Intent(ThongTinGiaoVien.this, MainActivity.class);
                intentb3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentb3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentb3);
                break;
            case R.id.thongTinTaiKhoan:
                Intent intentb4 = new Intent(ThongTinGiaoVien.this, ChinhSuaSV.class);
                intentb4.putExtra("ma", manhanduoc);
                intentb4.putExtra("status", "1");
                startActivity(intentb4);
                break;
            default:
                fragmentClass = ThongTinGiaoVien.class;
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
    }

    // Không cho bấm nút Back quay lại màn hình Đăng nhập
    @Override
    public void onBackPressed() {

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
            TextView tenNavi = findViewById(R.id.txtViewTenNavi);
            TextView lopNavi = findViewById(R.id.txtViewLopNavi);
            TextView maNavi = findViewById(R.id.txtViewMaNavi);

            try {
                JSONObject jsonObj = new JSONObject(result); // convert String to JSONObject
                ma = jsonObj.getString("maNguoiDung");
                ten = jsonObj.getString("tenNguoiDung");
                hinh = jsonObj.getString("hinh");
                ngaysinh = jsonObj.getString("ngaySinh");
                gioitinh = jsonObj.getString("gioiTinh");
                trinhdo = jsonObj.getString("trinhDo");
                tenlop = jsonObj.getString("tenLop");
                chucvu = jsonObj.getString("chucVu");
                tenkhoa = jsonObj.getString("tenKhoa");
                pass = jsonObj.getString("matKhau");

                tenNavi.setText(ten);
                lopNavi.setText(tenlop);
                maNavi.setText(ma);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    //Hàm xử lý JSON môn học hiện tại
    private class HttpAsyncTaskMonHienTaiGV extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return MethodChung.GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            TextView txtmagv = findViewById(R.id.txtViewMaMonHocGV);
            TextView txttengv = findViewById(R.id.txtViewTenMonHocGV);
            TextView txtphong = findViewById(R.id.txtViewTenPhongHocGV);
            TextView txtgiobd = findViewById(R.id.txtViewGioBatDauGV);
            TextView txtgiokt = findViewById(R.id.txtViewGioKetThucGV);


            try {
                JSONObject jsonObj = new JSONObject(result); // convert String to JSONObject
                mamon = jsonObj.getString("maMonHoc");
                tenmon = jsonObj.getString("tenMonHoc");
                ngaybd = jsonObj.getString("ngayBatDau");
                ngaykt = jsonObj.getString("ngayKetThuc");
                tenphong = jsonObj.getString("tenPhongHoc");
                giobd = jsonObj.getString("gioBatDau");
                giokt = jsonObj.getString("gioKetThuc");

                txtmagv.setText(mamon);
                txttengv.setText(tenmon);
                txtphong.setText(tenphong);
                txtgiobd.setText(giobd);
                txtgiokt.setText(giokt);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
