package com.utopianblacklist.services;

import com.utopianblacklist.objects.BannerUsers;
import com.utopianblacklist.objects.Blacklisted;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetDataService {

    @GET("bannedUsers?banned=true")
    Call<List<BannerUsers>> getAllBannedUsers();

    @GET("bannedUsers?banned=true")
    Call<List<BannerUsers>> getBanStatusOfUser(@Query("name") String name);

    @GET()
    Call<Blacklisted> getBlackList(@Url String url);
}
