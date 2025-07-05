package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.FoundItemAdapter
import com.example.myapplication.databinding.ActivityFoundItemsBinding
import com.example.myapplication.models.FoundItem

class FoundItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoundItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoundItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar with Back Arrow
        setSupportActionBar(binding.toolbarFoundItems)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarFoundItems.setNavigationOnClickListener {
            finish()
        }

        // Dummy Data
        val items = listOf(
            FoundItem("Backpack", "Library", "2025-07-04 2:00PM", "Black backpack with books", R.drawable.ic_launcher_foreground),
            FoundItem("Phone", "Cafeteria", "2025-07-02 10:00AM", "Samsung Galaxy S21", R.drawable.ic_launcher_foreground),
            FoundItem("Keys", "Parking Lot", "2025-07-01 5:00PM", "Set of car keys", R.drawable.ic_launcher_foreground)
        )

        binding.recyclerViewFoundItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFoundItems.adapter = FoundItemAdapter(items)
    }
}
