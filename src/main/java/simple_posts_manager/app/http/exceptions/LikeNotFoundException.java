package library_managment_system.app.exceptions;


public class LikeNotFoundException
    extends RuntimeException {
 
    private String message;
 
    public LikeNotFoundException(Long id)
    {
        super("like not found : invalid id " + id);
        this.message = "like not found : invalid id " + id;
    }
}