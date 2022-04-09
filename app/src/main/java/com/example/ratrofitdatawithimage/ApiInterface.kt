package com.example.ratrofitdatawithimage

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiInterface {
    @Multipart
    @POST("AddCustomer.php")
    fun addCustomer(
        @Part image: MultipartBody.Part?,
        @Part("customer_name") customerName: RequestBody?,
        @Part("reference") reference: RequestBody?
    ): Observable<ModelResponse>
}
