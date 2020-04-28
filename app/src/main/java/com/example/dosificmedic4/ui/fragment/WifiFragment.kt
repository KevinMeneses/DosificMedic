package com.example.dosificmedic4.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dosificmedic4.R
import com.example.dosificmedic4.bluetooth.BluetoothHelper
import kotlinx.android.synthetic.main.fragment_wifi.*
import java.io.IOException

class WifiFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wifi, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btEnviarOnClick()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun btEnviarOnClick() {
        btEnviarWifi.setOnClickListener {

            if(BluetoothHelper.mBluetoothSocket != null){
                val msg = (red_edittext.text.toString() + ";"
                        + contrasena_edittext.text.toString()).toByteArray()
                try {
                    BluetoothHelper.mBluetoothSocket!!.outputStream.write(msg)
                    Toast.makeText(context, "Red enviada", Toast.LENGTH_SHORT).show()
                    BluetoothHelper.mBluetoothSocket!!.outputStream.flush()
                } catch (e: IOException){
                    Toast.makeText(context, "No se logró enviar la red", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
