package com.example.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmManager: AlarmManager
    private lateinit var oneTimeAlarmIntent: PendingIntent
    private var intent: Intent? = null
    private val ALARM_TYPE = "ALARM_TYPE"
    private val ALARM_TYPE_ONE_TIME = "ALARM_TYPE_ONE_TIME"
    private val ALARM_DESCRIPTION = "ALARM_DESCRIPTION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        intent = Intent(this, MyReceiver::class.java)

        oneTimeAlarmIntent =
            PendingIntent.getBroadcast(this, 100, intent!!, PendingIntent.FLAG_IMMUTABLE)

        binding.btnStart.setOnClickListener {
            intent!!.putExtra(ALARM_TYPE, ALARM_TYPE_ONE_TIME)
            intent!!.putExtra(ALARM_DESCRIPTION, "One time alarm")

            alarmManager.set(
                AlarmManager.RTC, System.currentTimeMillis() + (5 * 1000),
                oneTimeAlarmIntent
            )
            Toast.makeText(this, "Alarm set.", Toast.LENGTH_SHORT).show()
        }

        binding.btnStop.setOnClickListener {
            alarmManager.cancel(oneTimeAlarmIntent)
            Toast.makeText(this, "Alarm cancelled.", Toast.LENGTH_SHORT).show()
        }
    }
}