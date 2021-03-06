package entity;

/**
 * Created by User on 15.05.15.
 */
public class ClientProtocol {
    private int id;
    private String type;
    private int userId;
    private String text;
    private long addInfo = 0;

    public ClientProtocol(String type, Message msg){
        this.id = 0;
        this.type = type;
        this.text = msg.getText();
        this.userId = msg.getUserId();
        this.addInfo = msg.getTime();
    }

    public ClientProtocol(String type, int userId, String text){
        this.id = 0;
        this.type = type;
        this.text = text;
        this.userId = userId;
        this.addInfo = 0;
    }
    public ClientProtocol(String type, String text, long addInfo){
        this.id = 0;
        this.type = type;
        this.text = text;
        this.userId = 0;
        this.addInfo = addInfo;
    }

    public ClientProtocol(String type, long messId){
        this.id = 0;
        this.type = type;
        this.text = new String();
        this.userId = 0;
        this.addInfo = messId;
    }

    public long getAddInfo() {
        return addInfo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
