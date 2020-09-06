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
    @POST("1FAIpQLSfIwQq5-PFqt3o3ndOrtgo6zVQQLJZ6ydGLQIQtcc9x_dPY7Q/formResponse")
    @FormUrlEncoded
    Call<Void> postProjectSubmission(
            @Field("entry.2005620554") String firstName,
            @Field("entry.360305506") String lastName,
            @Field("entry.1045781291") String email,
            @Field("entry.1065046570")  String gitHubLink);
}
