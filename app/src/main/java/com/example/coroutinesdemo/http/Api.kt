package com.example.coroutinesdemo.http

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface Api {
    @POST("login")
    @FormUrlEncoded
    fun login(@Field("userName")userName:String, @Field("passWord")passWord:String): Call<UserRequest>


}