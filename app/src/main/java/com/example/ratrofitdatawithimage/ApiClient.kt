package com.example.ratrofitdatawithimage

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    companion object {
        var retrofit: Retrofit? = null
        lateinit var apiInterface: ApiInterface
        val BASEURL = "https://trigonous.000webhostapp.com/"
        fun getClient(): ApiInterface {
            if (retrofit == null) {
                synchronized(this) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                    apiInterface = retrofit!!.create(ApiInterface::class.java)
                }


            }
            return apiInterface
        }
    }

}