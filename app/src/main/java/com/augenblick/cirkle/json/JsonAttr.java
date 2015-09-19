package com.augenblick.cirkle.json;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public enum JsonAttr {

    TYPE("type"),
    ERROR_PARAM_MESSAGE("message"),
    ERROR_PARAM_NAME("parameter");

    String name;

    JsonAttr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
