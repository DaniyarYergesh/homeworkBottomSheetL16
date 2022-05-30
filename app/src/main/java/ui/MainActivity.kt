package ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework_recyclerview.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import ui.fragments.Fragment1
import ui.fragments.Fragment2


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.page_1 -> {
                    true
                }
                R.id.page_2 -> {
                    true
                }
                R.id.page_3 -> {
                    val transaction = supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, Fragment1(), "MainFragment")
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                R.id.page_4 -> {
                    true
                }
                R.id.page_5 -> {
                    val transaction = supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, Fragment2(), "MainFragment")
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                else -> false
            }
        }
    }
}