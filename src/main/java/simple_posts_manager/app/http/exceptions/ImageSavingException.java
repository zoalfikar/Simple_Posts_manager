package library_managment_system.app.exceptions;


public class ImageSavingException
    extends RuntimeException {
 
    private String message;
 
    public ImageSavingException()
    {
        super("Something went wrong while updating image");
        this.message = "Something went wrong while updating image";
    }
}