package com.example.quan_ly_chi_tieu.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_ly_chi_tieu.R;
import com.example.quan_ly_chi_tieu.classes.BanGhiThu;
import com.example.quan_ly_chi_tieu.classes.Ngay_Thang;
import com.example.quan_ly_chi_tieu.data.SQLDatabase;

import java.util.ArrayList;
import java.util.List;

public class Entrance extends AppCompatActivity {
    ImageButton imgbtn_Income,imgbtn_Expense,imgbtn_reciept;
    Spinner month_spinner;
    EditText editTextMonth,editTextDate,editTextYear;
    TextView txtViewTotalMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrance_activity);
        ConnectView();
        SetEvents();
        float res=LoadMoneyFromThu(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2])-LoadMoneyFromChi(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]);
        if(res<0)
        {
            txtViewTotalMoney.setText("Số dư: "+res+" VND");
        }
        else
        {
            txtViewTotalMoney.setText("Số dư: +"+res+" VND");
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        float res=LoadMoneyFromThu(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2])-LoadMoneyFromChi(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]);
        if(res<0)
        {
            txtViewTotalMoney.setText("Số dư: "+res+" VND");
        }
        else
        {
            txtViewTotalMoney.setText("Số dư: +"+res+" VND");
        }
    }

    public void ConnectView()
    {
        txtViewTotalMoney=(TextView) findViewById(R.id.idTextViewTotalMoney);

        imgbtn_Income=(ImageButton) findViewById(R.id.imgbtn_Income);
        imgbtn_Expense=(ImageButton) findViewById(R.id.imgbtn_Expense);
        imgbtn_reciept=(ImageButton) findViewById(R.id.imgbtn_newReceipt);

        editTextMonth=(EditText) findViewById(R.id.idEditTxt_month);
        editTextYear=(EditText) findViewById(R.id.idEditTxt_year);
        editTextDate=(EditText) findViewById(R.id.idEditTxt_day);

    }
    public void SetEvents()
    {
        imgbtn_Income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entrance.this, Income.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        imgbtn_Expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Entrance.this, Expense.class);

                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });
        imgbtn_reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entrance.this, Reciept.class);
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
                float res=LoadMoneyFromThu(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2])-LoadMoneyFromChi(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]);
                if(res<0)
                {
                    txtViewTotalMoney.setText("Số dư: "+res+" VND");
                }
                else
                {
                    txtViewTotalMoney.setText("Số dư: +"+res+" VND");
                }
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
                float res=LoadMoneyFromThu(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2])-LoadMoneyFromChi(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]);
                if(res<0)
                {
                    txtViewTotalMoney.setText("Số dư: "+res+" VND");
                }
                else
                {
                    txtViewTotalMoney.setText("Số dư: +"+res+" VND");
                }
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
                float res=LoadMoneyFromThu(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2])-LoadMoneyFromChi(getNgayThangNam()[0],getNgayThangNam()[1],getNgayThangNam()[2]);
                if(res<0)
                {
                    txtViewTotalMoney.setText("Số dư: "+res+" VND");
                }
                else
                {
                    txtViewTotalMoney.setText("Số dư: +"+res+" VND");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
    public float LoadMoneyFromChi(@Nullable String ngay, @Nullable String thang, @Nullable String nam)
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
        Cursor cursorMoney = db.Query("SELECT SUM(Money) FROM BanGhiChi WHERE Ngay like ?",new String[]{ngayThangNam});
        float res=0;
        if(cursorMoney!=null&& cursorMoney.moveToFirst())
        {
            res=cursorMoney.getFloat(0);
        }
        return res;
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





}
