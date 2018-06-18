package com.patelheggere.committeelection;

import android.app.Application;

public class CommitteElection extends Application {
    private static CommitteElection mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // ApiClient.intialise();
       /* if(isDeve()) {
            ApiClient.setBaseUrl(AppConstants.appBaseUrlDev);
        }
        else
        {
            ApiClient.setBaseUrl(AppConstants.appBaseUrl);

        }*/

    }

    public static synchronized CommitteElection getInstance() {
        return mInstance;
    }
}
