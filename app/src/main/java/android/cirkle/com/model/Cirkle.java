package android.cirkle.com.model;

import java.util.List;

/**
 * Created by Mohamed Wagdy on 3/31/2015
 */
public class Cirkle {

    private int cirkleId;
    private String title;
    private List<User> members;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public int getCirkleId() {
        return cirkleId;
    }

    public void setCirkleId(int cirkleId) {
        this.cirkleId = cirkleId;
    }
}
