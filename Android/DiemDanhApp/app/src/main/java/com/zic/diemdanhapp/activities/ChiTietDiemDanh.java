package com.zic.diemdanhapp.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.text.PrecomputedTextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.adapters.MethodChung;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChiTietDiemDanh extends AppCompatActivity {

    String manhanduoc, status, urlmonhoc, urlngayhoc, urldiemdanh, mamonhoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_diem_danh);
        getSupportActionBar().hide();

        // Nhận mã và status
        Intent nhanpass = getIntent();
        manhanduoc = nhanpass.getStringExtra("ma");
        status = nhanpass.getStringExtra("status").toString();

        // Ân mọi thứ trừ logo và spinner chọn môn học
        final Spinner spinnermonhoc = findViewById(R.id.spinMonHoc);
        spinnermonhoc.setVisibility(View.VISIBLE);
        final TableLayout table = findViewById(R.id.tableLichHoc);
        table.setVisibility(View.INVISIBLE);
        final Spinner spinnerngay = findViewById(R.id.spinNgay);
        spinnerngay.setVisibility(View.INVISIBLE);
        final Button btnin = findViewById(R.id.btnInDiemDanh);
        btnin.setVisibility(View.INVISIBLE);
        final Button btncommit = findViewById(R.id.btnCommit);
        btncommit.setVisibility(View.INVISIBLE);

        final Button btngomonhoc = findViewById(R.id.btnGoTenMonHoc);
        final Button btngongay = findViewById(R.id.btnGoNgay);

        btngongay.setVisibility(View.INVISIBLE);

        if (status.equals("1")) {
            urlmonhoc = "giaovien/getTenMonHoc/" + manhanduoc;
            new HttpAsyncTaskMonHoc().execute(MethodChung.CreateURL() + urlmonhoc);
        }

        // Sự kiện bấm button Go của spinner môn học
        btngomonhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("1")) {
                    urlngayhoc = MethodChung.CreateURL() + "giaovien/getNgayHoc/" + manhanduoc + "/" + "1";
                    new HttpAsyncTaskNgay().execute(urlngayhoc);

                    btngongay.setVisibility(View.VISIBLE);
                    spinnerngay.setVisibility(View.VISIBLE);

                    btnin.setVisibility(View.INVISIBLE);
                    btncommit.setVisibility(View.INVISIBLE);
                    table.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Sự kiện bấm button Go của spinner ngày học
        btngongay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("1")) {

                    String item = spinnermonhoc.getSelectedItem().toString();
                    ((String) item).substring(((String) item).indexOf("{") + 1);
                    ((String) item).substring(0, ((String) item).indexOf("}"));

                    urldiemdanh = MethodChung.CreateURL() + "giaovien/xemChiTietDiemDanh/" + "1"
//                            + "/" + spinnerngay.getSelectedItem().toString();
                            + "/" + "2019-04-28";
                    new HttpAsyncTaskDiemDanh().execute(urldiemdanh);

                    btnin.setVisibility(View.VISIBLE);
                    btncommit.setVisibility(View.VISIBLE);
                    table.setVisibility(View.VISIBLE);
                }
            }
        });

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

            Spinner spinnermonhoc = findViewById(R.id.spinMonHoc);

            try {
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String tenmonhoc = obj.getString("tenMonHoc");
                    String mamonhoc1 = obj.getString("maMonHoc");
                    listtenmonhoc.add("{" + mamonhoc1 + "}" + tenmonhoc.toString());
                }

                //Thêm môn hoc vào spinner tên môn học
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listtenmonhoc);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnermonhoc.setAdapter(adapter);

            } catch (
                    JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    //Hàm xử lý onListener của spinner môn học
//    private class MyProcessEventMonHoc implements AdapterView.OnItemSelectedListener {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Object item = parent.getItemAtPosition(position).toString();
//            //Nếu spinner trống
//            if (((String) item).isEmpty()) {
//                Toast.makeText(ChiTietDiemDanh.this, "Lỗi ~", Toast.LENGTH_SHORT).show();
//                TableLayout table = findViewById(R.id.tableLichHoc);
//                table.setVisibility(View.INVISIBLE);
//                Spinner spinnerngay = findViewById(R.id.spinNgay);
//                spinnerngay.setVisibility(View.INVISIBLE);
//                Button btnin = findViewById(R.id.btnInDiemDanh);
//                btnin.setVisibility(View.INVISIBLE);
//                Button btncommit = findViewById(R.id.btnCommit);
//                btncommit.setVisibility(View.INVISIBLE);
//            } else {
//                Spinner spinnerngay = findViewById(R.id.spinNgay);
//                spinnerngay.setVisibility(View.VISIBLE);
//            }
//
//        }
//
//        //Nếu không chọn gì cả
//        public void onNothingSelected(AdapterView<?> arg0) {
//
//        }
//    }


    //Hàm xử lý JSON ngày
    private class HttpAsyncTaskNgay extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return MethodChung.GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            ArrayList<String> listngayhoc = new ArrayList<>();

            Spinner spinnerngayhoc = findViewById(R.id.spinNgay);

            try {
                Toast.makeText(ChiTietDiemDanh.this, "Yolo", Toast.LENGTH_SHORT).show();
                Toast.makeText(ChiTietDiemDanh.this, manhanduoc, Toast.LENGTH_SHORT).show();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    String ngay = array.get(i).toString();

                    listngayhoc.add(ngay);
                }

                //Thêm môn hoc vào spinner tên môn học
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChiTietDiemDanh.this, android.R.layout.simple_spinner_item, listngayhoc);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerngayhoc.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    //Hàm xử lý JSON danh sách điểm danh
    private class HttpAsyncTaskDiemDanh extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return MethodChung.GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            CreateDanhSachDiemDanh(result);

        }
    }

//    //Hàm xử lý onListener của spinner ngày
//    private class MyProcessEventNgay implements AdapterView.OnItemSelectedListener {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Object item = parent.getItemAtPosition(position).toString();
//            //Nếu spinner trống
//            if (((String) item).isEmpty()) {
//                Toast.makeText(ChiTietDiemDanh.this, "Lỗi ~", Toast.LENGTH_SHORT).show();
//                TableLayout table = findViewById(R.id.tableLichHoc);
//                table.setVisibility(View.INVISIBLE);
//                Spinner spinnerngay = findViewById(R.id.spinNgay);
//                spinnerngay.setVisibility(View.VISIBLE);
//                Button btnin = findViewById(R.id.btnInDiemDanh);
//                btnin.setVisibility(View.INVISIBLE);
//                Button btncommit = findViewById(R.id.btnCommit);
//                btncommit.setVisibility(View.INVISIBLE);
//            } else {
//                Spinner spinnerngay = findViewById(R.id.spinNgay);
//                spinnerngay.setVisibility(View.VISIBLE);
//                ((String) item).substring(((String) item).indexOf("{") + 1);
//                ((String) item).substring(0, ((String) item).indexOf("}"));
//                mamonhoc = item.toString();
//                Toast.makeText(ChiTietDiemDanh.this, mamonhoc, Toast.LENGTH_SHORT).show();
//                new HttpAsyncTaskNgay().execute(MethodChung.CreateURL() + manhanduoc + "/" + mamonhoc);
//            }
//
//        }
//
//        //Nếu không chọn gì cả
//        public void onNothingSelected(AdapterView<?> arg0) {
//
//        }
//    }

    private void CreateDanhSachDiemDanh(String result) {
        try {
            JSONArray array = new JSONArray(result);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String ma = obj.getString("maSinhVien");
                String ten = obj.getString("tenSinhVien");

                TextView t1 = new TextView(this);
                TextView t2 = new TextView(this);
                LinearLayout ln = new LinearLayout(this);
                CheckBox cb = new CheckBox(this);
                TableRow tr = new TableRow(this);

                CreateSinhVien(t1, ten, t2, ma, ln, cb, tr);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Hàm tạo TextView Tên
    private void CreateSinhVien(TextView t1, String ten, TextView t2, String ma, LinearLayout ln, CheckBox cb, TableRow tr) {
        t1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        t1.setPadding(10, 10, 10, 10);
        t1.setText(ten);
        t1.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        t1.setTextColor(Color.parseColor("#040750"));
        t1.setTextSize(15);

        t2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        t2.setPadding(10, 10, 10, 10);
        t2.setText(ma);
        t2.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        t2.setTextColor(Color.parseColor("#040750"));
        t2.setTextSize(15);

        ln.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 30));
        ln.addView(cb);

        tr.addView(t1);
        tr.addView(t2);
        tr.addView(ln);

    }
}
