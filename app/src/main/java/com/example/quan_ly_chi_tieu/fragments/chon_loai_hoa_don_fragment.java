package com.example.quan_ly_chi_tieu.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quan_ly_chi_tieu.R;
import com.example.quan_ly_chi_tieu.adapters.CustomListAdapter;
import com.example.quan_ly_chi_tieu.classes.HoaDonChi;
import com.example.quan_ly_chi_tieu.classes.HoaDonThu;

import java.util.ArrayList;
import java.util.List;


public class chon_loai_hoa_don_fragment extends Fragment {
    private Context context;
    private String layout_name, tabName;
    public ListView listView;
    public CustomListAdapter adapter;



    public chon_loai_hoa_don_fragment(Context context, String layout_name, String tabName) {
        this.context = context;
        this.layout_name = layout_name;
        this.tabName = tabName;
    }

    public String getTabName() {
        return tabName;
    }

    public int getLayoutRes(String layoutFile_name) {
        int res;
        res = context.getResources().getIdentifier(layoutFile_name, "layout", context.getPackageName());
        return res;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if ("Khoan thu".equals(tabName)) { // Correct way to compare strings
            View rootView = inflater.inflate(R.layout.khoan_thu_fragment_layout, container, false);
            this.listView =(ListView) rootView.findViewById(R.id.idListHoaDonThu);
            try {
                adapter=new CustomListAdapter(R.layout.mot_dong_hoadon,context,(List<HoaDonThu>) new ArrayList<HoaDonThu>(),HoaDonThu.class);
            }
            catch (Exception e)
            {
                Log.e("E",e.getMessage());
            }



            if(rListener!=null)
            {
                rListener.onFragmentReady(this);
            }
            return rootView;
        } else {
            View rootView = inflater.inflate(R.layout.khoan_chi_fragment_layout, container, false);
            listView =(ListView) rootView.findViewById(R.id.idListHoaDonChi);
            try {
                adapter=new CustomListAdapter(R.layout.mot_dong_hoadon,context,(List<HoaDonChi>) new ArrayList<HoaDonChi>(),HoaDonChi.class);
            }
            catch (Exception e)
            {
                Log.e("E",e.getMessage());
            }

            if(rListener!=null)
            {
                rListener.onFragmentReady(this);
            }
            return rootView;
        }




    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        if ("Khoan thu".equals(tabName)) {
            listView = view.findViewById(R.id.idListHoaDonThu);
            if(cListener!=null)
            {
                cListener.onFragmentCreated(this);
            }



        } else {
            listView = view.findViewById(R.id.idListHoaDonChi);
            if(cListener!=null)
            {
                cListener.onFragmentCreated(this);
            }

        }

    }
    public interface OnFragmentReadyListener {
        void onFragmentReady(chon_loai_hoa_don_fragment fragment);
    }
    private OnFragmentReadyListener rListener;
    public void setOnFragmentReadyListener(OnFragmentReadyListener listener) {
        rListener = listener;
    }
    public interface OnFragmentCreatedListener {
        void onFragmentCreated(chon_loai_hoa_don_fragment fragment);
    }
    private OnFragmentCreatedListener cListener;



    public void setOnFragmentCreatedListener(OnFragmentCreatedListener listener) {
        cListener = listener;
    }
}



