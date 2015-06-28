package android.cirkle.com.session;

/**
 * Created by Mohamed Wagdy on 4/6/2015
 */
public class SessionUtil {

    private static SessionUtil _instance;

    private SessionUtil() {}

    public static SessionUtil getInstance() {
        if(_instance == null) {
            _instance = new SessionUtil();
        }
        return _instance;
    }

    private String email;
    private String password;

    public String getEmail() {
        return "mohamedwagdykhorshid@gmail.com";//email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return "12345678"; //password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return email != null && password != null;
    }
}
