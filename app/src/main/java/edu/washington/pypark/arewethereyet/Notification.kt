package edu.washington.pypark.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Notification: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent!!.getStringExtra("message")
        val phoneNumber = intent!!.getStringExtra("phoneNumber")
        val number = "(" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6)
        Toast.makeText(context, number + ": \n"  + message, Toast.LENGTH_LONG).show()
    }
}