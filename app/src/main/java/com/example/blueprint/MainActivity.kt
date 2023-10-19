package com.example.blueprint

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.blueprint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
        map->

        var isGranted= true
        for (items in map){
            if (!items.value){
                isGranted= false
            }
        }
        if(isGranted){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Permission Failed", Toast.LENGTH_SHORT).show()
        }
    }


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {

            readPermission()
        }

    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        )== PackageManager.PERMISSION_GRANTED

    }

    fun readPermission(){
        var permission= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA

            )
        } else {
            arrayOf(
               Manifest.permission.READ_EXTERNAL_STORAGE,
               Manifest.permission.ACCESS_FINE_LOCATION,
               Manifest.permission.ACCESS_FINE_LOCATION,
               Manifest.permission.CAMERA
            )

        }
        if (! hasPermission(permission[0])){
            permissionLauncher.launch(permission)

        }
    }
}