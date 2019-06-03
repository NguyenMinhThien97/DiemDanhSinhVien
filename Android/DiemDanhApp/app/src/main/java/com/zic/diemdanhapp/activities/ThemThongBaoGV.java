package com.zic.diemdanhapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;
import com.zic.diemdanhapp.adapters.ThongBaoGiaoVienAdapter;
import com.zic.diemdanhapp.model.ThongBao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThemThongBaoGV extends AppCompatActivity {
    EditText txtTieuDe, txtNoiDung;
    Toolbar toolbar;
    Button btnThem;
    Spinner spinnerMonHoc;
    String noiDung, maMonHoc, tieuDe, maGiaoVien, urlmonhoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thong_bao_gv);
        Anhxa();
        ActionToolbar();

        Intent intentma = getIntent();
        maGiaoVien = intentma.getStringExtra("ma");


        urlmonhoc = MethodChung.CreateURL() + "giaovien/getTenMonHoc/" + maGiaoVien;
        new HttpAsyncTaskMonHoc().execute(urlmonhoc);

        String tenGiaoVien = "";
        String tenThongBao = "";
        String ngayTao = "";
        final ThongBao thongBao = (ThongBao) getIntent().getSerializableExtra("thongbaoGV");
        if (thongBao != null) {
            tenThongBao = thongBao.getTenThongBao();
            noiDung = thongBao.getNoiDung();
            txtTieuDe.setText(tenThongBao);
            txtNoiDung.setText(noiDung);
            ArrayList<String> listtenmonhoc = new ArrayList<>();
            listtenmonhoc.add(thongBao.getTenMonHoc());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listtenmonhoc);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMonHoc.setAdapter(adapter);
            spinnerMonHoc.setEnabled(false);
            Button btnSua = findViewById(R.id.btnThemThongBao);
            btnSua.setText("Sửa Thông Báo");
            Toolbar toolbar = findViewById(R.id.toolbarThongBaoGV);
            toolbar.setTitle("SỬA THÔNG BÁO");
        }


            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tieuDe = txtTieuDe.getText().toString();
                    noiDung = txtNoiDung.getText().toString();
                    if (tieuDe != null && noiDung != null) {
                        if (thongBao != null) {
                            UpdateData(String.valueOf(thongBao.getMaThongBao()), tieuDe, noiDung);
                        } else {
                            maMonHoc = spinnerMonHoc.getSelectedItem().toString();
                            maMonHoc = ((String) maMonHoc).substring(0, ((String) maMonHoc).indexOf("."));
                            SetData(maGiaoVien, maMonHoc.trim(), tieuDe, noiDung);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Nhập thiếu thông tin", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void SetData(final String maGiaoVien, final String maMonHoc, final String tieuDe, final String noiDung) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String UrlThongBaoSinhVien = MethodChung.CreateURL() + "thongbao/create";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlThongBaoSinhVien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("true")) {
                    Intent intentb1 = new Intent(ThemThongBaoGV.this, ThongBaoGiaoVien.class);
                    intentb1.putExtra("ma", maGiaoVien);
                    startActivity(intentb1);
                    Toast.makeText(getApplicationContext(), "Đã thêm thông báo", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Không thể thêm thông báo\nVui lòng thử lại!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("maGiaoVien", maGiaoVien);
                param.put("maMonHoc", maMonHoc);
                param.put("tenThongBao", tieuDe);
                param.put("noiDung", noiDung);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void UpdateData(final String maThongBao, final String tieuDe, final String noiDung) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String UrlThongBaoSinhVien = MethodChung.CreateURL() + "thongbao/update";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlThongBaoSinhVien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("true")) {

                    Toast.makeText(getApplicationContext(), "Đã Sửa thông báo", Toast.LENGTH_LONG).show();
                    Intent intentb2 = new Intent(ThemThongBaoGV.this, ThongBaoGiaoVien.class);
                    intentb2.putExtra("ma", maGiaoVien);
                    startActivity(intentb2);
                } else {
                    Toast.makeText(getApplicationContext(), "Không thể thêm thông báo\nVui lòng thử lại!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("maThongBao", maThongBao);
                param.put("tenThongBao", tieuDe);
                param.put("noiDung", noiDung);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarThongBaoGV);
        txtTieuDe = findViewById(R.id.txtTieuDe);
        txtNoiDung = findViewById(R.id.txtNoiDung);
        btnThem = findViewById(R.id.btnThemThongBao);
        spinnerMonHoc = findViewById(R.id.spinTenMonHoc);
    }

    //Hàm xử lý JSON môn học
    private class HttpAsyncTaskMonHoc extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return MethodChung.GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            ArrayList<String> listtenmonhoc = new ArrayList<>();
            Spinner spinnerMonHoc = findViewById(R.id.spinTenMonHoc);
            try {
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String tenmonhoc = obj.getString("tenMonHoc");
                    String mamonhoc1 = obj.getString("maMonHoc");
                    listtenmonhoc.add(mamonhoc1 + ". " + tenmonhoc);
                }

                //Thêm môn hoc vào spinner tên môn học
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listtenmonhoc);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMonHoc.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
