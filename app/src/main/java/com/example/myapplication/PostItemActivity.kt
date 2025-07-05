package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityPostItemBinding

class PostItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostItemBinding

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Set up the Toolbar with back arrow
        setSupportActionBar(binding.toolbarPostItem)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarPostItem.setNavigationOnClickListener {
            finish() // This goes back to HomeActivity
        }

        // ✅ Add Photo button
        binding.buttonPickImage.setOnClickListener {
            showImagePickerDialog()
        }

        // ✅ Submit button
        binding.buttonSubmitItem.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val place = binding.editTextPlaceFound.text.toString().trim()
            val timeFound = binding.editTextTimeFound.text.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()

            if (name.isEmpty() || place.isEmpty() || timeFound.isEmpty() || description.isEmpty()) {
                binding.textViewStatus.text = "⚠️ Please complete all fields!"
            } else {
                // TODO: Save to DB / server later
                binding.textViewStatus.text = "✅ Lost Item Posted Successfully!"
            }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            binding.textViewStatus.text = "❌ No camera app found!"
        }
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    if (imageBitmap != null) {
                        binding.imageViewPreview.setImageBitmap(imageBitmap)
                        binding.textViewStatus.text = "Photo captured!"
                    } else {
                        binding.textViewStatus.text = "Failed to capture image."
                    }
                }
                REQUEST_IMAGE_PICK -> {
                    val imageUri: Uri? = data?.data
                    if (imageUri != null) {
                        binding.imageViewPreview.setImageURI(imageUri)
                        binding.textViewStatus.text = "Image selected!"
                    } else {
                        binding.textViewStatus.text = "Failed to pick image."
                    }
                }
            }
        }
    }
}
