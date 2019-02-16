package com.example.maste.minigram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    protected static final String DESC_KEY = "description";
    protected static final String IMG_KEY = "image";
    protected static final String USER_KEY = "userPost";

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

    public void setDesc(String s) {
        put(DESC_KEY, s);
    }

    public ParseFile getImg() {
        return getParseFile(IMG_KEY);
    }

    public void setImg(ParseFile f) {
        put(IMG_KEY, f);
    }

    public ParseUser getUser() {
        return getParseUser(USER_KEY);
    }

    public void setUser(ParseUser u) {
        put(USER_KEY, u);
    }
}
