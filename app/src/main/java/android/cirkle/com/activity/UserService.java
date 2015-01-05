package android.cirkle.com.activity;

import android.cirkle.com.rest.RESTUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Wagdy on 1/5/2015
 */
public class UserService {

    public void addUser(String email, String password, String displayName) {

        String serviceURL = RESTUtil.getServerConnectionString() + "/users";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(serviceURL);

        HttpEntity entity = null;


        List<NameValuePair> nameValuePairs = new ArrayList<>(3);
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("displayname", displayName));

        try {
            entity = new UrlEncodedFormEntity(nameValuePairs);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        post.setEntity(entity);

        try {
            HttpResponse response =  client.execute(post);
            response.getAllHeaders();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
