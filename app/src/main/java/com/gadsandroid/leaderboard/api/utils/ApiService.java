package com.gadsandroid.leaderboard.api.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/api/hours")
    Call<List<LearningLeader>> getTopLearningLeaders();

    @GET("/api/skilliq")
    Call<List<SkillLeader>> getTopSkillLeaders();

    //TODO: replace with GADS link
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    Call<Void> postProjectSubmission(
            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.1824927963") String email,
            @Field("entry.284483984")  String gitHubLink);
}
