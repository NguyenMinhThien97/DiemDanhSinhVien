package com.zic.diemdanhapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;
import com.zic.diemdanhapp.adapters.ThongBaoAdapter;
import com.zic.diemdanhapp.adapters.ThongBaoGiaoVienAdapter;
import com.zic.diemdanhapp.model.ThongBao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThongBaoGiaoVien extends AppCompatActivity {

    ProgressDialog progressDialog;
    Toolbar toolbar;
    ListView listView;
    ThongBaoGiaoVienAdapter thongBaoGiaoVienAdapter;
    ArrayList<ThongBao> listThongBao;
    String maGiaoVien;
    boolean ketqua = false;
//    ImageView btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao_giao_vien);

        Intent intent = getIntent();
        maGiaoVien = intent.getStringExtra("ma");

        FloatingActionButton btnthem = findViewById(R.id.btnThemThongBao);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chuyenlayoutThemTB = new Intent(ThongBaoGiaoVien.this, ThemThongBaoGV.class);
                chuyenlayoutThemTB.putExtra("ma", maGiaoVien);
                startActivity((chuyenlayoutThemTB));
            }
        });

        Anhxa();
        ActionToolbar();
        GetData(maGiaoVien);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentListView = new Intent(ThongBaoGiaoVien.this, ChiTietThongBaoGiaoVien.class);
                intentListView.putExtra("thongbaoGV", listThongBao.get(position));
                intentListView.putExtra("ma", maGiaoVien);
                startActivity(intentListView);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String url = MethodChung.CreateURL() + "thongbao/deleteByID/" + listThongBao.get(position).getMaThongBao();
                new HttpAsyncTask().execute(url);
                listThongBao.remove(position);
                thongBaoGiaoVienAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    //Lấy dữ liệu
    private void GetData(String maGiaoVien) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String UrlThongBaoSinhVien = MethodChung.CreateURL() + "thongbao/getThongBaoGiaoVien/" + maGiaoVien;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlThongBaoSinhVien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                long maThongBao = 0;
                String tenGiaoVien = "";
                String tenMonHoc = "";
                String tenThongBao = "";
                String noiDung = "";
                String ngayTao = "";
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            maThongBao = jsonObject.getInt("maThongBao");
                            tenGiaoVien = jsonObject.getString("tenGiaoVien");
                            tenMonHoc = jsonObject.getString("tenMonHoc");
                            tenThongBao = jsonObject.getString("tenThongBao");
                            noiDung = jsonObject.getString("noiDung");
                            ngayTao = jsonObject.getString("ngayTao");
                            ThongBao t = new ThongBao(maThongBao, tenGiaoVien, tenMonHoc, tenThongBao, noiDung, ngayTao);
                            listThongBao.add(t);
                            thongBaoGiaoVienAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
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

    private void Anhxa() {
//        btnDelete = findViewById(R.id.imageButtonDeleteTB);
//        btnEdit = findViewById(R.id.imageButtonEditTB);
        toolbar = findViewById(R.id.toolbarThongBaoGV);
        listView = findViewById(R.id.listviewThongBaoGV);
        listThongBao = new ArrayList<>();
        thongBaoGiaoVienAdapter = new ThongBaoGiaoVienAdapter(getApplicationContext(), listThongBao);
        listView.setAdapter(thongBaoGiaoVienAdapter);
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
                ketqua = true;
                Toast.makeText(getApplicationContext(), "Đã xóa thông báo", Toast.LENGTH_SHORT).show();
            } else {
                ketqua = false;
                Toast.makeText(getApplicationContext(), "Xóa không thành công \n\tThử lại!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
