package com.example.clase7

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private lateinit var statusTextView: TextView
    private var isDeviceStable = true
    private lateinit var stableSound: MediaPlayer
    private lateinit var movementSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        val resetButton: Button = findViewById(R.id.resetButton)

        // Configurar sonidos
        stableSound = MediaPlayer.create(this, R.raw.stable_sound)
        movementSound = MediaPlayer.create(this, R.raw.movement_sound)

        // Configurar los sensores
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        // Iniciar la detección de sensores
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        gyroscope?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Botón para reiniciar la detección
        resetButton.setOnClickListener {
            isDeviceStable = true
            statusTextView.text = "Estable"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]

                    // Ajuste de la sensibilidad para detección de estabilidad
                    if (abs(x) < 1 && abs(y) < 1 && abs(z - 9.8) < 1) {
                        if (!isDeviceStable) {
                            isDeviceStable = true
                            statusTextView.text = "Estable"
                            if (!stableSound.isPlaying) stableSound.start()  // Reproduce sonido de estabilidad
                            println("Estable - Sonido reproducido")
                        }
                    }
                }

                Sensor.TYPE_GYROSCOPE -> {
                    val rotationThreshold = 1.5f
                    if (abs(it.values[0]) > rotationThreshold || abs(it.values[1]) > rotationThreshold || abs(it.values[2]) > rotationThreshold) {
                        if (isDeviceStable) {
                            isDeviceStable = false
                            statusTextView.text = "En Movimiento"
                            if (!movementSound.isPlaying) movementSound.start()  // Reproduce sonido de movimiento
                            println("En Movimiento - Sonido reproducido")
                        }
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se requiere implementación para este caso
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar recursos de MediaPlayer
        stableSound.release()
        movementSound.release()
    }
}

