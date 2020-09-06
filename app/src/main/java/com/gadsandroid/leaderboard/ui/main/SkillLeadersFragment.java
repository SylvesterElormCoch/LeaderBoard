package com.gadsandroid.leaderboard.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadsandroid.leaderboard.R;
import com.gadsandroid.leaderboard.SkillLeadersAdapter;
import com.gadsandroid.leaderboard.api.utils.ApiService;
import com.gadsandroid.leaderboard.api.utils.SkillLeader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SkillLeadersFragment extends Fragment {
    public static final String BASE_URL = "https://gadsapi.herokuapp.com/";
    private static final String TAG = SkillLeadersFragment.class.getName();
    private List<SkillLeader> mTopSkillLeadersDataset;
    private SkillLeadersAdapter mAdapter;

    public SkillLeadersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopSkillLeadersDataset = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        api.getTopSkillLeaders().enqueue(new Callback<List<SkillLeader>>() {
            @Override
            public void onResponse(Call<List<SkillLeader>> call, Response<List<SkillLeader>> response) {
                displayData(response.body());
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<List<SkillLeader>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }

    private void displayData(List<SkillLeader> skillLeaders) {
        skillLeaders = sortData(skillLeaders);
        if (skillLeaders != null) {
            mTopSkillLeadersDataset = skillLeaders;
            mAdapter.setData(skillLeaders);
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<SkillLeader> sortData(List<SkillLeader> skillLeaders) {
        if (skillLeaders == null) {
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            skillLeaders.sort(new Comparator<SkillLeader>() {

                // reverse return values for sorting in descending order
                @Override
                public int compare(SkillLeader leader1, SkillLeader leader2) {
                    int scoreforleader1 = Integer.parseInt(leader1.getScore());
                    int scoreforleader2 = Integer.parseInt(leader2.getScore());
                    if (scoreforleader1 > scoreforleader2) {
                        return -1;
                    } else if (scoreforleader1 < scoreforleader2) {
                        return 1;
                    }
                    return 0;
                }
            });
        }

        return skillLeaders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skill_leaders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView mRecyclerView = view.findViewById(R.id.skill_leaders);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new SkillLeadersAdapter(mTopSkillLeadersDataset);
        mRecyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}