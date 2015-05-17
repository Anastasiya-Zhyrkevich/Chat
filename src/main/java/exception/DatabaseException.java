package exception;

/**
 * Created by user on 17.05.2015.
 */
public class DatabaseException extends Exception {

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(String msg, Exception e) {
        super(msg, e);
    }
}

