package android.cirkle.com.rest;

import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.SystemErrorCode;
import android.cirkle.com.response.CirkleResponse;
import android.cirkle.com.response.HttpResponseResolver;
import android.cirkle.com.response.ValidationErrorResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/5/2015
 */
public class RESTUtil {

    private static RESTUtil instance;

    private RESTUtil() {

    }

    public static synchronized RESTUtil getInstance() {
        if(instance == null) {
            instance = new RESTUtil();
        }
        return instance;
    }

    private String getServerConnectionString() {
        return "http://cirkle-nodejs.herokuapp.com";
    }

    public void post(String path, Map<String, String> params) throws CirkleSystemException, CirkleBusinessException {

        String serviceURL = getServerConnectionString() + "/users";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(serviceURL);

        HttpEntity entity = null;

        List<NameValuePair> nameValuePairs = new ArrayList<>(3);

        Iterator<String> itr = params.keySet().iterator();

        while(itr.hasNext()) {
            String key = itr.next();
            nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            entity = new UrlEncodedFormEntity(nameValuePairs);
        } catch (UnsupportedEncodingException e) {
            throw new CirkleSystemException(SystemErrorCode.REST_UNSUPPORTED_ENCODING, e);
        }

        post.setEntity(entity);

        try {
            HttpResponse httpResponse =  client.execute(post);
            String response = EntityUtils.toString(httpResponse.getEntity());

            CirkleResponse cirkleResponse = HttpResponseResolver.resolve(response);

            if(cirkleResponse instanceof ValidationErrorResponse) {

                // TODO throw validation exception throw new CirkleBusinessException();
            }

        } catch (IOException e) {
            throw new CirkleSystemException(SystemErrorCode.REST_IO_OPERATION_FAILED, e);
        }

    }
}
