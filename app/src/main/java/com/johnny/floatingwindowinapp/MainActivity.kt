package com.johnny.floatingwindowinapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        this.title = "Floating Window Demo"
        view_window_container.marginCalculation = object :
            FloatingWindowContainer.MarginCalculation {
            override fun getMinLeftMargin(containerWidth: Int, floatingWindowWidth: Int): Int {
                return -floatingWindowWidth + ViewUtils.getPixels(this@MainActivity, inDP = 50)
            }

            override fun getMinTopMargin(containerHeight: Int, floatingWindowHeight: Int): Int {
                return -floatingWindowHeight + ViewUtils.getPixels(this@MainActivity, inDP = 50)
            }

            override fun getMaxLeftMargin(containerWidth: Int, floatingWindowWidth: Int): Int {
                return containerWidth - ViewUtils.getPixels(this@MainActivity, inDP = 50)
            }

            override fun getMaxTopMargin(containerHeight: Int, floatingWindowHeight: Int): Int {
                return containerHeight - ViewUtils.getPixels(this@MainActivity, inDP = 50)
            }
        }
        my_button.setOnClickListener {
            view_window_container.setFloatingWindowMargin(
                leftMargin = view_window_container.width -
                        ViewUtils.getPixels(this@MainActivity, inDP = 50),
                topMargin = view_window_container.height -
                        (view_window_container.floatingWindow?.height ?: 0) -
                        ViewUtils.getPixels(this@MainActivity, inDP = 50),
                animated = true
            ) {
                print("Complete!")
            }
        }
    }
}