package com.zaher.pinproject.data.remote.service;

import com.zaher.pinproject.data.remote.User.LoginObject;
import com.zaher.pinproject.data.remote.User.UserRequest;
import com.zaher.pinproject.data.remote.User.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRegisterService {

    @POST("register-users/")
    Call<UserResponse> updateuser(@Body UserRequest Request);

    @POST("register-users/login/")
    Call<UserResponse> getUser(@Body LoginObject loginObject);
}
