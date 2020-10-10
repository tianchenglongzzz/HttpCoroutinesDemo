package com.example.coroutinesdemo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinesdemo.http.createApi
import com.example.coroutinesdemo.http.retrofit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity() : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val TAG:String="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, "主线程id：${mainLooper.thread.id}")
        job= Job()
        retrofit<com.example.coroutinesdemo.http.UserRequest> {
            api= createApi().login("sanji","123456")

            onFail { msg, errorCode ->

            }
            onSuccess {
                Log.d(TAG, "onCreate: "+it.toString());
                tv.setText(it.toString())
            }

        }
        //test()
        GlobalScope.launch() {
           // delay(3000)
            Log.e(TAG, "协程执行结束 -- 线程id：${Thread.currentThread().id}")
          //  tv.setText(TAG)
        }
        GlobalScope.launch {
            val result1 = GlobalScope.async {
                getResult1()
            }
            val result2 = GlobalScope.async {
                getResult2()
            }
            val result = result1.await() + result2.await()
            Log.e(TAG,"result = $result")
        }
     //   Log.e(TAG, "协程执行结束")
    }
   /* fun test(){
        runOnUiThread(){
            repeat(8){
                Log.e(TAG, "协程执行$it 线程id：${Thread.currentThread().id}")
                Thread.sleep(1000)
            }
        }
    }*/
   private suspend fun getResult1(): Int {
       delay(3000)
       return 1
   }

    private suspend fun getResult2(): Int {
        delay(4000)
        return 2
    }

    override fun onDestroy() {
        super.onDestroy()
       job.cancel()
    }
}