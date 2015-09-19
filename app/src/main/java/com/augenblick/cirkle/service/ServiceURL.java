package com.augenblick.cirkle.service;

/**
 * Created by Mohamed Wagdy on 4/11/2015
 */
public enum ServiceURL {
    REGISTER("/public/register"),
    LOGIN("/public/login"),
    CIRCLES("/cirkles"),
    SEARCH_USERS("/search/users");

    private String url;

    ServiceURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}