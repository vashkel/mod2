package exception;

public class ServiceException extends Exception {

    public ServiceException(String message, Throwable cause) {
        super(message,cause);
    }

    public ServiceException(String s) {
        super(s);
    }

    public ServiceException(String message, RepositoryException e) {
        super(message, e);

    }
}
