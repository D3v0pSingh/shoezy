package com.example.shoezy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shoezy.databinding.ActivityMainBinding
import com.google.android.play.core.integrity.i

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.FragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_menubar)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        //This is just to set 0 on home button whenever it is clicked to know more watch ecommerce video
        binding.bottomBar.onItemSelected = {
            when(it){
                0 -> {
                    i = 0
                    navController.navigate(R.id.home_Fragment)
                }
                1 -> i = 1
                2 -> i = 2
            }
        }

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id){
                    R.id.cart_Fragment -> "Cart"
                    R.id.more_Fragment -> "Profile"
                    else -> "shoezy"
                }
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if ( i == 0){
            finish()
        }
    }
}