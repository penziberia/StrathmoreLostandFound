package com.example.myapplication.models

data class FoundItem(
    val name: String,
    val placeFound: String,
    val dateTime: String,
    val description: String,
    val imageResId: Int // For demo: use placeholder drawable
)
