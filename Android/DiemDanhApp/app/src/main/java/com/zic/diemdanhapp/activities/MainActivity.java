package com.zic.diemdanhapp.activities;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.Handler;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String ma, pass, status;

    public static String filename = "Valustoringfile";
    SharedPreferences SP;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        final TextView txtname = findViewById(R.id.txtName);
        final EditText txtpass = findViewById(R.id.txtPass);
        SP = getSharedPreferences(filename, 0);
        String getname = SP.getString("key1", "");
        String getpass = SP.getString("key2", "");
        txtname.setText(getname);
        txtpass.setText(getpass);

        // Sự kiện bấm nút Login
        Button btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected() == true) {
                    // Kiểm tra đã nhập chưa
                    if (txtname.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Bạn chưa nhập mã SV/GV ~", Toast.LENGTH_SHORT).show();
                    else if (txtpass.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu ~", Toast.LENGTH_SHORT).show();
                    else {

                        String urldangnhap = "nguoidung/" + "dangNhap/" + txtname.getText().toString() + "/" + txtpass.getText().toString();

                        // Thực hiện code Json
                        new HttpAsyncTask().execute(MethodChung.CreateURL() + urldangnhap);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Không có kết nối mạng ...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Sự kiện bấm nút Quên mật khẩu
        TextView btnFor = findViewById(R.id.btnForget);
        btnFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chuyenlayoutForget = new Intent(getApplicationContext(), ForgetPass.class);
                startActivity(chuyenlayoutForget);
            }
        });
    }


    //Hàm kiểm tra kết nối mạng
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
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
                pass = jsonObj.getString("matKhau");
                status = jsonObj.getString("status");

                // Hiện progress bar
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Đang chạy ..."); // Setting Message
                progressDialog.setTitle("Đang kiểm tra ~"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    public void run() {
                    }
                }).start();

                //Delay 1s
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Tắt progress bar
                        progressDialog.dismiss();

                        // Nếu CheckBox "Nhớ mật khẩu" được chọn
                        TextView txtname = findViewById(R.id.txtName);
                        EditText txtpass = findViewById(R.id.txtPass);
                        CheckBox checkboxnhomk = findViewById(R.id.checkBoxForget);
                        if (checkboxnhomk.isChecked()) {
                            SharedPreferences.Editor editit = SP.edit();
                            editit.putString("key1", txtname.getText().toString());
                            editit.putString("key2", txtpass.getText().toString());
                            editit.commit();
                        } else {
                            SharedPreferences.Editor editit = SP.edit();
                            editit.putString("key1", null);
                            editit.putString("key2", null);
                            editit.commit();
                        }

                        // Chuyển layout tương ứng nếu pass đúng
                        if (status.equals("1")) {
                            Intent chuyenlayoutGV = new Intent(getApplicationContext(), ThongTinGiaoVien.class);
                            chuyenlayoutGV.putExtra("ma", ma);
                            chuyenlayoutGV.putExtra("pass", pass);
                            startActivity((chuyenlayoutGV));
                        } else if (status.equals("0")) {
                            Intent chuyenlayoutSV = new Intent(getApplicationContext(), ThongTinSinhVien.class);
                            chuyenlayoutSV.putExtra("ma", ma);
                            chuyenlayoutSV.putExtra("pass", pass);
                            startActivity((chuyenlayoutSV));
                        } else
                            Toast.makeText(MainActivity.this, "Lỗi ...", Toast.LENGTH_SHORT).show();

                    }
                }, 1000);

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Sai thông tin tài khoản ...", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


}