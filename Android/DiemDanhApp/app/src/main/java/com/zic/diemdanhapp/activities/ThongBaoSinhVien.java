package com.zic.diemdanhapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.zic.diemdanhapp.model.ThongBao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThongBaoSinhVien extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ThongBaoAdapter thongBaoAdapter;
    ArrayList<ThongBao> listThongBao;
    String maSinhVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao_sinh_vien);
        Anhxa();
        ActionToolbar();
        Intent intent = getIntent();
        maSinhVien = intent.getStringExtra("ma");
        GetData(maSinhVien);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentListView = new Intent(getApplicationContext() ,ChiTietThongBaoSinhVien.class);
                intentListView.putExtra("thongbaoSV", listThongBao.get(position));
                startActivity(intentListView);
            }
        });

}

    private void GetData(String maSinhVien) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String UrlThongBaoSinhVien = MethodChung.CreateURL()+ "thongbao/getThongBaoSinhVien/"+maSinhVien;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlThongBaoSinhVien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                long maThongBao = 0;
                String tenGiaoVien = "";
                String tenMonHoc = "";
                String tenThongBao = "";
                String noiDung = "";
                String ngayTao = "";
                if (response != null && response.length() != 2){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i< jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            maThongBao = jsonObject.getInt("maThongBao");
                            tenGiaoVien = jsonObject.getString("tenGiaoVien");
                            tenMonHoc = jsonObject.getString("tenMonHoc");
                            tenThongBao = jsonObject.getString("tenThongBao");
                            noiDung = jsonObject.getString("noiDung");
                            ngayTao = jsonObject.getString("ngayTao");
                            ThongBao t = new ThongBao(maThongBao,tenGiaoVien,tenMonHoc,tenThongBao,noiDung,ngayTao);
                            listThongBao.add(t);
                            thongBaoAdapter.notifyDataSetChanged();
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
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listviewThongBaoSV);
        listThongBao = new ArrayList<>();
        thongBaoAdapter = new ThongBaoAdapter(getApplicationContext(),listThongBao);
        listView.setAdapter(thongBaoAdapter);
    }
}
