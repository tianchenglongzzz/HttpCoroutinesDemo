package com.example.coroutinesdemo.http
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import java.io.IOException
import java.net.ConnectException
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
fun <T> CoroutineScope.retrofit(dsl: RetrofitCoroutineDSL<T>.() -> Unit) {
    //在主线程中开启协程
    this.launch(Dispatchers.Main) {
        val coroutine = RetrofitCoroutineDSL<T>().apply(dsl)
        coroutine.api?.let { call ->
            //async 并发执行 在IO线程中

            var deferred =async(Dispatchers.IO) {
                try {
                    val execute = call.execute()
                    execute //已经在io线程中了，所以调用Retrofit的同步方法
                } catch (e: ConnectException) {
                    coroutine.onFail?.invoke("网络连接出错", -1)
                    null
                } catch (e: IOException) {
                    coroutine.onFail?.invoke("未知网络错误", -1)
                    null
                }
            }
            //当协程取消的时候，取消网络请求
            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                    coroutine.clean()
                }
            }
            //await 等待异步执行的结果
            val response = deferred.await()
            if (response == null) {
                coroutine.onFail?.invoke("返回为空", -1)
            } else {
                response.let {
                    it.body().let {
                      //  it?.onFailure {
                           // coroutine.onFail?.invoke(response.errorBody().toString(), response.code())
                      // Result
                        coroutine.onSuccess?.invoke(it!!)
                        }
                     //   it?.onSuccess {

                      //  }

                    }
                    /* if (response.isSuccessful) {
                       response.body().let {
                           //访问接口成功
                               if (it!!.isSuccess) {
                                   //判断status 为1 表示获取数据成功
                                   coroutine.onSuccess?.invoke(response.body()?.getOrNull()!!)
                               } else {
                                   coroutine.onFail?.invoke(response.message()?: "返回数据为空", response.code())
                               }

                       }
                    } else {
                        coroutine.onFail?.invoke(response.errorBody().toString(), response.code())
                    }*/
                }
            }
            coroutine.onComplete?.invoke()
        }
    }


fun createRetrofit():Retrofit= Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(createOkHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
fun  createApi()= createRetrofit().create(Api::class.java)

fun  createOkHttpClient()=OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor())
    .build()