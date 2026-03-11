package com.onlysamhiking.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.onlysamhiking.app.databinding.ActivityMainBinding
import com.onlysamhiking.app.util.PermissionHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (PermissionHelper.hasLocationPermission(this)) {
            navigateToMap()
        } else {
            binding.tvPermissionStatus.visibility = View.VISIBLE
            binding.tvPermissionStatus.text = "위치 권한이 필요합니다"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (PermissionHelper.hasLocationPermission(this)) {
                navigateToMap()
            } else {
                permissionLauncher.launch(PermissionHelper.getAllRequiredPermissions())
            }
        }

        // Auto-navigate if permissions already granted
        if (PermissionHelper.hasLocationPermission(this)) {
            navigateToMap()
        }
    }

    private fun navigateToMap() {
        startActivity(Intent(this, MapActivity::class.java))
        finish()
    }
}
