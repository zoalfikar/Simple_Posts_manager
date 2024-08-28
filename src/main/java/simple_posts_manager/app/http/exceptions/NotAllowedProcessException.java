package library_managment_system.app.exceptions;


public class NotAllowedProcessException
    extends RuntimeException {
 
    private String message;
 
    public NotAllowedProcessException()
    {
        super("current user can't do this process");
        this.message = "current user can't do this process";
    }
}