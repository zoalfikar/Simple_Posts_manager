package library_managment_system.app.exceptions;


public class PostNotExistsException
    extends RuntimeException {
 
    private String message;
 
    public PostNotExistsException() {}
 
    public PostNotExistsException(Long id)
    {
        super("Post not found : invalid id " + id);
        this.message = "Post not found : invalid id " + id;
    }
}