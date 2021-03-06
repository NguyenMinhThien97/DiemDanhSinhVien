package com.zic.diemdanhapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePass extends AppCompatActivity {

    private String oldpass, ma, ten, manhanduoc, status;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getSupportActionBar().hide();

        // Nhận mã SV/GV
        Intent nhanpass = getIntent();
        manhanduoc = nhanpass.getStringExtra("ma");
        status = nhanpass.getStringExtra("status");
        ten = nhanpass.getStringExtra("ten");

        final EditText editoldpass = findViewById(R.id.editOldPass);
        final EditText editnewpass1 = findViewById(R.id.editNewPass1);
        final EditText editnewpass2 = findViewById(R.id.editNewPass2);
        final TextView txtviewchangema = findViewById(R.id.txtViewMa);
        final TextView txtviewchangeten = findViewById(R.id.txtViewTen);

        txtviewchangema.setText(manhanduoc);
        txtviewchangeten.setText(ten);

        // Sự kiện bấm nút Tiếp tục
        Button btntieptucchange = findViewById(R.id.btnTiepChange);
        btntieptucchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editoldpass.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Chưa nhập mật khẩu cũ.", Toast.LENGTH_SHORT).show();
                else if (editnewpass1.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Chưa nhập khẩu mới.", Toast.LENGTH_SHORT).show();
                else if (editnewpass2.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Chưa nhập lại mật khẩu mới.", Toast.LENGTH_SHORT).show();
                else {
                    String urlcheck = "nguoidung/" + "findById/" + txtviewchangema.getText().toString();

                    new HttpAsyncTask().execute(MethodChung.CreateURL() + urlcheck);

                    // Hiện progress bar
                    progressDialog = new ProgressDialog(ChangePass.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setTitle("Đang kiểm tra !"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                        }
                    }).start();

                    //Delay 1s để lấy dữ liệu ( phòng trường hợp mạng yếu )
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Tắt progress bar
                            progressDialog.dismiss();

                            if (!editoldpass.getText().toString().equals(oldpass)) {
                                Toast.makeText(getApplicationContext(), "Mật khẩu sai.", Toast.LENGTH_SHORT).show();
                            } else if (editoldpass.getText().toString().equals(oldpass)
                                    && !editnewpass2.getText().toString().equals(editnewpass1.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Nhập lại mật khẩu mới không đúng.", Toast.LENGTH_SHORT).show();
                            } else {
                                String urldoimatkhau = "nguoidung/" + "doiMatKhau/" + txtviewchangema.getText().toString()
                                        + "/" + editoldpass.getText().toString() + "/" + editnewpass2.getText().toString();

                                new HttpAsyncTask().execute(MethodChung.CreateURL() + urldoimatkhau);

                                // Hiện progress bar
                                progressDialog = new ProgressDialog(ChangePass.this);
                                progressDialog.setMessage("Đang chạy ..."); // Setting Message
                                progressDialog.setTitle("Đang thực hiện !"); // Setting Title
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                progressDialog.show(); // Display Progress Dialog
                                progressDialog.setCancelable(false);
                                new Thread(new Runnable() {
                                    public void run() {
                                    }
                                }).start();

                                //Delay 1s để lấy dữ liệu ( phòng trường hợp mạng yếu )
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Tắt progress bar
                                        progressDialog.dismiss();

                                        // Chuyển sang layout Main
                                        Intent chuyenlayoutMain = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(chuyenlayoutMain);
                                    }
                                }, 1000);
                            }
                        }
                    }, 1000);
                }
            }

        });

        // Sự kiện bấm nút Huỷ
        Button btnhuychange = findViewById(R.id.btnHuyChange);
        btnhuychange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("1")) {
                    Intent intentgv = new Intent(getApplicationContext(), ThongTinGiaoVien.class);
                    intentgv.putExtra("ma", manhanduoc);
                    startActivity(intentgv);
                } else if (status.equals("0")) {
                    Intent intentsv = new Intent(getApplicationContext(), ThongTinSinhVien.class);
                    intentsv.putExtra("ma", manhanduoc);
                    startActivity(intentsv);
                }
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
            try {
                JSONObject jsonObj = new JSONObject(result); // convert String to JSONObject
                ma = jsonObj.getString("maNguoiDung");
                oldpass = jsonObj.getString("matKhau");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
