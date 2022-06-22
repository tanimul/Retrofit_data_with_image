package com.example.ratrofitdatawithimage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ratrofitdatawithimage.databinding.ActivityMainBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var filepath_uri: Uri? = null
    private lateinit var bitMap: Bitmap
    private var path: String=""
    var customerViewModel: CustomerViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customerViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CustomerViewModel::class.java]

        binding.btSelectImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                mGetContent.launch("image/*")
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }
        }

        binding.btInsert.setOnClickListener { view ->
            addCustomer(
                binding.etName.text.toString(),
                binding.etPhoneNo.text.toString()
            )
        }
    }

    private val mGetContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri != null) {
                Glide
                    .with(this)
                    .load(uri)
                    .centerCrop()
                    .into(binding.ivImage)
                filepath_uri = uri

                val input_stream = uri?.let { contentResolver.openInputStream(it) }

                // Handle the returned Uri
                val context: Context = this@MainActivity

                path = RealPathUtil().getRealPath(context, uri)

                bitMap = BitmapFactory.decodeStream(input_stream)


            }


        }

    private fun addCustomer(name: String, reference: String) {

        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val customerName = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        val customerReference =
            RequestBody.create(MediaType.parse("multipart/form-data"), reference)
        customerViewModel!!.createNewEvent(body, customerName, customerReference)!!
            .observe(
                this@MainActivity
            ) { modelResponse ->

                Log.d(TAG, "addCustomer: ..."+modelResponse)
                Toast.makeText(this@MainActivity, "addCustomer", Toast.LENGTH_SHORT)
                    .show()
                if (modelResponse != null) {
                    Log.d(TAG, "addCustomer: $modelResponse")
                    Log.d(TAG, "Not Null: ...")
//                    if (modelResponse.getStatus().toString().equals("200")) {
//                        Toast.makeText(this@MainActivity, "Added Successfully", Toast.LENGTH_SHORT)
//                            .show()
//                    } else {
//                        Toast.makeText(this@MainActivity, "Not Added", Toast.LENGTH_SHORT).show()
//                    }
                }else{
                    Log.d(TAG, "Null: ...")
                }
            }
    }
}