package library_managment_system.app.exceptions;


public class PostAlreadyLikedException
    extends RuntimeException {
 
    private String message;
 
    public PostAlreadyLikedException(Long id)
    {
        super("post already liked by user , post id =" + id);
        this.message = "post already liked by user , post id =" + id;
    }
}