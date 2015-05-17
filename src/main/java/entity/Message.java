package entity;

/**
 * Created by User on 15.05.15.
 */
public class Message {
    private int id;
    private String text;
    private int userId;
    private int time;

    public Message(String text, int userId, int time){
        this.id = 0;
        this.text = text;
        this.userId = userId;
        this.time = time;
    }

    public Message(int id, String text, int userId, int time){
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.time = time;
    }

    public Message(int id, String text, int userId){
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.time = 0;
    }

    public void setTime(int time){
       this.time = time;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public String getText(){
        return this.text;
    }
    public int getUserId(){
        return this.userId;
    }
    public int getTime(){
        return this.time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (time != message.time) return false;
        if (userId != message.userId) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + time;
        return result;
    }
}
