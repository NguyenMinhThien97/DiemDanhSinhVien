package com.zic.diemdanhapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.model.ThongBao;

import java.util.ArrayList;

public class ThongBaoAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongBao> arrayThongBao;

    public ThongBaoAdapter(Context context, ArrayList<ThongBao> arrayThongBao) {
        this.context = context;
        this.arrayThongBao = arrayThongBao;
    }

    @Override
    public int getCount() {
        return arrayThongBao.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayThongBao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTenThongBao, txtNoiDungThongBao , txtTenGiaoVien, txtNgayTao;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_thongbao, null);
            viewHolder.txtTenThongBao = convertView.findViewById(R.id.textviewTenThongBao);
            viewHolder.txtNoiDungThongBao = convertView.findViewById(R.id.textviewNoiDungThongBao);
            viewHolder.txtTenGiaoVien = convertView.findViewById(R.id.textviewTenGiaoVien);
            viewHolder.txtNgayTao = convertView.findViewById(R.id.textviewNgayTao);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ThongBao thongBao = (ThongBao) getItem(position);
        viewHolder.txtTenThongBao.setText(thongBao.getTenThongBao());
        viewHolder.txtNoiDungThongBao.setMaxLines(2);
        viewHolder.txtNoiDungThongBao.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtNoiDungThongBao.setText(thongBao.getNoiDung());
        viewHolder.txtTenGiaoVien.setText(thongBao.getTenGiaoVien());
        viewHolder.txtNgayTao.setText(thongBao.getNgayTao());
        return convertView;
    }
}
