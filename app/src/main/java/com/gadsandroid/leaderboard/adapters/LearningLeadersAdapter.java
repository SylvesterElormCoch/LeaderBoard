package com.gadsandroid.leaderboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadsandroid.leaderboard.R;
import com.gadsandroid.leaderboard.api.utils.LearningLeader;

import java.util.List;

public class LearningLeadersAdapter extends RecyclerView.Adapter {
    List<LearningLeader> mTopLearningLeaders;
    public LearningLeadersAdapter(List<LearningLeader> learningLeadersDataSet) {
        mTopLearningLeaders = learningLeadersDataSet;
    }

    public void setData(List<LearningLeader> mTopLearningLeadersDataset) {
        mTopLearningLeaders = mTopLearningLeadersDataset;
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
                .inflate(R.layout.learning_leader_layout, parent, false);

        LearningLeadersAdapter.MyViewHolder vh = new LearningLeadersAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LearningLeader learningLeader = mTopLearningLeaders.get(position);
        String name = learningLeader.getName();
        String hours = learningLeader.getHours();
        String country = learningLeader.getCountry();
        String subtitle = hours + " learning hours, " + country;
        ((MyViewHolder) holder).mTitle.setText(name);
        ((MyViewHolder) holder).mSubTitle.setText(subtitle);
    }

    @Override
    public int getItemCount() {
        return mTopLearningLeaders.size();
    }
}
