package com.example.dosificmedic4.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dosificmedic4.ui.fragment.AlarmaFragment
import com.example.dosificmedic4.R
import com.example.dosificmedic4.bluetooth.BluetoothHelper
import com.example.dosificmedic4.ui.fragment.WifiFragment
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        fragment_pager.adapter = pagerAdapter

        crearAdapter()
        crearSocket()
        conectarSocket()
    }

    @Suppress("DEPRECATION")
    private inner class ScreenSlidePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

        val alarmaFragment = AlarmaFragment()
        val wifiFragment = WifiFragment()

        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> return alarmaFragment
                1 -> return wifiFragment
            }
            return alarmaFragment
        }

        override fun getCount(): Int {
            return 2
        }
    }

    private fun conectarSocket() {
        if (BluetoothHelper.conectarBtSocket() == "0") {
            Toast.makeText(baseContext, "La conexión al socket falló", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun crearSocket() {
        val adress = intent.getStringExtra(EXTRA_DEVICE_ADDRESS)

        if (BluetoothHelper.crearBtSocket(adress) == null) {
            Toast.makeText(baseContext, "La creación del Socket falló", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun crearAdapter() {
        if (BluetoothHelper.crearBtAdapter() == null) {
            Toast.makeText(baseContext, "El dispositivo no soporta bluetooth", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BluetoothHelper.cerrarSocket()
    }

    companion object {
        val EXTRA_DEVICE_ADDRESS = "device_address"
    }
}