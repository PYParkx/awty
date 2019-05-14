package edu.washington.pypark.arewethereyet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService


class MainActivity : AppCompatActivity() {

    var phoneNumberValidation: Boolean = false
    var nagMinutesValidation: Boolean = false
    var messageValidation: Boolean = false
    lateinit var message: String
    lateinit var phoneNumber: String
    lateinit var nagMinutes: Number
    var toastMessage: String = "Error:"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarm: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, Notification::class.java)

        val startButton: Button = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            toastMessage = "Error:"
            if (startButton.text.equals("Start")) {
                    val nagMinutesText: EditText = findViewById(R.id.minutes)
                    if (nagMinutesText.text.toString() != "") {
                        nagMinutesValidation = nagMinutesTest()
                    }
                    phoneNumberValidation = phoneNumberTest()
                    messageValidation = messaageTest()
                if (messageValidation == false) {
                    toastMessage = toastMessage + " Message is invalid."
                }
                if (phoneNumberValidation == false) {
                    toastMessage = toastMessage + " Phone Number is invalid."
                }
                if(nagMinutesValidation == false) {
                    toastMessage = toastMessage + " Minutes is invalid."
                }
                    if (phoneNumberValidation && nagMinutesValidation && messageValidation) {
                        startButton.text = "Stop"
                        val timeInterval = nagMinutes.toLong() * 60000
                        intent.putExtra("message", message)
                        intent.putExtra("phoneNumber", phoneNumber)
                        val pendingIntent = PendingIntent.getBroadcast(this,0, intent,0)
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeInterval, pendingIntent)
                    } else {
                        Toast.makeText(this, toastMessage,Toast.LENGTH_LONG).show()
                    }
            } else {
                    phoneNumberValidation = false
                    nagMinutesValidation = false
                    messageValidation = false
                    startButton.text = "Start"
                    val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0,intent,PendingIntent.FLAG_CANCEL_CURRENT)
                    alarm.cancel(pendingIntent)
                 Toast.makeText(this, "The service has been stopped", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun messaageTest(): Boolean {
        val messageText: EditText = findViewById(R.id.message)
        message = messageText.text.toString()
        return message != ""
    }

    private fun nagMinutesTest(): Boolean {
        val nagMinutesText: EditText = findViewById(R.id.minutes)
        nagMinutes = Integer.parseInt(nagMinutesText.getText().toString())
        val test = Integer.parseInt(nagMinutesText.getText().toString())
        if (test <= 0) {
            return false
        } else if (test % 1 != 0) {
            return false
        } else {
            return true
        }
        return true
    }

    private fun phoneNumberTest(): Boolean {
        val phoneNumberText: EditText = findViewById(R.id.phoneNumber)
        phoneNumber = phoneNumberText.text.toString()
        phoneNumber = phoneNumberRemoveFluff(phoneNumber)
        return (phoneNumber.length == 10)
    }

    private fun phoneNumberRemoveFluff(phoneNumber: String): String {
        val Regex = "[^\\d]"
        return phoneNumber.replace(Regex.toRegex(), "")
    }
}
