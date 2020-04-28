package com.example.dosificmedic4.ui.activity

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.dosificmedic4.R
import kotlinx.android.synthetic.main.activity_lista_dispositivos.*

class ListaDispositivosActivity : Activity() {

    private var mBtAdapter: BluetoothAdapter? = null
    private var mPairedDevicesArrayAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_dispositivos)
    }

    public override fun onResume() {
        super.onResume()

        checkBTState()
        connecting.textSize = 40f
        connecting.text = " "

        mPairedDevicesArrayAdapter = ArrayAdapter(this, R.layout.device_name)

        paired_devices.adapter = mPairedDevicesArrayAdapter
        paired_devices.onItemClickListener = mDeviceClickListener

        mBtAdapter = BluetoothAdapter.getDefaultAdapter()

        val pairedDevices = mBtAdapter?.getBondedDevices()

        if (pairedDevices!!.size > 0) {
            findViewById<View>(R.id.title_paired_devices).visibility = View.VISIBLE
            for (device in pairedDevices) {
                mPairedDevicesArrayAdapter?.add(device.name + "\n" + device.address)
            }
        } else {
            val noDevices = "Ningun dispositivo pudo ser emparejado"
            mPairedDevicesArrayAdapter?.add(noDevices)
        }
    }

    private val mDeviceClickListener = OnItemClickListener { _, v, _, _ ->
        connecting.text = "Conectando..."
        val info = (v as TextView).text.toString()
        val address = info.substring(info.length - 17)
        val i = Intent(this@ListaDispositivosActivity, MainActivity::class.java)
        i.putExtra(EXTRA_DEVICE_ADDRESS, address)
        startActivity(i)
    }

    private fun checkBTState() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBtAdapter == null) {
            Toast.makeText(baseContext, "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show()
        } else {
            if (mBtAdapter!!.isEnabled) {
                Log.d(TAG, "...Bluetooth Activado...")
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 1)
            }
        }
    }

    companion object {
        private const val TAG = "DeviceListActivity"
        var EXTRA_DEVICE_ADDRESS = "device_address"
    }
}