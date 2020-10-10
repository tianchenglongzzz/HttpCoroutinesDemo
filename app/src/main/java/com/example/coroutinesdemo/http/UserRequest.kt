package com.example.coroutinesdemo.http

data class UserRequest(
    val code: Int,
    val `data`: Data,
    val flag: Boolean,
    val message: String
)