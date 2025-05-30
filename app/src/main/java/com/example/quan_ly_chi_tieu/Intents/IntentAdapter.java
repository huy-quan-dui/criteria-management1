package com.example.quan_ly_chi_tieu.Intents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.quan_ly_chi_tieu.classes.HoaDonChi;
import com.example.quan_ly_chi_tieu.classes.HoaDonThu;

public class IntentAdapter {
    public static Intent createIntentWithData(Context currentActivity, Class<?> activity, String bundleName, String[] key, Object[] data)
    {
        Intent intent;
        intent = new Intent(currentActivity, activity);
        if(bundleName!=null)
        {
            Bundle bundle = new Bundle();
            for(int i =0;i<data.length;i++)
            {
                if(data[i].getClass().equals(String.class))
                {
                    bundle.putString(key[i],data[i].toString());
                }
                else if(data[i].getClass().equals(Integer.class))
                {
                    bundle.putInt(key[i],Integer.parseInt(data[i].toString()));
                }
                else if(data[i].getClass().equals(Float.class))
                {
                    bundle.putFloat(key[i],Float.parseFloat(data[i].toString()));
                }
                else if(data[i].getClass().equals(Double.class))
                {
                    bundle.putDouble(key[i],Double.parseDouble(data[i].toString()));
                }
                else if(data[i].getClass().equals(HoaDonThu.class))
                {
                    bundle.putSerializable(key[i],(HoaDonThu) data[i]);
                }
                else if(data[i].getClass().equals(HoaDonChi.class))
                {
                    bundle.putSerializable(key[i],(HoaDonChi) data[i]);
                }
            }
            intent.putExtra(bundleName,bundle);
        }

        return intent;
    }
    public static Intent createIntentWithoutData(Context currentActivity, Class<?> activity)
    {
        Intent intent;
        intent = new Intent(currentActivity, activity);
        return intent;
    }

}
