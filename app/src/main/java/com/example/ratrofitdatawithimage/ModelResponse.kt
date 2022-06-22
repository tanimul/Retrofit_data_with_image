package com.example.ratrofitdatawithimage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelResponse(
    @SerializedName("info") @Expose
    private var info: List<String>,
    @SerializedName("status")
    @Expose
    private val status: Int
)