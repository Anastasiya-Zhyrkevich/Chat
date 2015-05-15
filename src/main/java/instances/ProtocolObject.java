package instances;

/**
 * Created by User on 15.05.15.
 */
public class ProtocolObject {
    private int id = 0;
    private String type;
    private int userId;
    private String text;
    private int addInfo = 0;

    public ProtocolObject(String type, int userId, String text, int addInfo){
        this.type = type;
        this.text = text;
        this.userId = userId;
        this.addInfo = addInfo;
    }

    public ProtocolObject(String type, int userId, String text){
        this.type = type;
        this.text = text;
        this.userId = userId;
    }

    public int getAddInfo() {
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
