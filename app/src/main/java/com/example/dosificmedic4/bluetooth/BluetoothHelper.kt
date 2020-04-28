package com.example.dosificmedic4.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

object BluetoothHelper {

    private var mmDevice: BluetoothDevice? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    var mBluetoothSocket: BluetoothSocket? = null

    private val BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun crearBtAdapter(): BluetoothAdapter? {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return mBluetoothAdapter
    }

    fun crearBtSocket(adress: String?): BluetoothSocket? {
        mmDevice = mBluetoothAdapter!!.getRemoteDevice(adress)
        try {
            mBluetoothSocket = createBluetoothSocket(mmDevice)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return mBluetoothSocket
    }

    fun conectarBtSocket(): String {
        return try {
            mBluetoothSocket!!.connect()
            "1"
        } catch (e: IOException) {
            try {
                mBluetoothSocket!!.close()
            } catch (e2: IOException) {
                e.printStackTrace()
            }
            e.printStackTrace()
            "0"
        }
    }

    @Throws(IOException::class)
    private fun createBluetoothSocket(device: BluetoothDevice?): BluetoothSocket {
        return if (mmDevice!!.uuids[0].uuid != null) {
            device!!.createRfcommSocketToServiceRecord(mmDevice!!.uuids[0].uuid)
        } else {
            device!!.createRfcommSocketToServiceRecord(BTMODULEUUID)
        }
    }

    fun cerrarSocket() {
        try {
            mBluetoothSocket!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}