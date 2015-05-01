package android.cirkle.com.model;

import org.json.JSONException;
import org.json.JSONStringer;

/**
 * Created by Mohamed Wagdy on 5/1/2015
 */
public interface JSONifiable {
    public void jsonify(JSONStringer jsonStringer) throws JSONException;
}
