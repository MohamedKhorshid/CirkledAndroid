package android.cirkle.com.json;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public enum JsonAttr {

    TYPE("type");

    String name;

    JsonAttr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
