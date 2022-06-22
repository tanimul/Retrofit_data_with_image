package com.example.ratrofitdatawithimage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CustomerViewModel(application: Application) : AndroidViewModel(application) {
    var customerRepository: CustomerRepository = CustomerRepository()
    fun createNewEvent(
        body: MultipartBody.Part?,
        customerName: RequestBody?,
        customerReference: RequestBody?
    ): LiveData<ModelResponse?>? {
        return customerRepository.createNewStudent(body, customerName, customerReference)
    }
}