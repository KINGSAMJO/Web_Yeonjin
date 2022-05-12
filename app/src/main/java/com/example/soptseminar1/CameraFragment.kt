package com.example.soptseminar1

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FragmentCameraBinding


class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화되지 않았습니다.")

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted: Boolean ->
        if(isGranted) {
            navigateGallery()
        } else {
            Toast.makeText(requireContext(), "갤러리 접근 권한이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private val galleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK && it.data != null) {
                val galleryImage = it.data?.data
                Glide.with(this)
                    .load(galleryImage)
                    .into(binding.imgGallery)
            } else if (it.resultCode == RESULT_CANCELED){
                Toast.makeText(requireContext(), "사진 선택이 취소되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        changeGalleryImage()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        galleryLauncher.launch(intent)
    }

    private fun showInContextUI(){
        AlertDialog.Builder(requireContext())
            .setTitle("권한 동의 필요")
            .setMessage("프로필 사진 수정을 위해 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            .setNegativeButton("거부") { _, _ ->
                Toast.makeText(requireContext(), "갤러리 접근 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }

    private fun changeGalleryImage(){
        binding.btnAttached.setOnClickListener{
            when {
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    navigateGallery()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showInContextUI()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }
}