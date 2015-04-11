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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return email != null && password != null;
    }
}
