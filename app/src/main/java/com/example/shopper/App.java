package com.example.shopper;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

/**
 * This class creates a notification channel for shopper. Notification channels became
 * necessary starting with android oreo to be able to show notifications. This notification channel
 * for shopper will be created as soon as the application starts and will be available for all other
 * activity classes to use.
 */
public class App extends Application {

    //delcare and initialize a channel ID
    public static final String CHANNEL_SHOPPER_ID = "channelshopper";

    //override the oncreate method

    @Override
    public void onCreate() {
        super.onCreate();

        //call method that creates notification channel for shopper
        createNotificationChannel();
    }

    /**
     * This method creates the notification channel for shopper
     */
    protected void createNotificationChannel() {
        //check if android oreo (API 26) or higher because notification channels were not available
        //on lower versions
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //initialize notification channel - must give it an ID, name, and importance
            NotificationChannel channelshopper = new NotificationChannel(
                    CHANNEL_SHOPPER_ID,
                    "Channel Shopper",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            //customize the notification channel - set its description
            channelshopper.setDescription("This is the shopper channel.");

            //initialize a notification manager
            NotificationManager manager = getSystemService(NotificationManager.class);

            //create shopper notification channel
            manager.createNotificationChannel(channelshopper);
        }
    }
}
