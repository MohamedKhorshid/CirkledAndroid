package com.augenblick.cirkle.model;

import android.graphics.Bitmap;
import android.media.Image;

import org.json.JSONException;
import org.json.JSONStringer;

/**
 * Created by Mohamed Wagdy on 3/31/2015
 */
public class User implements JSONifiable{

    private String userId;
    private String displayName;
    private String email;
    private Bitmap image;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public void jsonify(JSONStringer jsonStringer) throws JSONException {
        jsonStringer.object().key("id").value(getUserId()).key("email").value(getEmail()).endObject();
    }
}
