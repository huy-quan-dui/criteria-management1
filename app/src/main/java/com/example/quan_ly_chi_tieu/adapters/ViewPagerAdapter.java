package com.example.quan_ly_chi_tieu.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quan_ly_chi_tieu.fragments.chon_loai_hoa_don_fragment;

import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listFragment;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior,List<Fragment> listFragment) {
        super(fm, behavior);
        this.listFragment=listFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);

    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        chon_loai_hoa_don_fragment frag = (chon_loai_hoa_don_fragment) listFragment.get(position);

        return frag.getTabName();
    }
}
