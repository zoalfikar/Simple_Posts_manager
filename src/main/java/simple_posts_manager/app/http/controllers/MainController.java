package simple_posts_manager.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static simple_posts_manager.controllers.helper.IndexController.sendSuccessMessage;


@RestController	
public class MainController {
	@GetMapping(path="/")
	public @ResponseBody Object  greeting() {
		return sendSuccessMessage("Welcome in posts managment system");
	}
}


