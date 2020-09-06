package com.gadsandroid.leaderboard.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gadsandroid.leaderboard.R;
import com.gadsandroid.leaderboard.ui.fragments.LearningLeadersFragment;
import com.gadsandroid.leaderboard.ui.fragments.SkillLeadersFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.learning_leaders);
            case 1:
                return mContext.getString(R.string.skill_iq_leaders);
            default:
                return super.getPageTitle(position);
        }
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        if (position == 1) {
            return new SkillLeadersFragment();
        }
        return new LearningLeadersFragment();
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}