package library_managment_system.app.exceptions;


public class CommentNotfoundException
    extends RuntimeException {
 
    private String message;
 
    public CommentNotfoundException(Long id)
    {
        super("comment not found : invalid id " + id);
        this.message = "comment not found : invalid id " + id;
    }
}