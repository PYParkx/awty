package edu.washington.pypark.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Notification: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent!!.getStringExtra("message")
        val phoneNumber = intent!!.getStringExtra("phoneNumber")
        val number = "Texting (" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6)
        val msg = number + "\n" + message
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}