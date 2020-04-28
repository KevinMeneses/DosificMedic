package com.example.dosificmedic4.ui.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.dosificmedic4.bluetooth.BluetoothHelper
import com.example.dosificmedic4.R
import kotlinx.android.synthetic.main.fragment_alarma.*
import java.io.IOException

class AlarmaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alarma, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        alarmaOnClick()
        cantidadOnClick()
        nombreOnClick()
        frecuenciaOnClick()
        colorOnClick()

        btEnviarOnClick()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun btEnviarOnClick() {
        btEnviar.setOnClickListener {

            if(BluetoothHelper.mBluetoothSocket != null){
                val msg = (tpAlarma.hour.toString() + ":"
                        + tpAlarma.minute + ";"
                        + spFrecuencia.selectedItem + ";"
                        + spColor.selectedItem + ";"
                        + spCantidad.selectedItem + ";"
                        + etNombre.text + ";").toByteArray()
                try {
                    BluetoothHelper.mBluetoothSocket!!.outputStream.write(msg)
                    Toast.makeText(context, "Alarma enviada", Toast.LENGTH_SHORT).show()
                    BluetoothHelper.mBluetoothSocket!!.outputStream.flush()
                } catch (e: IOException){
                    Toast.makeText(context, "No se logró enviar la alarma", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun colorOnClick() {
        spColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val arrayColors = intArrayOf(Color.YELLOW, Color.GREEN, Color.RED, Color.parseColor("#572364"), Color.parseColor("#ff8000"), Color.BLUE)
                vColor.setBackgroundColor(arrayColors[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun frecuenciaOnClick() {
        ibFreq.setOnClickListener {
            if (llFreq.visibility == View.GONE) {
                llFreq.visibility = View.VISIBLE
                llAlarma.visibility = View.GONE
                llCantidad.visibility = View.GONE
                llNombre.visibility = View.GONE
            } else llFreq.visibility = View.GONE
        }
    }

    private fun nombreOnClick() {
        ibNombre.setOnClickListener {
            if (llNombre.visibility == View.GONE) {
                llNombre.visibility = View.VISIBLE
                llAlarma.visibility = View.GONE
                llCantidad.visibility = View.GONE
                llFreq.visibility = View.GONE
            } else llNombre.visibility = View.GONE
        }
    }

    private fun cantidadOnClick() {
        ibCantidad.setOnClickListener {
            if (llCantidad.visibility == View.GONE) {
                llCantidad.visibility = View.VISIBLE
                llAlarma.visibility = View.GONE
                llNombre.visibility = View.GONE
                llFreq.visibility = View.GONE
            } else llCantidad.visibility = View.GONE
        }
    }

    private fun alarmaOnClick() {
        ibAlarma.setOnClickListener {
            if (llAlarma.visibility == View.GONE) {
                llAlarma.visibility = View.VISIBLE
                llCantidad.visibility = View.GONE
                llNombre.visibility = View.GONE
                llFreq.visibility = View.GONE
            } else llAlarma.visibility = View.GONE
        }
    }
}
