package com.example.quan_ly_chi_tieu.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_ly_chi_tieu.R;
import com.example.quan_ly_chi_tieu.adapters.CustomListBanGhiChi;
import com.example.quan_ly_chi_tieu.adapters.CustomListBanGhiThu;
import com.example.quan_ly_chi_tieu.classes.BanGhiChi;
import com.example.quan_ly_chi_tieu.classes.BanGhiThu;
import com.example.quan_ly_chi_tieu.classes.Ngay_Thang;
import com.example.quan_ly_chi_tieu.data.SQLDatabase;

import java.util.ArrayList;
import java.util.List;

public class Income extends AppCompatActivity {
    ImageButton imgbtn_Exit,imgbtn_reciept;
    EditText editTextMonth,editTextDate,editTextYear;
    ListView listView;
    CustomListBanGhiThu adapter;
    TextView txtViewTotalMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_activity);
        ConnectView();
        SetEvents();
        LoadDataToListView(null,null,null);


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.addDataForThu(  LoadDataFromDB(null,null,null));
    }

    public void ConnectView()
    {
        txtViewTotalMoney=(TextView) findViewById(R.id.idTextViewTotalMoney);

        imgbtn_Exit=(ImageButton) findViewById(R.id.imgbtn_Exit);
        imgbtn_reciept=(ImageButton) findViewById(R.id.imgbtn_newReceipt);

        editTextMonth=(EditText) findViewById(R.id.idEditTxt_month);
        editTextYear=(EditText) findViewById(R.id.idEditTxt_year);
        editTextDate=(EditText) findViewById(R.id.idEditTxt_day);

        listView=(ListView) findViewById(R.id.idListViewDoanhThu);
    }
    public void SetEvents()
    {
        imgbtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
        imgbtn_reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Income.this, Reciept.class);
                intent.putExtra("previous_activity","com.example.quan_ly_chi_tieu.activities.Income");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
            }
        });
        editTextMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.addDataForThu(LoadDataFromDB(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                {
                    if(s.toString().length()==3 || Integer.parseInt(s.toString()) >12 || Integer.parseInt(s.toString()) <1)
                    {
                        editTextMonth.setText("");
                    }


                }


            }
        });
        editTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.addDataForThu(LoadDataFromDB(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                {
                    if(editTextMonth.getText().toString().equals(""))
                    {
                        editTextDate.setText("");
                    }
                    else
                    {
                        int month=Integer.parseInt(editTextMonth.getText().toString());
                        Ngay_Thang ngayThang= new Ngay_Thang(month);
                        int maxDay=ngayThang.getNgay();
                        int typedDay= Integer.parseInt(s.toString());
                        if(typedDay>maxDay)
                        {
                            editTextDate.setText("");
                        }



                    }


                }



            }
        });
        editTextYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.addDataForThu(LoadDataFromDB(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void LoadDataToListView(@Nullable String ngay,@Nullable String thang,@Nullable String nam)
    {
        adapter=new CustomListBanGhiThu(R.layout.mot_dong_listview,this,LoadDataFromDB(ngay,thang,nam));

        listView.setAdapter(adapter);
        adapter.SetDataSetChangedListener(new CustomListBanGhiThu.OnDataSetChangedListener() {
            @Override
            public void onDataSetChanged() {
                txtViewTotalMoney.setText("Thu nhập: +"+LoadMoneyFromThu(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2])+" VND");
            }
        });

    }
    public List<BanGhiThu> LoadDataFromDB(@Nullable String ngay, @Nullable String thang, @Nullable String nam)
    {
        List<BanGhiThu> list= new ArrayList<>();
        String ngayTemp="";
        String thangTemp="";
        String namTemp="";

        if(ngay==null || ngay.isEmpty()|| ngay=="")
        {
            ngayTemp="__";
        }
        else
        {
            ngayTemp=ngay;
        }
        if(thang==null || thang.isEmpty() || thang=="")
        {
            thangTemp="__";
        }
        else
        {
            thangTemp=thang;
        }
        if(nam==null || nam.isEmpty()  || nam=="")
        {
            namTemp="____";
        }
        else
        {
            namTemp=nam;
        }
        String ngayThangNam=ngayTemp+thangTemp+namTemp+"%";

        String queryForListView=
                "SELECT * FROM BanGhiThu" +
                        " WHERE Ngay like ? " +
                        "ORDER BY SUBSTR(Ngay, 5, 4) ASC,SUBSTR(Ngay, 3, 2) ASC,SUBSTR(Ngay, 1, 2) ASC, STT ASC";
        SQLDatabase db = SQLDatabase.getInstance(this);
        db.onCreate(db.getReadableDatabase());
        Cursor cursor = db.Query(queryForListView,new String[]{ngayThangNam});
        Cursor cursorMoney = db.Query("SELECT SUM(Money) FROM BanGhiThu WHERE Ngay like ?",new String[]{ngayThangNam});
        if(cursorMoney!=null && cursorMoney.moveToLast())
        {

            txtViewTotalMoney.setText("Thu nhập: +"+cursorMoney.getFloat(0)+" VND");

        }
        while(cursor.moveToNext())
        {
            int Stt = cursor.getInt(cursor.getColumnIndex("STT"));
            String Ngay = cursor.getString(cursor.getColumnIndex("Ngay"));
            int ID = cursor.getInt(cursor.getColumnIndex("ID"));
            float Money = cursor.getFloat(cursor.getColumnIndex("Money"));
            String Note = cursor.getString(cursor.getColumnIndex("Note"));
            BanGhiThu banGhiThu= new BanGhiThu(Stt,Ngay,ID,Money,Note);


            list.add(banGhiThu);
        }
        return list;
    }
    public String[] getNgayThangNam()
    {

        String ngay="",thang="",nam="";
        if(!editTextDate.getText().toString().isEmpty() && editTextDate.getText().toString().length()!=0)
        {
            if(editTextDate.getText().toString().length()==1 )
            {
                ngay="0"+editTextDate.getText().toString();
            }
            else
            {
                ngay=editTextDate.getText().toString();
            }
        }
        if(!editTextMonth.getText().toString().isEmpty() && editTextMonth.getText().toString().length()!=0)
        {
            if(editTextMonth.getText().toString().length()==1 )
            {
                thang="0"+editTextMonth.getText().toString();
            }
            else
            {
                thang=editTextMonth.getText().toString();
            }
        }
        if(!editTextYear.getText().toString().isEmpty() && editTextYear.getText().toString().length()!=0)
        {
            if(editTextYear.getText().toString().length()==1 )
            {
                nam="0"+editTextYear.getText().toString();
            }
            else
            {
                nam=editTextYear.getText().toString();
            }
        }
        String[] ngayThangNam = new String[]{ngay,thang,nam};


        return ngayThangNam;
    }
    public float LoadMoneyFromThu(@Nullable String ngay, @Nullable String thang, @Nullable String nam)
    {

        String ngayTemp="";
        String thangTemp="";
        String namTemp="";

        if(ngay==null || ngay.isEmpty()|| ngay=="")
        {
            ngayTemp="__";
        }
        else
        {
            ngayTemp=ngay;
        }
        if(thang==null || thang.isEmpty() || thang=="")
        {
            thangTemp="__";
        }
        else
        {
            thangTemp=thang;
        }
        if(nam==null || nam.isEmpty()  || nam=="")
        {
            namTemp="____";
        }
        else
        {
            namTemp=nam;
        }
        String ngayThangNam=ngayTemp+thangTemp+namTemp+"%";


        SQLDatabase db = SQLDatabase.getInstance(this);
        db.onCreate(db.getReadableDatabase());
        Cursor cursorMoney = db.Query("SELECT SUM(Money) FROM BanGhiThu WHERE Ngay like ?",new String[]{ngayThangNam});
        float res=0;
        if(cursorMoney!=null&& cursorMoney.moveToFirst())
        {
            res=cursorMoney.getFloat(0);
        }
        return res;
    }

}
