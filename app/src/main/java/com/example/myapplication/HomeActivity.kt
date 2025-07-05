package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        // Set up the Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Lost & Found"

        // Set up the Drawer Toggle
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle Navigation Drawer items
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    drawerLayout.closeDrawers()
                    true
                }

                R.id.nav_logout -> {
                    Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }

        // Post Item button ➜ Start PostItemActivity
        binding.buttonPostItem.setOnClickListener {
            startActivity(Intent(this, PostItemActivity::class.java))
            drawerLayout.closeDrawers()
        }

        // View Items button ➜ Start FoundItemsActivity
        binding.buttonViewItems.setOnClickListener {
            startActivity(Intent(this, FoundItemsActivity::class.java))
            drawerLayout.closeDrawers()
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(binding.navigationView)) {
            drawerLayout.closeDrawer(binding.navigationView)
        } else {
            super.onBackPressed()
        }
    }
}
