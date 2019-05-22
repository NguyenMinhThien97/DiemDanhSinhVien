package com.zic.diemdanhapp.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.GMailSender;
import com.zic.diemdanhapp.adapters.MethodChung;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPass extends AppCompatActivity {

    private String ma, url, sdt, email, pass;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        getSupportActionBar().hide();

        final EditText editma = findViewById(R.id.editMa);
        final EditText editemail = findViewById(R.id.editEmail);

        // Sự kiện bấm nút Huỷ
        Button btnhuy = findViewById(R.id.btnHuy);
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chuyenlayoutMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(chuyenlayoutMain);
            }
        });

        // Sự kiện bấm nút Tiếp tục
        Button btntieptuc = findViewById(R.id.btnTiep);
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Bỏ note 3 dòng dưới
//                url = MethodChung.CreateURL() + "nguoidung/findById/" + editma.getText().toString()
//                        + "/" + editemail.getText().toString();
//                new HttpAsyncTask().execute(url);

                // Note dòng dưới này
                new HttpAsyncTask().execute();
            }
        });


//        //Lấy item vào spinner "cách lấy lại mật khẩu" từ app\res\values\strings.xml
//        Spinner spinFor = findViewById(R.id.spinForget);
//        ArrayAdapter<CharSequence> spinArrayFor = ArrayAdapter.createFromResource(this, R.array.spinForget, android.R.layout.simple_spinner_item);
//        spinArrayFor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinFor.setAdapter(spinArrayFor);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return MethodChung.GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            String t = "true";
            if (result.equals(t)) {
                Toast.makeText(getApplicationContext(), "Thực hiện thành công ~", Toast.LENGTH_SHORT).show();

                // Hiện progress bar
                progressDialog = new ProgressDialog(ForgetPass.this);
                progressDialog.setMessage("Đang chạy ..."); // Setting Message
                progressDialog.setTitle("Chuyển sang giao diện đăng nhập ~"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    public void run() {
                    }
                }).start();

                //Delay 2s
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent chuyenlayoutmain = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity((chuyenlayoutmain));
                    }
                }, 2000);

            } else {
                Toast.makeText(getApplicationContext(), "Thực hiện thất bại ...", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
