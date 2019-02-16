package com.example.maste.minigram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                                 .applicationId("codepath-shawn-insta") // should correspond to APP_ID env variable
                                 .clientKey("cMT955Ac7FiU")  // set explicitly unless clientKey is explicitly configured on Parse server
                                 .server("http://codepath-shawn-insta.herokuapp.com/parse").build());
    }
}
