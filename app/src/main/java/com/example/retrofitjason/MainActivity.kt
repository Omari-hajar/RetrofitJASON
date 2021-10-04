package com.example.retrofitjason

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.RelativeLayout
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASE_URL ="https://jsonplaceholder.typicode.com/"
class MainActivity : AppCompatActivity() {

    private lateinit var layoutGenFacts: RelativeLayout
    private lateinit var tvText: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvOutput: TextView


    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        layoutGenFacts = findViewById(R.id.layoutGenFacts)

        tvText = findViewById(R.id.tvText)
        tvTime = findViewById(R.id.tvTime)
        tvOutput = findViewById(R.id.tvOutput)



        getMyData();

    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()

            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)


        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for(myData in responseBody){
                    myStringBuilder.append(myData.id)
                    myStringBuilder.append("\n")
                }

                tvOutput.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {

                Log.d("TAG", "OnFailure" + t.message)

            }
        })

    }

}