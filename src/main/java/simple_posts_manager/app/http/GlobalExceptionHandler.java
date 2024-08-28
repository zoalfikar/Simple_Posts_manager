package simple_posts_manager.app.http;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.dao.DataIntegrityViolationException ;
import org.springframework.dao.DataAccessException;

import library_managment_system.app.exceptions.PostNotExistsException;
import library_managment_system.app.exceptions.NotAllowedProcessException;
import library_managment_system.app.exceptions.CommentNotfoundException;
import library_managment_system.app.exceptions.LikeNotFoundException;
import library_managment_system.app.exceptions.CommentNotfoundException;
import library_managment_system.app.exceptions.ImageSavingException;
import library_managment_system.app.exceptions.PostAlreadyLikedException;




@RestControllerAdvice
public class GlobalExceptionHandler {

    private Object exceptionErorr(Exception ex){
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        return errorBody;
    }

    private Object getValidationErrorsMap(Map<String, Object> errors) {
        Map<String, Object> errorsResponseBody = new HashMap<>();
        errorsResponseBody.put("errors", errors);
        return errorsResponseBody;
    }


	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, Object> errorsBody = new HashMap<>();
        for (FieldError e : errors){
            errorsBody.put(e.getField() , e.getDefaultMessage());
        }
        return new ResponseEntity<>(this.getValidationErrorsMap(errorsBody),new HttpHeaders() , HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IOException .class)
    public ResponseEntity<Object>  handleIOException (IOException  ex) {
        return new ResponseEntity<>(this.exceptionErorr(ex),new HttpHeaders() , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostNotExistsException .class)
    public ResponseEntity<Object>  handlePostNotExistsException (PostNotExistsException  ex) {
        return new ResponseEntity<>(this.exceptionErorr(ex),new HttpHeaders() , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotAllowedProcessException .class)
    public ResponseEntity<Object>  handleNotAllowedProcessException (NotAllowedProcessException  ex) {
        return new ResponseEntity<>(this.exceptionErorr(ex),new HttpHeaders() , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LikeNotFoundException .class)
    public ResponseEntity<Object>  handleLikeNotFoundException (LikeNotFoundException  ex) {
        return new ResponseEntity<>(this.exceptionErorr(ex),new HttpHeaders() , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentNotfoundException .class)
    public ResponseEntity<Object>  handleCommentNotfoundException (CommentNotfoundException  ex) {
        return new ResponseEntity<>(this.exceptionErorr(ex),new HttpHeaders() , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImageSavingException .class)
    public ResponseEntity<Object>  handleImageSavingException (ImageSavingException  ex) {
        return new ResponseEntity<>(this.exceptionErorr(ex),new HttpHeaders() , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PostAlreadyLikedException .class)
    public ResponseEntity<Object>  handlePostAlreadyLikedException (PostAlreadyLikedException  ex) {
        return new ResponseEntity<>(this.exceptionErorr(ex),new HttpHeaders() , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}