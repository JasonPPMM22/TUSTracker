package com.example.tustracker.ui

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

// setting up Firebase Messaging Service to handle notifications
class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Triggered when a message or notification is received from Firebase Cloud Messaging
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if the message contains a notification
        remoteMessage.notification?.let {
            val title = it.title
            val message = it.body

            sendNotification(title, message)
        }
    }

    // Called when a new token is generated for the device
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun sendNotification(title: String?, message: String?) {
        // Get the system notification service
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate a random notification ID
        val notificationId = Random.nextInt()

        // Building the notification
        val notificationBuilder =
            NotificationCompat.Builder(this, "default_channel").setContentTitle(title)
                .setContentText(message).setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Notify user with the built notification
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
