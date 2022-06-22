package com.example.ratrofitdatawithimage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CustomerRepository {
    private var apiInterface: ApiInterface = ApiClient.getClient()

    fun createNewStudent(
        body: MultipartBody.Part?,
        customerName: RequestBody?,
        customerReference: RequestBody?
    ): LiveData<ModelResponse?>? {
        val result = MutableLiveData<ModelResponse?>()
        apiInterface.addCustomer(body, customerName, customerReference).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<ModelResponse?> { s ->
                Log.d("DDDDDDDDDDDDDDDDDDD", "createNewStudent: "+s)
                if (s != null) {
                    result.postValue(s)
                    Log.d("DDDDDDDDDDDDDDDDDDD", "createNewStudent: "+s)
                } else {
                    result.postValue(null)
                }
            }, Consumer<Throwable?> { result.postValue(null) })
        return result
    }
}