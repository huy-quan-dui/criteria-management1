package com.example.quan_ly_chi_tieu.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quan_ly_chi_tieu.R;
import com.example.quan_ly_chi_tieu.classes.HoaDonChi;
import com.example.quan_ly_chi_tieu.classes.HoaDonThu;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    public List<HoaDonThu> listDatathu;
    public List<HoaDonChi> listDataChi;
    private int layout;
    private Context context;

    public CustomListAdapter(int layout, Context context,List<?> listData,Class<?> datatype ) {
        this.layout = layout;
        this.context = context;
        if(datatype== HoaDonThu.class)
        {
            this.listDatathu=(List<HoaDonThu>) listData;
        }
        if(datatype== HoaDonChi.class)
        {
            this.listDataChi=(List<HoaDonChi>) listData;
        }
    }


    public void clearDataForThu() {
        if (listDatathu != null) {
            listDatathu.clear();
            notifyDataSetChanged();
        }
    }

    // Method to clear data for HoaDonChi
    public void clearDataForChi() {
        if (listDataChi != null) {
            listDataChi.clear();
            notifyDataSetChanged();
        }
    }
    public void addDataForThu(List<HoaDonThu> newData) {
        this.clearDataForThu();
        listDatathu.addAll(newData);
        notifyDataSetChanged();
    }

    // Method to add data for HoaDonChi
    public void addDataForChi(List<HoaDonChi> newData) {
       this.clearDataForChi();
        listDataChi.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(listDatathu!=null)
        {
            return listDatathu.size();
        }
        else
            return listDataChi.size();

    }

    @Override
    public Object getItem(int position) {
        if(listDatathu!=null)
        {
            return listDatathu.get(position);
        }
        else
            return listDataChi.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(layout,null);

        if(listDatathu!= null)
        {

            TextView IDHoaDon =(TextView) convertView.findViewById(R.id.idIDHoaDon);
            IDHoaDon.setText(listDatathu.get(position).getID()+"");

            TextView TenHoaDon =(TextView) convertView.findViewById(R.id.idTenHoaDon);
            TenHoaDon.setText(listDatathu.get(position).getTenHoaDon()+"");
            return convertView;
        }
        else
        {

            TextView IDHoaDon =(TextView) convertView.findViewById(R.id.idIDHoaDon);
            IDHoaDon.setText(listDataChi.get(position).getID()+"");

            TextView TenHoaDon =(TextView) convertView.findViewById(R.id.idTenHoaDon);
            TenHoaDon.setText(listDataChi.get(position).getTenHoaDon()+"");
            return convertView;
        }
    }


}
