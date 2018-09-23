package ru.debian17.cicerone.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.debian17.cicerone.R

class TestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TestActivityTag"
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TestActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

}
