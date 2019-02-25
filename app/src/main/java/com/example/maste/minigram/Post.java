package com.example.maste.minigram;

import android.support.annotation.NonNull;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String USER_KEY = "userPost";
    public static final String CREATED_KEY = "createdAt";
    private static final String DESC_KEY = "description";
    private static final String IMG_KEY = "image";

    public Post() {
    }

    public Post(String caption, ParseFile image, ParseUser user) {
        put(DESC_KEY, caption);
        put(IMG_KEY, image);
        put(USER_KEY, user);
    }

    public String getDesc() {
        return getString(DESC_KEY);
    }

    public ParseFile getImg() {
        return getParseFile(IMG_KEY);
    }

    public ParseUser getUser() {
        return getParseUser(USER_KEY);
    }

    @NonNull
    @Override
    public String toString() {
        return "User: " + getUser().getUsername() + "\nImage: " + getImg().getUrl() + "\nDesc: " + getDesc();
    }
}
