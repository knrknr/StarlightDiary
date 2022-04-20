package com.nrsoft.starlightdiary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_intro)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
        },2000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}