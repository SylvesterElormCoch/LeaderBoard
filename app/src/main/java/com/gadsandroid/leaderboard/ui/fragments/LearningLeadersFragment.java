package com.gadsandroid.leaderboard.ui.fragments;

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

import com.gadsandroid.leaderboard.adapters.LearningLeadersAdapter;
import com.gadsandroid.leaderboard.R;
import com.gadsandroid.leaderboard.api.utils.ApiService;
import com.gadsandroid.leaderboard.api.utils.LearningLeader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LearningLeadersFragment extends Fragment {
    public static final String BASE_URL = "https://gadsapi.herokuapp.com/";
    private static final String TAG = LearningLeadersFragment.class.getName();
    private List<LearningLeader> mTopLearningLeadersDataset;
    private RecyclerView mRecyclerView;
    private LearningLeadersAdapter mAdapter;

    public LearningLeadersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopLearningLeadersDataset = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        api.getTopLearningLeaders().enqueue(new Callback<List<LearningLeader>>() {
            @Override
            public void onResponse(Call<List<LearningLeader>> call, Response<List<LearningLeader>> response) {
                displayData(response.body());
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<List<LearningLeader>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void displayData(List<LearningLeader> learningLeaders) {
        learningLeaders = sortData(learningLeaders);
        Log.d(TAG, "displayData: " + learningLeaders.get(0).getName());
        if (learningLeaders != null) {
            mTopLearningLeadersDataset = learningLeaders;
            mAdapter.setData(learningLeaders);
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<LearningLeader> sortData(List<LearningLeader> learningLeaders) {
        if (learningLeaders == null){
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // reverse return values for sorting in descending order
            learningLeaders.sort(new Comparator<LearningLeader>() {
                @Override
                public int compare(LearningLeader leader1, LearningLeader leader2) {
                    int hoursforleader1 = Integer.parseInt(leader1.getHours());
                    int hoursforleader2 = Integer.parseInt(leader2.getHours());
                    if (hoursforleader1 > hoursforleader2){
                        return -1;
                    }else if ( hoursforleader1 < hoursforleader2){
                        return 1;
                    }
                    return 0;
                }
            });
        }

        return learningLeaders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_leaders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.learning_leaders);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new LearningLeadersAdapter(mTopLearningLeadersDataset);
        mRecyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}