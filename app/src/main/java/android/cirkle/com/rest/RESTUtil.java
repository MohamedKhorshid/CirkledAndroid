package android.cirkle.com.rest;

import android.cirkle.com.exception.BusinessErrorCode;
import android.cirkle.com.exception.CirkleBusinessException;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.exception.CirkleSystemException;
import android.cirkle.com.exception.CirkleUnauthorizedException;
import android.cirkle.com.exception.SystemErrorCode;
import android.cirkle.com.response.CirkleResponse;
import android.cirkle.com.response.HttpResponseResolver;
import android.cirkle.com.response.UnauthorizedResponse;
import android.cirkle.com.response.ValidationErrorResponse;
import android.cirkle.com.session.SessionManager;
import android.content.Context;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Wagdy on 1/5/2015
 */
public class RESTUtil {

    Context context;

    public RESTUtil(Context context) {
        this.context = context;
    }

    private String getServerConnectionString() {
        // return "http://192.168.1.24:8000";
        return "http://192.168.43.94:8000";
        // return "http://cirkle.mybluemix.net";
    }

    public CirkleResponse delete(String path, Map<String, String> params) throws CirkleException {

        String serviceURL = getServerConnectionString() + path + appendGetParams(params);;

        HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(serviceURL);

        appendHttpHeader(delete);

        return executeHttpRequest(delete, client);

    }

    public CirkleResponse post(String path, Map<String, String> params) throws CirkleException {

        String serviceURL = getServerConnectionString() + path;

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(serviceURL);

        appendHttpHeader(post);

        appendPostParams(post, params);

        return executeHttpRequest(post, client);

    }

    public CirkleResponse get(String path, Map<String, String> params) throws CirkleException {

        String serviceURL = getServerConnectionString() + path + appendGetParams(params);;

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(serviceURL);

        appendHttpHeader(get);

        return executeHttpRequest(get, client);

    }

    public CirkleResponse get(String path) throws CirkleException {
        return get(path, new HashMap<String, String>());
    }

    public CirkleResponse delete(String path) throws CirkleException {
        return delete(path, new HashMap<String, String>());
    }

    private CirkleResponse executeHttpRequest(HttpUriRequest request, HttpClient client) throws CirkleException {
        try {
            HttpResponse httpResponse =  client.execute(request);
            String response = EntityUtils.toString(httpResponse.getEntity());

            CirkleResponse cirkleResponse = HttpResponseResolver.resolve(response, httpResponse.getStatusLine().getStatusCode());

            if(cirkleResponse instanceof ValidationErrorResponse) {
                BusinessErrorCode errorCode = BusinessErrorCode.parseErrorCode(response);
                throw new CirkleBusinessException(errorCode);
            } else if (cirkleResponse instanceof UnauthorizedResponse) {
                throw new CirkleUnauthorizedException();
            }

            return cirkleResponse;

        } catch (IOException e) {
            throw new CirkleSystemException(SystemErrorCode.REST_IO_OPERATION_FAILED, e);
        }
    }

    private void appendPostParams(HttpPost post, Map<String, String> params) throws CirkleSystemException {
        HttpEntity entity = null;

        List<NameValuePair> nameValuePairs = new ArrayList<>(params.size());

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
    }

    private String appendGetParams(Map<String, String> params) {

        StringBuilder builder = new StringBuilder("?");

        Iterator<String> itr = params.keySet().iterator();
        while(itr.hasNext()) {
            String key = itr.next();
            builder.append(key);
            builder.append("=");
            builder.append(params.get(key));
            builder.append("&");
        }

        return builder.toString();

    }

    private void appendHttpHeader(AbstractHttpMessage message) {

        SessionManager sessionManager = new SessionManager(context);

        if(sessionManager.isLoggedIn()) {
            String email = sessionManager.getEmail();
            String password = sessionManager.getPassword();

            message.addHeader("authorization", "Basic " + Base64.encodeToString((email + ":" + password).getBytes(), Base64.NO_WRAP));
        }

        message.addHeader("accept", "application/json");
    }
}
