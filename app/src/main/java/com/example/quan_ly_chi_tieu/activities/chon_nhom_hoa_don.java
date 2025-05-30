package com.example.quan_ly_chi_tieu.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.quan_ly_chi_tieu.Intents.IntentAdapter;
import com.example.quan_ly_chi_tieu.R;
import com.example.quan_ly_chi_tieu.adapters.CustomListAdapter;
import com.example.quan_ly_chi_tieu.adapters.ViewPagerAdapter;
import com.example.quan_ly_chi_tieu.classes.HoaDonChi;
import com.example.quan_ly_chi_tieu.classes.HoaDonThu;
import com.example.quan_ly_chi_tieu.data.SQLDatabase;
import com.example.quan_ly_chi_tieu.fragments.chon_loai_hoa_don_fragment;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class chon_nhom_hoa_don extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SQLDatabase QuanLyChiTieu;
    String[] cacKhoanThu = new String[]{"Lương","Thu nhập khác","Tiền chuyển đến"};
    String[] cacKhoanChi = new String[]{"Ăn uống","Thuê nhà","Hóa đơn tiền điện","Hóa đơn wifi","Mua sắm","Giáo dục","Di chuyển","Sức khỏe","Giải trí"};
    Intent intentsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_nhom_hoadon_activity);
        ConnectView();
        LoadDatabase();
        setAdapterForPages();
        loadDataToPages();
        setFragmentItemsListener();

    }



    public void ConnectView()
    {
        tabLayout=(TabLayout) findViewById(R.id.tablayout_chonNhom);
        viewPager=(ViewPager) findViewById(R.id.ViewPager_chonNhom);
    }
    public void LoadDatabase()
    {
        try {
            QuanLyChiTieu=SQLDatabase.getInstance(this);
            QuanLyChiTieu.onCreate(QuanLyChiTieu.getWritableDatabase());
            for(int i=0;i< cacKhoanThu.length;i++)
            {
                QuanLyChiTieu.insertLoaiThu(i,cacKhoanThu[i]);
            }
            for(int i=0;i< cacKhoanChi.length;i++)
            {
                QuanLyChiTieu.insertLoaiChi(i,cacKhoanChi[i]);
            }
        }catch (Exception e)
        {
            Log.w("LoadDatabase section",e.getMessage());
        }




    }

    public void setAdapterForPages()
    {
        List<Fragment> listFragment = new ArrayList<>();
        chon_loai_hoa_don_fragment khoanThuFragment = new chon_loai_hoa_don_fragment(chon_nhom_hoa_don.this,"khoan_thu_fragment_layout","Khoan thu");
        chon_loai_hoa_don_fragment khoanChiFragment = new chon_loai_hoa_don_fragment(chon_nhom_hoa_don.this,"khoan_chi_fragment_layout","Khoan chi");
        listFragment.add(khoanChiFragment);
        listFragment.add(khoanThuFragment);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),ViewPagerAdapter.POSITION_UNCHANGED,listFragment);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);




    }
    public boolean loadDataToPages()
    {
        try {
            chon_loai_hoa_don_fragment fragmentThu=(chon_loai_hoa_don_fragment) viewPagerAdapter.getItem(1);
            fragmentThu.setOnFragmentReadyListener(new chon_loai_hoa_don_fragment.OnFragmentReadyListener() {
                @Override
                public void onFragmentReady(chon_loai_hoa_don_fragment fragment) {
                    List<HoaDonThu> listHDThu=new ArrayList<HoaDonThu>();
                    Cursor reader = QuanLyChiTieu.Query("SELECT * FROM LoaiThu",new String[]{});
                    while (reader.moveToNext())
                    {
                        HoaDonThu hd=new HoaDonThu(reader.getInt(0),reader.getString(1));
                        listHDThu.add(hd);
                    }

                    fragment.adapter.addDataForThu(listHDThu);
                    fragment.listView.setAdapter(fragment.adapter);


                }
            });

            chon_loai_hoa_don_fragment fragmentChi=(chon_loai_hoa_don_fragment) viewPagerAdapter.getItem(0);
            fragmentChi.setOnFragmentReadyListener(new chon_loai_hoa_don_fragment.OnFragmentReadyListener() {
                @Override
                public void onFragmentReady(chon_loai_hoa_don_fragment fragment) {
                    List<HoaDonChi> listHDChi=new ArrayList<HoaDonChi>();
                    Cursor reader = QuanLyChiTieu.Query("SELECT * FROM LoaiChi",new String[]{});
                    while (reader.moveToNext())
                    {
                        HoaDonChi hd=new HoaDonChi(reader.getInt(0),reader.getString(1));
                        listHDChi.add(hd);

                    }

                    fragment.adapter.addDataForChi(listHDChi);
                    fragment.listView.setAdapter(fragment.adapter);

                }
            });
            return true;
        }
        catch (Exception e)
        {
            Log.w("W",e.getMessage());
            return false;
        }

    }


    public void setFragmentItemsListener()
    {
        try {
            chon_loai_hoa_don_fragment fragmentThu=(chon_loai_hoa_don_fragment) viewPagerAdapter.getItem(1);
            fragmentThu.setOnFragmentCreatedListener(new chon_loai_hoa_don_fragment.OnFragmentCreatedListener() {
                @Override
                public void onFragmentCreated(chon_loai_hoa_don_fragment fragment) {
                    fragment.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                Object obj = fragment.listView.getItemAtPosition(position);
                                HoaDonThu hd= (HoaDonThu) obj;

                                intentsend= new Intent();
                                Bundle bundle= new Bundle();
                                bundle.putSerializable("LoaiThu",hd);
                                intentsend.putExtra("data",bundle);
                                setResult(RESULT_OK,intentsend);
                                finish();
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                            }
                            catch (Exception e)
                            {
                                Log.e("fragment items clicked",e.getMessage());
                            }

                        }
                    });
                }
            });

            chon_loai_hoa_don_fragment fragmentChi=(chon_loai_hoa_don_fragment) viewPagerAdapter.getItem(0);
            fragmentChi.setOnFragmentCreatedListener(new chon_loai_hoa_don_fragment.OnFragmentCreatedListener() {
                @Override
                public void onFragmentCreated(chon_loai_hoa_don_fragment fragment) {
                    fragment.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                Object obj = fragment.listView.getItemAtPosition(position);
                                HoaDonChi hd= (HoaDonChi) obj;

                                intentsend= new Intent();
                                Bundle bundle= new Bundle();
                                bundle.putSerializable("LoaiChi",hd);
                                intentsend.putExtra("data",bundle);
                                setResult(RESULT_OK,intentsend);
                                finish();
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                            }
                            catch (Exception e)
                            {
                                Log.e("fragment items clicked",e.getMessage());
                            }
                        }
                    });
                }
            });

        }
        catch (Exception e)
        {
            Log.w("W",e.getMessage());

        }
    }


}
