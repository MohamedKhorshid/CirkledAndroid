package com.augenblick.cirkle.error;

import com.augenblick.cirkle.exception.BusinessErrorCode;
import android.content.Context;

/**
 * Created by Mohamed Wagdy on 3/14/2015
 */
public class ErrorMessage {

    public static String format(BusinessErrorCode code, Context context) {
        if(code == null) {
            return "";
        }

        return context.getResources().getString(code.getMessage());
    }
}
