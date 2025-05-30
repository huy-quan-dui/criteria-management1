package com.example.quan_ly_chi_tieu.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_ly_chi_tieu.Intents.IntentAdapter;
import com.example.quan_ly_chi_tieu.R;
import com.example.quan_ly_chi_tieu.classes.HoaDonChi;
import com.example.quan_ly_chi_tieu.classes.HoaDonThu;
import com.example.quan_ly_chi_tieu.classes.Ngay_Thang;
import com.example.quan_ly_chi_tieu.classes.convert_String_toClass;
import com.example.quan_ly_chi_tieu.data.SQLDatabase;

public class Reciept extends AppCompatActivity {
    ImageButton imgbtn_exit,imgbtn_chonNhom,imagebtn_Yes;
    EditText editTextNote,editTxtTien,editTextMonth,editTextDate,editTextYear;
    TextView txtViewNhomHoaDon;
    Intent intentSend,intentRecieve;
    Bundle previousState;
    HoaDonThu hdThu;
    HoaDonChi hdChi;
    SQLDatabase db;

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reciept_activity);
        ConnectView();

        SetEvents();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE)
        {
            try {
                if (data != null) {
                    Bundle bundle=data.getBundleExtra("data");
                    if(bundle.getSerializable("LoaiThu")!=null)
                    {
                        hdThu =(HoaDonThu) bundle.getSerializable("LoaiThu");
                        txtViewNhomHoaDon.setText(hdThu.getTenHoaDon());
                    }
                    if(bundle.getSerializable("LoaiChi")!=null)
                    {
                        hdChi =(HoaDonChi) bundle.getSerializable("LoaiChi");
                        txtViewNhomHoaDon.setText(hdChi.getTenHoaDon());
                    }
                }
            }
            catch (Exception e)
            {
                Log.e("OnResultAc",e.getMessage());
            }

        }
        else if (resultCode==RESULT_CANCELED)
        {

        }
    }

    public void ConnectView()
    {

        imgbtn_exit=(ImageButton) findViewById(R.id.imgbtn_Exit);
        imgbtn_chonNhom=(ImageButton) findViewById(R.id.imgbtn_chonNhom);
        imagebtn_Yes=(ImageButton) findViewById(R.id.imgbtn_Yes);
        txtViewNhomHoaDon=(TextView) findViewById(R.id.txtview_chonNhom);
        editTxtTien=(EditText) findViewById(R.id.edittxt_tien);
        editTextNote=(EditText) findViewById(R.id.idEditTxt_Note);

        editTextMonth=(EditText) findViewById(R.id.idEditTxt_month);
        editTextYear=(EditText) findViewById(R.id.idEditTxt_year);
        editTextDate=(EditText) findViewById(R.id.idEditTxt_day);

    }

    public void SetEvents()
    {
        imgbtn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
            }
        });
        imgbtn_chonNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSend = IntentAdapter.createIntentWithoutData(Reciept.this,chon_nhom_hoa_don.class);
                startActivityForResult(intentSend,REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        imagebtn_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NgayString="";
                String ngay="";
                String thang="";
                String nam=editTextYear.getText().toString().trim();
                if(editTextDate.getText().toString().trim().length()<2)
                {
                    ngay="0"+editTextDate.getText().toString().trim();
                }
                else
                {
                    ngay=editTextDate.getText().toString().trim();
                }

                if(editTextMonth.getText().toString().trim().length()<2)
                {
                    thang="0"+editTextMonth.getText().toString().trim();
                }
                else
                {
                    thang=editTextMonth.getText().toString().trim();
                }
                NgayString=ngay+thang+nam;
                double money=Double.parseDouble(editTxtTien.getText().toString().trim());
                String note =editTextNote.getText().toString().trim();
                db=SQLDatabase.getInstance(Reciept.this);
                db.onCreate(db.getWritableDatabase());
                if(hdThu!=null)
                {
                    int id= hdThu.getID();

                    if(db.insertBanGhiThu(NgayString,id,money,note)>0)
                    {
                        Log.e("Insert BanGhiThu succeeded","HORAAY");
                    }
                    Cursor cursor = db.Query("SELECT * FROM BanGhiThu",new String[]{});
                    String row="";
                    while (cursor.moveToNext())
                    {
                        row="";
                        row+="STT"+cursor.getInt(0);
                        row+="Ngay"+cursor.getString(1);
                        row+="ID"+cursor.getInt(2)+"/";
                        row+="Money"+cursor.getInt(3)+"/";
                        row+="Note"+cursor.getString(4)+"/";
                        Log.i("Row"+cursor.getPosition(),row);
                    }

                }
                if(hdChi!=null)
                {
                    int id= hdChi.getID();

                    if(db.insertBanGhiChi(NgayString,id,money,note)>0)
                    {
                        Log.e("Insert BanGhiChi succeeded","HORAAY");
                    }
                    Cursor cursor = db.Query("SELECT * FROM BanGhiChi",new String[]{});
                    String row="";
                    while (cursor.moveToNext())
                    {
                        row="";
                        row+="STT"+cursor.getInt(0);
                        row+="Ngay"+cursor.getString(1);
                        row+="ID"+cursor.getInt(2)+"/";
                        row+="Money"+cursor.getInt(3)+"/";
                        row+="Note"+cursor.getString(4)+"/";
                        Log.i("Row"+cursor.getPosition(),row);
                    }
                }
                finish();
                overridePendingTransition(R.anim.slide_out_bottom,R.anim.slide_in_top);
            }
        });
        editTextMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0)
                {
                    if(editTextMonth.getText().toString().trim().length()>0)
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



    }




}
