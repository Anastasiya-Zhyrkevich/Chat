package instances;

/**
 * Created by User on 15.05.15.
 */
public class User {
    private int id;
    private String userName;

    public User(int id, String userName){
        this.id = id;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
