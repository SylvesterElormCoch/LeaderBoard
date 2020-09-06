package com.gadsandroid.leaderboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadsandroid.leaderboard.R;
import com.gadsandroid.leaderboard.api.utils.SkillLeader;

import java.util.List;

public class SkillLeadersAdapter extends RecyclerView.Adapter {
    List<SkillLeader> mTopSkillLeaders;

    public SkillLeadersAdapter(List<SkillLeader> leaders) {
        mTopSkillLeaders = leaders;
    }

    public void setData(List<SkillLeader> leaders){
        mTopSkillLeaders = leaders;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle;
        public TextView mSubTitle;

        public MyViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.main_title);
            mSubTitle = v.findViewById(R.id.sub_title);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_leader_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SkillLeader skillLeader = mTopSkillLeaders.get(position);
        String name = skillLeader.getName();
        String IQ = skillLeader.getScore();
        String country = skillLeader.getCountry();
        String subtitle = IQ + " skill IQ, " + country;
        ((SkillLeadersAdapter.MyViewHolder) holder).mTitle.setText(name);
        ((SkillLeadersAdapter.MyViewHolder) holder).mSubTitle.setText(subtitle);
    }

    @Override
    public int getItemCount() {
        return mTopSkillLeaders.size();
    }
}
