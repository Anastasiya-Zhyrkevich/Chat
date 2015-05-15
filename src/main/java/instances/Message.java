package instances;

/**
 * Created by User on 15.05.15.
 */
public class Message {
    private int id = 0;
    private String text;
    private int userId;
    private int time = 0;

    public Message(String text, int userId){
        this.text = text;
        this.userId = userId;
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

}
