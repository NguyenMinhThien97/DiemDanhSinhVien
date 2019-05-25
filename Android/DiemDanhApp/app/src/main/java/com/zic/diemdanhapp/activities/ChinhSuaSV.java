package com.zic.diemdanhapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChinhSuaSV extends AppCompatActivity {

    private String manhanduoc, status;

    private String ma, ten, hinh, ngaysinh, gioitinh, sdt, email, tenlop, trinhdo, chucvu, tenkhoa, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_sv);
        getSupportActionBar().hide();

        // Nhận mã + status tài khoản
        Intent nhanpass = getIntent();
        manhanduoc = nhanpass.getStringExtra("ma");
        status = nhanpass.getStringExtra("status");

        // thực hiện code JSON
        new HttpAsyncTask().execute(MethodChung.CreateURL() + "nguoidung/findById/" + manhanduoc);


        // Sự kiện bấm nút Sửa
        Button btnsua = findViewById(R.id.btnSua);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        // Sự kiện bấm nút Đổi mật khẩu
        Button btndoi = findViewById(R.id.btnChangePass);
        btndoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentb2 = new Intent(ChinhSuaSV.this, ChangePass.class);
                intentb2.putExtra("ma", manhanduoc);
                intentb2.putExtra("status", status);
                intentb2.putExtra("ten", ten);
                startActivity(intentb2);
            }
        });

    }

//    //Hàm kiểm tra kết nối
//    public boolean isConnected() {
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected())
//            return true;
//        else
//            return false;
//    }

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

            TextView txtngaysinh = findViewById(R.id.txtNgaySinh);
            TextView txtgioitinh = findViewById(R.id.txtGioiTinh);
            TextView txtsdt = findViewById(R.id.txtSDT);
            TextView txtemail = findViewById(R.id.txtEmail);

            // Tên lớp chỉ có sv có, gv không có
            TextView txtkhoalop = findViewById(R.id.txtKhoaLop);

            TextView txttrinhdo = findViewById(R.id.txtTrinhDo);
            TextView txtchucvu = findViewById(R.id.txtChucVu);
            TextView txttenkhoa = findViewById(R.id.txtTenKhoa);

            try {
                JSONObject jsonObj = new JSONObject(result); // convert String to JSONObject
                ma = jsonObj.getString("maNguoiDung");
                ten = jsonObj.getString("tenNguoiDung");
                hinh = jsonObj.getString("hinh");
                ngaysinh = jsonObj.getString("ngaySinh");
                gioitinh = jsonObj.getString("gioiTinh");
                sdt = jsonObj.getString("sodienthoai");
                email = jsonObj.getString("email");
                tenlop = jsonObj.getString("tenLop");
                trinhdo = jsonObj.getString("trinhDo");
                chucvu = jsonObj.getString("chucVu");
                tenkhoa = jsonObj.getString("tenKhoa");

                tenNavi.setText(ten);

                if (status.equals("0"))
                    lopNavi.setText(tenlop);
                else if (status.equals("1"))
                    lopNavi.setText(tenkhoa);

                maNavi.setText(ma);

                txtngaysinh.setText(ngaysinh);
                txtgioitinh.setText(gioitinh);
                txtsdt.setText(sdt);
                txtemail.setText(email);
                txtkhoalop.setText(tenlop);
                txttrinhdo.setText(trinhdo);
                txtchucvu.setText(chucvu);
                txttenkhoa.setText(tenkhoa);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
