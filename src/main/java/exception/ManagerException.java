package exception;

/**
 * Created by user on 17.05.2015.
 */
public class ManagerException extends Exception{

    public ManagerException(String msg) {
        super(msg);
    }

    public ManagerException(String msg, Exception e) {
        super(msg, e);
    }
}
