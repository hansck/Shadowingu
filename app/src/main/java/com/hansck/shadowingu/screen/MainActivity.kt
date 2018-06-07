package com.hansck.shadowingu.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.hansck.shadowingu.R
import com.hansck.shadowingu.util.Calculation
import com.hansck.shadowingu.util.MethodCallListener


class MainActivity : AppCompatActivity(), MethodCallListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Calculation.getInstance().calculateMFCC(this,
                resources.getIdentifier("kare", "raw", packageName),
                resources.getIdentifier("kare2", "raw", packageName),
                this)
    }

    override fun onMFCCCalculated(distance: Double) {
        Log.e("DISTANCE HERE", distance.toString())
    }
}
