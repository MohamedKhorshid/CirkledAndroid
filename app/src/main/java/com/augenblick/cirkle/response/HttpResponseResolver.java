package com.augenblick.cirkle.response;

import com.augenblick.cirkle.exception.CirkleSystemException;

/**
 * Created by Mohamed Wagdy on 1/7/2015
 */
public class HttpResponseResolver {

    public static CirkleResponse resolve(String response, int statusCode) throws CirkleSystemException {

        CirkleResponse cirkleResponse = CirkleResponseFactory.getCirkleResponse(statusCode);

        if(cirkleResponse instanceof SystemErrorResponse) {
            throw new CirkleSystemException(((SystemErrorResponse) cirkleResponse).getErrorCode(), new Exception(response));
        } else if(cirkleResponse instanceof OkResponse) {
            cirkleResponse.setBody(response);
        }

        return cirkleResponse;
    }

}
