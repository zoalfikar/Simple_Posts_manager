package simple_posts_manager.controllers.helper;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ResponseBody;


public class IndexController {

    public static  Object sendSuccessMessage(String msg) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", msg);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public static  Object sendFailMessage(String msg) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", msg);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
