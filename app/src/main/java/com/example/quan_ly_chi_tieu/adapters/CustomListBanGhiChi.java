package com.example.quan_ly_chi_tieu.adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quan_ly_chi_tieu.R;
import com.example.quan_ly_chi_tieu.activities.Expense;
import com.example.quan_ly_chi_tieu.classes.BanGhiChi;
import com.example.quan_ly_chi_tieu.classes.BanGhiThu;
import com.example.quan_ly_chi_tieu.data.SQLDatabase;

import java.util.List;

public class CustomListBanGhiChi extends BaseAdapter {
    List<BanGhiChi> list;
    private int layout;
    private Context context;



    public interface OnDataSetChangedListener {
        void onDataSetChanged();
    }
    public void SetDataSetChangedListener(OnDataSetChangedListener listener)
    {
        this.listener=listener;
    }
    public interface OnDialogResultListener {
        void onResult(BanGhiChi banGhiChi);
    }
    public OnDataSetChangedListener listener;

    public CustomListBanGhiChi(int layout, Context context,List<BanGhiChi> list ) {
        this.layout = layout;
        this.context = context;
        this.list=list;
    }


    public void clearDataForChi() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    // Method to clear data for HoaDonChi

    public void addDataForChi(List<BanGhiChi> newData) {
        this.clearDataForChi();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    // Method to add data for HoaDonChi


    @Override
    public int getCount() {
        return list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(layout,null);

        BanGhiChi banGhi=list.get(position);

        ImageView img=(ImageView) convertView.findViewById(R.id.idImgViewLoai);
        img.setImageResource(R.drawable.expense);

        TextView STT=(TextView) convertView.findViewById(R.id.idTextViewStt);
        STT.setText(banGhi.getSTT()+"");

        TextView Ngay=(TextView)convertView.findViewById(R.id.idTextViewNgay);
        String ngay="";
        String thang="";
        String nam="";

        ngay=banGhi.getNgay().substring(0,2);
        thang=banGhi.getNgay().substring(2,4);
        nam=banGhi.getNgay().substring(4);




        Ngay.setText(ngay+"/"+thang+"/"+nam);

        TextView TenHD=(TextView)convertView.findViewById(R.id.idTextViewTenHD);
        SQLDatabase db= SQLDatabase.getInstance(context);
        db.onCreate(db.getReadableDatabase());
        Cursor cursor= db.Query("SELECT TenHoaDon FROM LoaiChi WHERE ID=?",new String[]{banGhi.getID()+""});
        if(cursor!=null && cursor.moveToFirst())
        {
            TenHD.setText(cursor.getString(0));
        }

        TextView Money=(TextView)convertView.findViewById(R.id.idTextViewMoney);
        Money.setText(banGhi.getMoney()+" VNĐ");
        TextView Note=(TextView)convertView.findViewById(R.id.idTextViewNote);
        Note.setText(banGhi.getNote());

        ImageButton buttonDestroy=convertView.findViewById(R.id.idImgButtonDelete);
        buttonDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int STTtoDelete=banGhi.getSTT();
                String NgayToDelete=banGhi.getNgay();
                db.deleteBanGhiChi(STTtoDelete,NgayToDelete);
                list.remove(position);
                notifyDataSetChanged();
                listener.onDataSetChanged();

            }
        });
        ImageButton buttonUpdate=convertView.findViewById(R.id.idImgButtonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(banGhi, new OnDialogResultListener() {
                    @Override
                    public void onResult(BanGhiChi banGhiChi) {
                        Money.setText(banGhiChi.getMoney()+" VNĐ");
                        Note.setText(banGhiChi.getNote());
                        db.updateBanGhiChi(banGhiChi.getSTT(),banGhiChi.getNgay(),banGhiChi.getMoney(),banGhiChi.getNote());
                        listener.onDataSetChanged();
                    }
                });



            }
        });




        return convertView;

    }
    private void ShowDialog(BanGhiChi banGhiChi,OnDialogResultListener listener)
    {
        BanGhiChi newBanGhiChi=banGhiChi;
        final Dialog dialog=new Dialog(context);
        dialog.setTitle("Sửa đổi");
        dialog.setContentView(R.layout.dialog_change);

        EditText dialogEditTxtMoney=dialog.findViewById(R.id.idEditTxt_MoneyUpdate);
        dialogEditTxtMoney.setText(banGhiChi.getMoney()+"");

        EditText DialogEditTxtNote=dialog.findViewById(R.id.idEditTxt_NoteUpdate);
        DialogEditTxtNote.setText(banGhiChi.getNote());

        ImageButton bttnOK=dialog.findViewById(R.id.idImgButtonUpdateOK);
        bttnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBanGhiChi.setMoney( Float.parseFloat(dialogEditTxtMoney.getText().toString()));
                newBanGhiChi.setNote(DialogEditTxtNote.getText().toString());
                listener.onResult(newBanGhiChi);
                dialog.dismiss();


            }
        });
        ImageButton bttnCancel=dialog.findViewById(R.id.idImgButtonUpdateNo);
        bttnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dialog.dismiss();
            }
        });
        dialog.show();

    }
}
