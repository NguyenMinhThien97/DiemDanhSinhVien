package com.zic.diemdanhapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zic.diemdanhapp.R;
import com.zic.diemdanhapp.activities.ThongBaoGiaoVien;
import com.zic.diemdanhapp.model.ThongBao;

import java.util.ArrayList;

public class ThongBaoGiaoVienAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongBao> arrayThongBao;

    public ThongBaoGiaoVienAdapter(Context context, ArrayList<ThongBao> arrayThongBao) {
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
        public TextView txtTenThongBao, txtNoiDungThongBao , txtTenMonHoc, txtNgayTao;
//        public ImageView btnDelete;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_thongbao_gv,null);
            viewHolder.txtTenThongBao = convertView.findViewById(R.id.textviewTenThongBaoGV);
            viewHolder.txtNoiDungThongBao = convertView.findViewById(R.id.textviewNoiDungThongBaoGV);
            viewHolder.txtTenMonHoc = convertView.findViewById(R.id.textviewTenMonHoc);
            viewHolder.txtNgayTao = convertView.findViewById(R.id.textviewNgayTaoGV);
//            viewHolder.btnDelete = convertView.findViewById(R.id.imageButtonDeleteTB);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ThongBao thongBao = (ThongBao) getItem(position);
        viewHolder.txtTenThongBao.setText(thongBao.getTenThongBao());
        viewHolder.txtNoiDungThongBao.setMaxLines(2);
        viewHolder.txtNoiDungThongBao.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtNoiDungThongBao.setText(thongBao.getNoiDung());
        viewHolder.txtTenMonHoc.setText(thongBao.getTenMonHoc());
        viewHolder.txtNgayTao.setText(thongBao.getNgayTao());

        return convertView;
    }
}
