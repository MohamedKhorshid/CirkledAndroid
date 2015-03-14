package android.cirkle.com.error;

import android.content.Context;

/**
 * Created by Mohamed Wagdy on 3/14/2015
 */
public class ErrorMessage {

    public static String format(String code, Context context) {
        int id = context.getResources().getIdentifier(code, "string", context.getPackageName());
        return context.getResources().getString(id);
    }
}
