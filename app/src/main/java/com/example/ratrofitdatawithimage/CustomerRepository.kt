package com.example.ratrofitdatawithimage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CustomerRepository {
    private lateinit var apiInterface: ApiInterface

    init {
        apiInterface = ApiClient.getClient()
    }

    fun createNewStudent(
        body: MultipartBody.Part?,
        customerName: RequestBody?,
        customerReference: RequestBody?
    ): MutableLiveData<ModelResponse?> {
        val result = MutableLiveData<ModelResponse?>()
        apiInterface.addCustomer(body, customerName, customerReference).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<ModelResponse?> { s ->
                if (s != null) {
                    result.postValue(s)
                } else {
                    result.postValue(null)
                }
            }, Consumer<Throwable?> { result.postValue(null) })
        return result
    }

}